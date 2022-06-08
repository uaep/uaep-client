package com.example.uaep.ui.navigate

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Handshake
import androidx.compose.material.icons.filled.RateReview
import androidx.compose.material.icons.filled.SportsSoccer
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(val title: String, val icon: ImageVector, val route: String){
    object Matching : BottomNavItem("매칭", Icons.Filled.Handshake, "home")
    object Review: BottomNavItem("리뷰", Icons.Filled.RateReview,"review_screen")
    object Participating: BottomNavItem("참가 중", Icons.Filled.SportsSoccer,"participating_screen")
}