package com.example.uaep.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.uaep.dto.DummyResponse
import com.example.uaep.dto.ErrorResponse
import com.example.uaep.dto.RoomDto
import com.example.uaep.dto.UserDto
import com.example.uaep.model.Room
import com.example.uaep.network.AuthService
import com.example.uaep.network.CookieChanger
import com.example.uaep.network.GameApiService
import com.example.uaep.network.ReAuthService
import com.example.uaep.network.UserApiService
import com.example.uaep.utils.ErrorMessage
import com.example.ueap.model.RoomsFeed
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

sealed interface HomeUiState {

    val isLoading: Boolean
    val errorMessages: List<ErrorMessage>

    data class NoPosts(
        override val isLoading: Boolean,
        override val errorMessages: List<ErrorMessage>,
    ) : HomeUiState

    data class HasPosts(
        val roomsFeed: RoomsFeed,
        val selectedRoom: RoomDto,
        override val isLoading: Boolean,
        override val errorMessages: List<ErrorMessage>,
        val isArticleOpen: Boolean,
    ) : HomeUiState
}

private data class HomeViewModelState(
    val roomsFeed: RoomsFeed? = null,
    val selectedRoomId: RoomDto? = null,
    val isArticleOpen: Boolean = false,
    val isLoading: Boolean = false,
    val errorMessages: List<ErrorMessage> = emptyList()
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
                selectedRoom = selectedRoomId
                    ?: RoomDto("-1", Date(0,0,0,0,0),"Wrong Page","6vs6","-", "-", null, null, null, null, null)
            )
        }
}

class HomeViewModel(
    participating: Boolean,
    auto: Boolean
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


        when {
            auto -> selectAutoMatching()
            participating -> refreshParticipating()
            else -> refreshPosts()
        }

        //refreshPosts()
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
                            Log.i("room_list", (room_list.orEmpty().toString()))
                            val result = RoomsFeed(
                                data = //listOf(room1, room2, room3)+
                                        room_list.orEmpty()
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


    fun refreshParticipating() {
        // Ui state is refreshing
        viewModelState.update { it.copy(isLoading = true) }

        var check = false
        do {
            UserApiService.getInstance().getUser().enqueue(object :
                Callback<UserDto> {
                override fun onResponse(
                    call: Call<UserDto>,
                    response: Response<UserDto>
                ) {
                    if (response.isSuccessful) {
                        check = false
                        room_list = response.body()?.games.orEmpty()
                        Log.i("participating_response", response.body().toString())
                        Log.i("participating_list", (room_list.orEmpty().toString()))
                        val result = RoomsFeed(
                            data = //listOf(room1, room2, room3)+
                            room_list.orEmpty()
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
                override fun onFailure(call: Call<UserDto>, t: Throwable) {
                    Log.i("test", "실패$t")
                    check = true
                }
            })
        }while(check)

    }


    /**
     * Selects the given article to view more information about it.
     */
    fun selectArticle(postId: String) {
        // Treat selecting a detail as simply interacting with it
        var check = false

        do {

            AuthService.getInstance().select(postId).enqueue(object :
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

    fun selectAutoMatching() {
        // Treat selecting a detail as simply interacting with it
        var check = false

        do {

            AuthService.getInstance().getRecommend().enqueue(object :
                Callback<Room> {
                override fun onResponse(
                    call: Call<Room>,
                    response: Response<Room>
                ) {
                    if (response.isSuccessful) {
                        if(response.body() != null)
                            room_list = listOf(response.body()!!)
                        else
                            room_list = emptyList()
                        Log.i("participating_response", response.body().toString())
                        Log.i("participating_list", (room_list.orEmpty().toString()))
                        val result = RoomsFeed(
                            data = //listOf(room1, room2, room3)+
                            room_list.orEmpty()
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
                override fun onFailure(call: Call<Room>, t: Throwable) {
                    Log.i("test", "실패$t")
                    check = true
                }
            })
        }while(check)
        viewModelState.update {
            it.copy( isLoading = false)
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

    // TODO: 지역필터링
    fun getAllGamesByRegion(region: String) {
        // Ui state is refreshing
        viewModelState.update { it.copy(isLoading = true) }

        //viewModelScope.launch {
        var check = false
        do {
            GameApiService.getInstance().getAllGamesByRegion(region).enqueue(object :
                Callback<List<Room>> {
                override fun onResponse(
                    call: Call<List<Room>>,
                    response: Response<List<Room>>
                ) {
                    if (response.isSuccessful) {
                        check = false
                        room_list = response.body().orEmpty()
                        Log.i("rooms_response", response.body().toString())
                        Log.i("room_list", (room_list.orEmpty().toString()))
                        val result = RoomsFeed(
                            data = room_list.orEmpty()
                        )
                        viewModelState.update {
                            it.copy(roomsFeed = result, isLoading = false)
                        }
                        room_list = emptyList()
                    } else {
//                        Log.i("rooms_fail_body", response.errorBody().toString())
//                        Log.i("rooms_fail_head", response.headers().toString())
//                        check = true
//                        val errorResponse: ErrorResponse? =
//                            Gson().fromJson(
//                                response.errorBody()!!.charStream(),
//                                object : TypeToken<ErrorResponse>() {}.type
//                            )
//                        if (errorResponse!!.message != null && (errorResponse!!.statusCode == 401)) {
//                            ReAuthService.getInstance().reauth().enqueue(object :
//                                Callback<DummyResponse> {
//
//                                override fun onResponse(
//                                    call: Call<DummyResponse>,
//                                    response: Response<DummyResponse>
//                                ) {
//                                    if (response.isSuccessful) {
//                                        val tokens = CookieChanger<DummyResponse>().change(response)
//                                        AuthService.getCookieJar().saveToken(tokens)
//                                    }
//                                }
//                                override fun onFailure(
//                                    call: Call<DummyResponse>,
//                                    t: Throwable
//                                ) {
//                                    Log.i("test", "실패$t")
//                                }
//                            })
//                        }
                    }
                }
                override fun onFailure(call: Call<List<Room>>, t: Throwable) {
                    Log.i("test", "실패$t")
                    check = true
                }
            })
        }while(check)
    }

    fun getAllGamesByGender(gender: String) {
        // Ui state is refreshing
        viewModelState.update { it.copy(isLoading = true) }

        //viewModelScope.launch {
        var check = false
        do {
            GameApiService.getInstance().getAllGamesByGender(gender).enqueue(object :
                Callback<List<Room>> {
                override fun onResponse(
                    call: Call<List<Room>>,
                    response: Response<List<Room>>
                ) {
                    if (response.isSuccessful) {
                        check = false
                        room_list = response.body().orEmpty()
                        Log.i("rooms_response", response.body().toString())
                        Log.i("room_list", (room_list.orEmpty().toString()))
                        val result = RoomsFeed(
                            data = room_list.orEmpty()
                        )
                        viewModelState.update {
                            it.copy(roomsFeed = result, isLoading = false)
                        }
                        room_list = emptyList()
                    } else {
//                        Log.i("rooms_fail_body", response.errorBody().toString())
//                        Log.i("rooms_fail_head", response.headers().toString())
//                        check = true
//                        val errorResponse: ErrorResponse? =
//                            Gson().fromJson(
//                                response.errorBody()!!.charStream(),
//                                object : TypeToken<ErrorResponse>() {}.type
//                            )
//                        if (errorResponse!!.message != null && (errorResponse!!.statusCode == 401)) {
//                            ReAuthService.getInstance().reauth().enqueue(object :
//                                Callback<DummyResponse> {
//
//                                override fun onResponse(
//                                    call: Call<DummyResponse>,
//                                    response: Response<DummyResponse>
//                                ) {
//                                    if (response.isSuccessful) {
//                                        val tokens = CookieChanger<DummyResponse>().change(response)
//                                        AuthService.getCookieJar().saveToken(tokens)
//                                    }
//                                }
//                                override fun onFailure(
//                                    call: Call<DummyResponse>,
//                                    t: Throwable
//                                ) {
//                                    Log.i("test", "실패$t")
//                                }
//                            })
//                        }
                    }
                }
                override fun onFailure(call: Call<List<Room>>, t: Throwable) {
                    Log.i("test", "실패$t")
                    check = true
                }
            })
        }while(check)
    }

    fun getAllGamesByLevel(level: String) {
        // Ui state is refreshing
        viewModelState.update { it.copy(isLoading = true) }

        //viewModelScope.launch {
        var check = false
        do {
            GameApiService.getInstance().getAllGamesByLevel(level).enqueue(object :
                Callback<List<Room>> {
                override fun onResponse(
                    call: Call<List<Room>>,
                    response: Response<List<Room>>
                ) {
                    if (response.isSuccessful) {
                        check = false
                        room_list = response.body().orEmpty()
                        Log.i("rooms_response", response.body().toString())
                        Log.i("room_list", (room_list.orEmpty().toString()))
                        val result = RoomsFeed(
                            data = room_list.orEmpty()
                        )
                        viewModelState.update {
                            it.copy(roomsFeed = result, isLoading = false)
                        }
                        room_list = emptyList()
                    } else {
//                        Log.i("rooms_fail_body", response.errorBody().toString())
//                        Log.i("rooms_fail_head", response.headers().toString())
//                        check = true
//                        val errorResponse: ErrorResponse? =
//                            Gson().fromJson(
//                                response.errorBody()!!.charStream(),
//                                object : TypeToken<ErrorResponse>() {}.type
//                            )
//                        if (errorResponse!!.message != null && (errorResponse!!.statusCode == 401)) {
//                            ReAuthService.getInstance().reauth().enqueue(object :
//                                Callback<DummyResponse> {
//
//                                override fun onResponse(
//                                    call: Call<DummyResponse>,
//                                    response: Response<DummyResponse>
//                                ) {
//                                    if (response.isSuccessful) {
//                                        val tokens = CookieChanger<DummyResponse>().change(response)
//                                        AuthService.getCookieJar().saveToken(tokens)
//                                    }
//                                }
//                                override fun onFailure(
//                                    call: Call<DummyResponse>,
//                                    t: Throwable
//                                ) {
//                                    Log.i("test", "실패$t")
//                                }
//                            })
//                        }
                    }
                }
                override fun onFailure(call: Call<List<Room>>, t: Throwable) {
                    Log.i("test", "실패$t")
                    check = true
                }
            })
        }while(check)
    }

    fun getAllGamesByStatus(status: String) {
        // Ui state is refreshing
        viewModelState.update { it.copy(isLoading = true) }

        //viewModelScope.launch {
        var check = false
        do {
            GameApiService.getInstance().getAllGamesByStatus(status).enqueue(object :
                Callback<List<Room>> {
                override fun onResponse(
                    call: Call<List<Room>>,
                    response: Response<List<Room>>
                ) {
                    if (response.isSuccessful) {
                        check = false
                        room_list = response.body().orEmpty()
                        Log.i("rooms_response", response.body().toString())
                        Log.i("room_list", (room_list.orEmpty().toString()))
                        val result = RoomsFeed(
                            data = room_list.orEmpty()
                        )
                        viewModelState.update {
                            it.copy(roomsFeed = result, isLoading = false)
                        }
                        room_list = emptyList()
                    } else {
//                        Log.i("rooms_fail_body", response.errorBody().toString())
//                        Log.i("rooms_fail_head", response.headers().toString())
//                        check = true
//                        val errorResponse: ErrorResponse? =
//                            Gson().fromJson(
//                                response.errorBody()!!.charStream(),
//                                object : TypeToken<ErrorResponse>() {}.type
//                            )
//                        if (errorResponse!!.message != null && (errorResponse!!.statusCode == 401)) {
//                            ReAuthService.getInstance().reauth().enqueue(object :
//                                Callback<DummyResponse> {
//
//                                override fun onResponse(
//                                    call: Call<DummyResponse>,
//                                    response: Response<DummyResponse>
//                                ) {
//                                    if (response.isSuccessful) {
//                                        val tokens = CookieChanger<DummyResponse>().change(response)
//                                        AuthService.getCookieJar().saveToken(tokens)
//                                    }
//                                }
//                                override fun onFailure(
//                                    call: Call<DummyResponse>,
//                                    t: Throwable
//                                ) {
//                                    Log.i("test", "실패$t")
//                                }
//                            })
//                        }
                    }
                }
                override fun onFailure(call: Call<List<Room>>, t: Throwable) {
                    Log.i("test", "실패$t")
                    check = true
                }
            })
        }while(check)
    }

    fun getAllGamesByNumPlayer(numPlayer: String) {
        // Ui state is refreshing
        viewModelState.update { it.copy(isLoading = true) }

        //viewModelScope.launch {
        var check = false
        do {
            GameApiService.getInstance().getAllGamesByNumPlayer(numPlayer).enqueue(object :
                Callback<List<Room>> {
                override fun onResponse(
                    call: Call<List<Room>>,
                    response: Response<List<Room>>
                ) {
                    if (response.isSuccessful) {
                        check = false
                        room_list = response.body().orEmpty()
                        Log.i("rooms_response", response.body().toString())
                        Log.i("room_list", (room_list.orEmpty().toString()))
                        val result = RoomsFeed(
                            data = room_list.orEmpty()
                        )
                        viewModelState.update {
                            it.copy(roomsFeed = result, isLoading = false)
                        }
                        room_list = emptyList()
                    } else {
//                        Log.i("rooms_fail_body", response.errorBody().toString())
//                        Log.i("rooms_fail_head", response.headers().toString())
//                        check = true
//                        val errorResponse: ErrorResponse? =
//                            Gson().fromJson(
//                                response.errorBody()!!.charStream(),
//                                object : TypeToken<ErrorResponse>() {}.type
//                            )
//                        if (errorResponse!!.message != null && (errorResponse!!.statusCode == 401)) {
//                            ReAuthService.getInstance().reauth().enqueue(object :
//                                Callback<DummyResponse> {
//
//                                override fun onResponse(
//                                    call: Call<DummyResponse>,
//                                    response: Response<DummyResponse>
//                                ) {
//                                    if (response.isSuccessful) {
//                                        val tokens = CookieChanger<DummyResponse>().change(response)
//                                        AuthService.getCookieJar().saveToken(tokens)
//                                    }
//                                }
//                                override fun onFailure(
//                                    call: Call<DummyResponse>,
//                                    t: Throwable
//                                ) {
//                                    Log.i("test", "실패$t")
//                                }
//                            })
//                        }
                    }
                }
                override fun onFailure(call: Call<List<Room>>, t: Throwable) {
                    Log.i("test", "실패$t")
                    check = true
                }
            })
        }while(check)
    }



    /**
     * Factory for HomeViewModel that takes PostsRepository as a dependency
     */
    companion object {
        fun provideFactory(
            participating: Boolean,
            auto: Boolean
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return HomeViewModel(participating, auto) as T
            }
        }
    }
}
