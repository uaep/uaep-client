package com.example.uaep

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat
import androidx.navigation.NavHostController
import com.example.uaep.ui.match.MatchCreationScreen
import com.example.uaep.ui.match.MatchCreationScreenViewModel
import com.example.uaep.ui.profile.ProfileDto
import com.example.uaep.ui.profile.ProfileInfo
import com.example.uaep.ui.theme.UaepTheme


class MainActivity : ComponentActivity() {

    lateinit var navController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, true)
        
        setContent {
//            UaepTheme {
//                ProfileInfo(
//                    ProfileDto(
//                        name = "김광진",
//                        position = "FK",
//                        address = "경기도 수원시 어디로 가야하죠 아저씨 119",
//                        gender = "남자"
//                    )
//                )
//            }
//            val windowSizeClass = rememberWindowSizeClass()
//            UaepApp(windowSize = windowSizeClass)
        }
    }
}

