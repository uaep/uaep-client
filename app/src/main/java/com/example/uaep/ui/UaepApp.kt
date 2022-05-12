package com.example.uaep.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalDrawer
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.uaep.ui.Navi.UaepDestinations
import com.example.uaep.ui.Navi.UaepNavGraph
import com.example.uaep.ui.Navi.UaepNavigationActions
import com.example.uaep.uitmp.UaepTheme
import com.example.uaep.utils.WindowSize
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.launch

@Composable
fun UaepApp(
    windowSize: WindowSize
) {
    UaepTheme{

        val navController = rememberNavController()
        val isExpandedScreen = windowSize == WindowSize.Expanded

        UaepNavGraph(
            isExpandedScreen = isExpandedScreen,
            navController = navController,
            //openDrawer = { coroutineScope.launch { sizeAwareDrawerState.open() } },
        )


    }
}
@Composable
fun rememberContentPaddingForScreen(additionalTop: Dp = 0.dp) =
    WindowInsets.systemBars
        .only(WindowInsetsSides.Bottom)
        .add(WindowInsets(top = additionalTop))
        .asPaddingValues()