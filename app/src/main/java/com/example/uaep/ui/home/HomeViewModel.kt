package com.example.uaep.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.uaep.data.rooms
import com.example.uaep.model.Room
import com.example.uaep.utils.ErrorMessage
import com.example.ueap.model.RoomsFeed
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

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

    }

    /**
     * Refresh posts and update the UI state accordingly
     */
    fun refreshPosts() {
        // Ui state is refreshing
        viewModelState.update { it.copy(isLoading = true) }

        viewModelScope.launch {
            val result = rooms
            viewModelState.update {
                it.copy(roomsFeed = result, isLoading = false)
            }
        }
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
