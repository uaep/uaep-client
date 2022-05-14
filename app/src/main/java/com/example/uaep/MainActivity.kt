package com.example.uaep

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.uaep.data.rooms
import com.example.uaep.ui.home.HomeFeedScreen
import com.example.uaep.ui.home.HomeUiState
import com.example.uaep.uitmp.UaepTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.core.view.WindowCompat
import com.example.uaep.ui.UaepApp
import com.example.uaep.ui.match.RoomContainer
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
