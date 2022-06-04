package com.example.uaep.ui

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.add
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.systemBars
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.example.uaep.ui.navigate.UaepNavGraph
import com.example.uaep.ui.theme.UaepTheme
import com.example.uaep.utils.WindowSize

@Composable
fun UaepApp(
    windowSize: WindowSize
) {
    UaepTheme {

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