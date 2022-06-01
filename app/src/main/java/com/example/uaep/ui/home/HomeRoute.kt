package com.example.uaep.ui.home

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.uaep.ui.match.MatchScreen
import com.example.uaep.ui.match.RoomContainer

@Composable
fun HomeRoute(
    homeViewModel: HomeViewModel,
    isExpandedScreen: Boolean,
    openDrawer: () -> Unit,
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    navController: NavHostController
) {
    // UiState of the HomeScreen
    val uiState by homeViewModel.uiState.collectAsState()

    HomeRoute(
        uiState = uiState,
        isExpandedScreen = isExpandedScreen,
        onSelectRoom = { homeViewModel.selectArticle(it) },
        onRefreshPosts = { homeViewModel.refreshPosts() },
        onErrorDismiss = { homeViewModel.errorShown(it) },
        onInteractWithFeed = { homeViewModel.interactedWithFeed() },
        openDrawer = openDrawer,
        scaffoldState = scaffoldState,
        navController = navController
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeRoute(
    uiState: HomeUiState,
    isExpandedScreen: Boolean,
    onSelectRoom: (String) -> Unit,
    onRefreshPosts: () -> Unit,
    onErrorDismiss: (Long) -> Unit,
    onInteractWithFeed: () -> Unit,
    openDrawer: () -> Unit,
    scaffoldState: ScaffoldState,
    navController : NavHostController
) {
    // Construct the lazy list states for the list and the details outside of deciding which one to
    // show. This allows the associated state to survive beyond that decision, and therefore
    // we get to preserve the scroll throughout any changes to the content.
    val homeListLazyListState = rememberLazyListState()


    val homeScreenType = getHomeScreenType(isExpandedScreen, uiState)
    when (homeScreenType) {
        HomeScreenType.Feed -> {
            HomeFeedScreen(
                uiState = uiState,
                showTopAppBar = !isExpandedScreen,
                onSelectPost = onSelectRoom,
                onRefreshPosts = onRefreshPosts,
                onErrorDismiss = onErrorDismiss,
                openDrawer = openDrawer,
                homeListLazyListState = homeListLazyListState,
                scaffoldState = scaffoldState,
                navController = navController
            )
        }
        HomeScreenType.ArticleDetails -> {
            // Guaranteed by above condition for home screen type
            check(uiState is HomeUiState.HasPosts)

            MatchScreen(
                room = uiState.selectedRoom,
                isExpandedScreen = isExpandedScreen,
                onBack = onInteractWithFeed
            )

            // If we are just showing the detail, have a back press switch to the list.
            // This doesn't take anything more than notifying that we "interacted with the list"
            // since that is what drives the display of the feed
            BackHandler {
                onInteractWithFeed()
            }
        }
    }
}

private enum class HomeScreenType {
    Feed,
    ArticleDetails
}

@Composable
private fun getHomeScreenType(
    isExpandedScreen: Boolean,
    uiState: HomeUiState
): HomeScreenType = when (isExpandedScreen) {
    false -> {
        when (uiState) {
            is HomeUiState.HasPosts -> {
                if (uiState.isArticleOpen) {
                    HomeScreenType.ArticleDetails
                } else {
                    HomeScreenType.Feed
                }
            }
            is HomeUiState.NoPosts -> HomeScreenType.Feed
        }
    }
    true -> HomeScreenType.Feed
}