package com.example.uaep

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.rememberScaffoldState
import androidx.compose.ui.Modifier
import com.example.uaep.data.rooms
import com.example.uaep.ui.home.HomeFeedScreen
import com.example.uaep.ui.home.HomeUiState
import com.example.uaep.uitmp.UaepTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {
            UaepTheme() {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background,
                ) {
//                    LoginView()
//                    SignUpView()

                    HomeFeedScreen(
                        uiState = HomeUiState.HasPosts(
                            roomsFeed = rooms,
                            isLoading = false,
                            errorMessages = emptyList(),
                        ),
                        showTopAppBar = true,
                        onSelectPost = {},
                        onRefreshPosts = {},
                        onErrorDismiss = {},
                        openDrawer = {},
                        homeListLazyListState = rememberLazyListState(),
                        scaffoldState = rememberScaffoldState(),
                    )
                }
            }
        }
    }
}