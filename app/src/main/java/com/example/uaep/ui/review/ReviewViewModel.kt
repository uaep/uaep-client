package com.example.uaep.ui.review

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.uaep.data.room1
import com.example.uaep.data.room2
import com.example.uaep.data.room3
import com.example.uaep.dto.DummyResponse
import com.example.uaep.dto.ErrorResponse
import com.example.uaep.dto.RoomDto
import com.example.uaep.model.Room
import com.example.uaep.network.AuthService
import com.example.uaep.network.CookieChanger
import com.example.uaep.network.ReAuthService
import com.example.uaep.utils.ErrorMessage
import com.example.ueap.model.RoomsFeed
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

sealed interface ReviewUiState {

    val isLoading: Boolean
    val errorMessages: List<ErrorMessage>

    data class NoPosts(
        override val isLoading: Boolean,
        override val errorMessages: List<ErrorMessage>,
    ) : ReviewUiState

    data class HasPosts(
        val roomsFeed: RoomsFeed,
        val selectedRoom: RoomDto,
        override val isLoading: Boolean,
        override val errorMessages: List<ErrorMessage>,
        val isArticleOpen: Boolean
    ) : ReviewUiState
}

private data class ReviewViewModelState(
    val roomsFeed: RoomsFeed? = null,
    val selectedRoomId: RoomDto? = null,
    val isArticleOpen: Boolean = false,
    val isLoading: Boolean = false,
    val errorMessages: List<ErrorMessage> = emptyList()
) {

    fun toUiState(): ReviewUiState =
        if (roomsFeed == null) {
            ReviewUiState.NoPosts(
                isLoading = isLoading,
                errorMessages = errorMessages
            )
        } else {
            ReviewUiState.HasPosts(
                roomsFeed = roomsFeed,
                isArticleOpen = isArticleOpen,
                isLoading = isLoading,
                errorMessages = errorMessages,
                selectedRoom = selectedRoomId
                    ?: RoomDto("-1", Date(0,0,0,0,0),"Wrong Page","6vs6","-", "-", null, null, null, null, null, null)
            )
        }
}

class ReviewViewModel(
) : ViewModel() {

    private val viewModelState = MutableStateFlow(ReviewViewModelState(isLoading = true))

    val uiState = viewModelState
        .map { it.toUiState() }
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            viewModelState.value.toUiState()
        )

    init {
        refreshPosts()
        refreshPosts()
    }

    var room_list: List<Room>? = null
    fun refreshPosts() {
        // Ui state is refreshing
        viewModelState.update { it.copy(isLoading = true) }

        //viewModelScope.launch {
        var check = false
        do {
            AuthService.getInstance().reviews().enqueue(object :
                Callback<List<Room>> {
                override fun onResponse(
                    call: Call<List<Room>>,
                    response: Response<List<Room>>
                ) {
                    if (response.isSuccessful) {
                        check = false
                        room_list = response.body().orEmpty()
                        Log.i("rooms_response", response.body().toString())
                        Log.i("room_list", room_list.orEmpty().toString())
                        val result = RoomsFeed(
                            data = room_list.orEmpty()
                        )
                        viewModelState.update {
                            it.copy(roomsFeed = result, isLoading = false)
                        }
                        room_list = emptyList()
                    } else {
                        Log.i("rooms_fail_body", response.errorBody().toString())
                        Log.i("rooms_fail_head", response.headers().toString())
                        check = true
                        val errorResponse: ErrorResponse? =
                            Gson().fromJson(
                                response.errorBody()!!.charStream(),
                                object : TypeToken<ErrorResponse>() {}.type
                            )
                        if (errorResponse!!.message != null && (errorResponse!!.statusCode == 401)) {
                            ReAuthService.getInstance().reauth().enqueue(object :
                                Callback<DummyResponse> {

                                override fun onResponse(
                                    call: Call<DummyResponse>,
                                    response: Response<DummyResponse>
                                ) {
                                    if (response.isSuccessful) {
                                        val tokens = CookieChanger<DummyResponse>().change(response)
                                        AuthService.getCookieJar().saveToken(tokens)
                                    }
                                }
                                override fun onFailure(
                                    call: Call<DummyResponse>,
                                    t: Throwable
                                ) {
                                    Log.i("test", "실패$t")
                                }
                            })
                        }
                    }
                }
                override fun onFailure(call: Call<List<Room>>, t: Throwable) {
                    Log.i("test", "실패$t")
                    check = true
                }
            })
        }while(check)


        //}
    }


    fun selectArticle(postId: String) {
        // Treat selecting a detail as simply interacting with it
        var check = false

        do {

            AuthService.getInstance().selectReview(postId).enqueue(object :
                Callback<RoomDto> {
                override fun onResponse(
                    call: Call<RoomDto>,
                    response: Response<RoomDto>
                ) {
                    if (response.isSuccessful) {
                        Log.i("room_enter", response.body().toString())
                        viewModelState.update {
                            it.copy(
                                selectedRoomId = response.body(),
                                isArticleOpen = true
                            )
                        }
                    } else {
                        Log.i("rooms_fail_raw", response.raw().toString())
                        Log.i("rooms_fail_head", response.headers().toString())
                        check = true
                        val errorResponse: ErrorResponse? =
                            Gson().fromJson(
                                response.errorBody()!!.charStream(),
                                object : TypeToken<ErrorResponse>() {}.type
                            )
                        if (errorResponse!!.message != null && (errorResponse!!.statusCode == 401)) {
                            ReAuthService.getInstance().reauth().enqueue(object :
                                Callback<DummyResponse> {

                                override fun onResponse(
                                    call: Call<DummyResponse>,
                                    response: Response<DummyResponse>
                                ) {
                                    if (response.isSuccessful) {
                                        val tokens = CookieChanger<DummyResponse>().change(response)
                                        AuthService.getCookieJar().saveToken(tokens)
                                    }
                                }
                                override fun onFailure(
                                    call: Call<DummyResponse>,
                                    t: Throwable
                                ) {
                                    Log.i("test", "실패$t")
                                }
                            })
                        }
                    }
                }
                override fun onFailure(call: Call<RoomDto>, t: Throwable) {
                    Log.i("test", "실패$t")
                    check = true
                }
            })
        }while(check)

    }


    fun errorShown(errorId: Long) {
        viewModelState.update { currentUiState ->
            val errorMessages = currentUiState.errorMessages.filterNot { it.id == errorId }
            currentUiState.copy(errorMessages = errorMessages)
        }
    }


    fun interactedWithFeed() {
        viewModelState.update {
            it.copy(isArticleOpen = false)
        }
    }


    companion object {
        fun provideFactory(
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return ReviewViewModel() as T
            }
        }
    }
}
