package com.example.uaep.ui.review

import android.content.res.Configuration
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.uaep.ui.components.CommonTopAppBar
import com.example.uaep.ui.navigate.BottomNavigationBar
import com.example.uaep.ui.theme.UaepTheme

@ExperimentalMaterial3Api
@Composable
fun ReviewScreen(
    showTopAppBar: Boolean,
    openDrawer: () -> Unit,
    navController: NavHostController,
    onSelectPost: (String) -> Unit
) {
    ReviewWithList(
        showTopAppBar = showTopAppBar,
        openDrawer = openDrawer,
        navController = navController,
        onSelectPost = onSelectPost
    )
}

@ExperimentalMaterial3Api
@Composable
fun ReviewWithList(
    showTopAppBar: Boolean,
    openDrawer: () -> Unit,
    navController: NavController,
    onSelectPost: (String) -> Unit
){
    Scaffold (
        topBar = {
            if (showTopAppBar) {
                CommonTopAppBar(
                    openDrawer = openDrawer,
                    navController = navController
                )
            }
        },
        bottomBar = {
            BottomNavigationBar(navController = navController, onSelectPost = onSelectPost)
        }
    ) {
        Text("ReviewList", color = Color.Red)
    }
}

@ExperimentalMaterial3Api
@Preview(
    name = "Light Mode",
    showBackground = true
)
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    name = "Dark Mode",
    showBackground = true
)
@Composable
fun PreviewReviewScreen() {
    UaepTheme {
        ReviewScreen(
            showTopAppBar = true,
            openDrawer = {},
            navController = rememberNavController(),
            onSelectPost={}
        )
    }
}