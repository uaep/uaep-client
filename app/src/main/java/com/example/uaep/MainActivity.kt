package com.example.uaep

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat
import com.example.uaep.ui.UaepApp
import com.example.uaep.utils.rememberWindowSizeClass


class MainActivity : ComponentActivity() {
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
            val windowSizeClass = rememberWindowSizeClass()
            UaepApp(windowSize = windowSizeClass)
        }
    }
}