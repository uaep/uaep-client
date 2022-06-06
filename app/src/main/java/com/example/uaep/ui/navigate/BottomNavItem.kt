package com.example.uaep.ui.navigate

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Handshake
import androidx.compose.material.icons.filled.RateReview
import androidx.compose.material.icons.filled.SportsSoccer
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(val title: String, val icon: ImageVector, val route: String){
    object Participate : BottomNavItem("참가", Icons.Filled.Handshake, "home")
    object Review: BottomNavItem("리뷰", Icons.Filled.RateReview,"review_screen")
    object Match: BottomNavItem("경기", Icons.Filled.SportsSoccer,"my_match_screen")
}