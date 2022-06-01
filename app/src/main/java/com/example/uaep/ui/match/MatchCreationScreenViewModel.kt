package com.example.uaep.ui.match

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.ViewModel

class MatchCreationScreenViewModel : ViewModel() {
    private val mTitle = mutableStateOf("")
    private val mPlace = mutableStateOf("")
    private val mNumPlayer = mutableStateOf("")
    private val mNumPlayerSelected = mutableStateOf(false)

    val title: State<String> = mTitle
    val place: State<String> = mPlace
    val numPlayer: State<String> = mNumPlayer
    val numPlayerSelected = mNumPlayerSelected
    val icon: ImageVector
        @Composable get() = if (mNumPlayerSelected.value) Icons.Filled.ArrowDropUp else Icons.Filled.ArrowDropDown

    fun updateTitle(title: String) {
        mTitle.value = title
    }

    fun updatePlace(place: String) {
        mPlace.value = place
    }

    fun updateNumPlayer(numPlayer: String) {
        mNumPlayer.value = numPlayer
    }

    fun onNumPlayerSelected() {
        mNumPlayerSelected.value = !mNumPlayerSelected.value
    }
}