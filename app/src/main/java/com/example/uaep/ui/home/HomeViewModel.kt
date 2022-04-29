package com.example.uaep.ui.home

import com.example.uaep.utils.ErrorMessage
import com.example.ueap.model.RoomsFeed

sealed interface HomeUiState {

    val isLoading: Boolean
    val errorMessages: List<ErrorMessage>

    data class NoPosts(
        override val isLoading: Boolean,
        override val errorMessages: List<ErrorMessage>,
    ) : HomeUiState

    data class HasPosts(
        val roomsFeed: RoomsFeed,
        override val isLoading: Boolean,
        override val errorMessages: List<ErrorMessage>,
    ) : HomeUiState
}