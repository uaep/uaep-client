package com.example.uaep

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat
import androidx.navigation.NavHostController
import com.example.uaep.ui.match.MatchCreationScreen
import com.example.uaep.ui.match.MatchCreationScreenViewModel
import com.example.uaep.ui.theme.UaepTheme


class MainActivity : ComponentActivity() {

    lateinit var navController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, true)
        
        setContent {
            UaepTheme{
                MatchCreationScreen(vm = MatchCreationScreenViewModel())
            }
//            val windowSizeClass = rememberWindowSizeClass()
//            UaepApp(windowSize = windowSizeClass)
//            ProfileScreen()
        }
    }
}

