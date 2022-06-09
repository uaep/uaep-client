package com.example.uaep.ui.review

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController


@Composable
fun ReviewRoute(
    reviewViewModel: ReviewViewModel,
    isExpandedScreen: Boolean,
    openDrawer: () -> Unit,
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    navController: NavHostController
) {
    // UiState of the HomeScreen
    val uiState by reviewViewModel.uiState.collectAsState()

    ReviewRoute(
        uiState = uiState,
        isExpandedScreen = isExpandedScreen,
        onSelectRoom = { reviewViewModel.selectArticle(it) },
        onRefreshPosts = { reviewViewModel.refreshPosts() },
        onErrorDismiss = { reviewViewModel.errorShown(it) },
        onInteractWithFeed = { reviewViewModel.interactedWithFeed() },
        openDrawer = openDrawer,
        scaffoldState = scaffoldState,
        navController = navController
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ReviewRoute(
    uiState: ReviewUiState,
    isExpandedScreen: Boolean,
    onSelectRoom: (String) -> Unit,
    onRefreshPosts: () -> Unit,
    onErrorDismiss: (Long) -> Unit,
    onInteractWithFeed: () -> Unit,
    openDrawer: () -> Unit,
    scaffoldState: ScaffoldState,
    navController : NavHostController
) {
    val homeListLazyListState = rememberLazyListState()


    val homeScreenType = getHomeScreenType(isExpandedScreen, uiState)
    when (homeScreenType) {
        ReviewScreenType.Feed -> {
            ReviewFeedScreen(
                uiState = uiState,
                showTopAppBar = !isExpandedScreen,
                onSelectPost = onSelectRoom,
                onRefreshPosts = onRefreshPosts,
                onErrorDismiss = onErrorDismiss,
                openDrawer = openDrawer,
                reviewListLazyListState = homeListLazyListState,
                scaffoldState = scaffoldState,
                navController = navController
            )
        }
        ReviewScreenType.ArticleDetails -> {
            check(uiState is ReviewUiState.HasPosts)

            ReviewRoomScreen(
                room = uiState.selectedRoom,
                isExpandedScreen = isExpandedScreen,
                onBack = onInteractWithFeed,
                onRefresh = onSelectRoom,
                navController = navController
            )


            BackHandler {
                onInteractWithFeed()
            }
        }
    }
}

private enum class ReviewScreenType {
    Feed,
    ArticleDetails
}

@Composable
private fun getHomeScreenType(
    isExpandedScreen: Boolean,
    uiState: ReviewUiState
): ReviewScreenType = when (isExpandedScreen) {
    false -> {
        when (uiState) {
            is ReviewUiState.HasPosts -> {
                if (uiState.isArticleOpen) {
                    ReviewScreenType.ArticleDetails
                } else {
                    ReviewScreenType.Feed
                }
            }
            is ReviewUiState.NoPosts -> ReviewScreenType.Feed
        }
    }
    true -> ReviewScreenType.Feed
}