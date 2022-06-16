package com.example.uaep.ui.match

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.ViewModel
import com.example.uaep.network.GameApiService

class MatchCreationScreenViewModel() : ViewModel() {
    private val mPlace = mutableStateOf("")
    private val mNumPlayer = mutableStateOf("")
    private val mNumPlayerSelected = mutableStateOf(false)
    private val mGender = mutableStateOf("")
    private val mGenderSelected = mutableStateOf(false)
    private val mLimitaion = mutableStateOf("")
    private val mLimitaionSelected = mutableStateOf(false)
    val gameApiService: GameApiService = GameApiService.getInstance()

    val place: State<String> = mPlace
    val numPlayer: State<String> = mNumPlayer
    val numPlayerSelected: State<Boolean> = mNumPlayerSelected
    val gender: State<String> = mGender
    val genderSelected: State<Boolean> = mGenderSelected
    val limitation: State<String> = mLimitaion
    val limitationSelected: State<Boolean> = mLimitaionSelected

    val icon1: ImageVector
        @Composable get() = if (mNumPlayerSelected.value) Icons.Filled.ArrowDropUp else Icons.Filled.ArrowDropDown
    val icon2: ImageVector
        @Composable get() = if (mGenderSelected.value) Icons.Filled.ArrowDropUp else Icons.Filled.ArrowDropDown
    val icon3: ImageVector
        @Composable get() = if (mLimitaionSelected.value) Icons.Filled.ArrowDropUp else Icons.Filled.ArrowDropDown

    fun updatePlace(place: String) {
        mPlace.value = place
    }

    fun updateNumPlayer(numPlayer: String) {
        mNumPlayer.value = numPlayer
    }

    fun onNumPlayerSelected() {
        mNumPlayerSelected.value = !mNumPlayerSelected.value
    }

    fun updateGender(gender: String) {
        mGender.value = gender
    }

    fun onGenderSelected() {
        mGenderSelected.value = !mGenderSelected.value
    }

    fun updateLimitaion(limitaion: String) {
        mLimitaion.value = limitaion
    }

    fun onLimitationSelected() {
        mLimitaionSelected.value = !mLimitaionSelected.value
    }
}