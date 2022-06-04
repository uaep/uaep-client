package com.example.uaep

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat
import androidx.navigation.NavHostController
import com.example.uaep.ui.UaepApp
import com.example.uaep.utils.rememberWindowSizeClass


class MainActivity : ComponentActivity() {

    lateinit var navController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, true)
        
        setContent {
            val windowSizeClass = rememberWindowSizeClass()
            UaepApp(windowSize = windowSizeClass)
        }
    }
}