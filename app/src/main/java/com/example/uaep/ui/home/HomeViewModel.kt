package com.example.uaep.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.uaep.data.room1
import com.example.uaep.data.room2
import com.example.uaep.data.room3
import com.example.uaep.data.rooms
import com.example.uaep.dto.DummyResponse
import com.example.uaep.dto.ErrorResponse
import com.example.uaep.dto.RoomsResponseDto
import com.example.uaep.dto.UrlResponseDto
import com.example.uaep.model.Room
import com.example.uaep.network.AuthService
import com.example.uaep.network.CookieChanger
import com.example.uaep.network.ReAuthService
import com.example.uaep.utils.ErrorMessage
import com.example.ueap.model.RoomsFeed
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

sealed interface HomeUiState {

    val isLoading: Boolean
    val errorMessages: List<ErrorMessage>

    data class NoPosts(
        override val isLoading: Boolean,
        override val errorMessages: List<ErrorMessage>,
    ) : HomeUiState

    data class HasPosts(
        val roomsFeed: RoomsFeed,
        val selectedRoom: Room,
        override val isLoading: Boolean,
        override val errorMessages: List<ErrorMessage>,
        val isArticleOpen: Boolean
    ) : HomeUiState
}

private data class HomeViewModelState(
    val roomsFeed: RoomsFeed? = null,
    val selectedRoomId: String? = null,
    val isArticleOpen: Boolean = false,
    val isLoading: Boolean = false,
    val errorMessages: List<ErrorMessage> = emptyList(),
) {

    /**
     * Converts this [HomeViewModelState] into a more strongly typed [HomeUiState] for driving
     * the ui.
     */
    fun toUiState(): HomeUiState =
        if (roomsFeed == null) {
            HomeUiState.NoPosts(
                isLoading = isLoading,
                errorMessages = errorMessages
            )
        } else {
            HomeUiState.HasPosts(
                roomsFeed = roomsFeed,
                // Determine the selected post. This will be the post the user last selected.
                // If there is none (or that post isn't in the current feed), default to the
                // highlighted post
                isArticleOpen = isArticleOpen,
                isLoading = isLoading,
                errorMessages = errorMessages,
                selectedRoom = roomsFeed.allPosts.find {
                    it.id == selectedRoomId
                } ?: roomsFeed.data[0]
            )
        }
}

class HomeViewModel(
) : ViewModel() {

    private val viewModelState = MutableStateFlow(HomeViewModelState(isLoading = true))

    // UI state exposed to the UI
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

    /**
     * Refresh posts and update the UI state accordingly
     */
    var room_list: List<Room>? = null
    fun refreshPosts() {
        // Ui state is refreshing
        viewModelState.update { it.copy(isLoading = true) }

        //viewModelScope.launch {
            var check = false

            do {

                AuthService.getInstance().rooms().enqueue(object :
                    Callback<List<Room>> {
                    override fun onResponse(
                        call: Call<List<Room>>,
                        response: Response<List<Room>>
                    ) {
                        if (response.isSuccessful) {
                            check = false
                            room_list = response.body().orEmpty()
                            Log.i("rooms_response", response.body().toString())
                            Log.i("room_list", (listOf(room1, room2, room3)+room_list.orEmpty()).toString())
                            val result = RoomsFeed(
                                data = listOf(room1, room2, room3)+room_list.orEmpty()
                            )
                            viewModelState.update {
                                it.copy(roomsFeed = result, isLoading = false)
                            }
                            room_list = emptyList()
                        } else {
                            Log.i("rooms_fail_raw", response.raw().toString())
                            Log.i("rooms_fail_head", response.headers().toString())
                            check = true
                            val errorResponse: ErrorResponse? =
                                Gson().fromJson(
                                    response.errorBody()!!.charStream(),
                                    object : TypeToken<ErrorResponse>() {}.type
                                )
                            if (errorResponse!!.message != null && (errorResponse!!.message == "Expired access or refresh token")) {
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


    /**
     * Selects the given article to view more information about it.
     */
    fun selectArticle(postId: String) {
        // Treat selecting a detail as simply interacting with it
        viewModelState.update {
            it.copy(
                selectedRoomId = postId,
                isArticleOpen = true
            )
        }
    }


    /**
     * Notify that an error was displayed on the screen
     */
    fun errorShown(errorId: Long) {
        viewModelState.update { currentUiState ->
            val errorMessages = currentUiState.errorMessages.filterNot { it.id == errorId }
            currentUiState.copy(errorMessages = errorMessages)
        }
    }

    /**
     * Notify that the user interacted with the feed
     */
    fun interactedWithFeed() {
        viewModelState.update {
            it.copy(isArticleOpen = false)
        }
    }



    /**
     * Factory for HomeViewModel that takes PostsRepository as a dependency
     */
    companion object {
        fun provideFactory(
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return HomeViewModel() as T
            }
        }
    }
}
