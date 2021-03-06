package com.example.uaep.ui.review

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.uaep.R
import com.example.uaep.data.rooms
import com.example.uaep.dto.RoomDto
import com.example.uaep.model.Room
import com.example.uaep.ui.components.CommonTopAppBar
import com.example.uaep.ui.components.UaepSnackbarHost
import com.example.uaep.ui.home.RoomCardSimple
import com.example.uaep.ui.navigate.BottomNavigationBar
import com.example.uaep.ui.navigate.Screen
import com.example.uaep.ui.rememberContentPaddingForScreen
import com.example.uaep.ui.theme.UaepTheme
import com.example.ueap.model.RoomsFeed
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import java.util.*

@Composable
fun ReviewFeedScreen(
    uiState: ReviewUiState,
    showTopAppBar: Boolean,
    onSelectPost: (String) -> Unit,
    onRefreshPosts: () -> Unit,
    onErrorDismiss: (Long) -> Unit,
    openDrawer: () -> Unit,
    reviewListLazyListState: LazyListState,
    scaffoldState: ScaffoldState,
    modifier: Modifier = Modifier,
    navController : NavController
) {
    ReviewScreenWithList(
        uiState = uiState,
        showTopAppBar = showTopAppBar,
        onRefreshPosts = onRefreshPosts,
        onErrorDismiss = onErrorDismiss,
        openDrawer = openDrawer,
        reviewListLazyListState = reviewListLazyListState,
        scaffoldState = scaffoldState,
        modifier = modifier,
        navController = navController,
        onSelectPost=onSelectPost
    ) { hasPostsUiState, contentModifier ->
        ReviewList(
            roomsFeed = hasPostsUiState.roomsFeed,
            onArticleTapped = onSelectPost,
            contentPadding = rememberContentPaddingForScreen(
                additionalTop = if (showTopAppBar) 0.dp else 8.dp
            ),
            modifier = contentModifier,
            state = reviewListLazyListState
        )
    }
}

@Composable
private fun ReviewScreenWithList(
    uiState: ReviewUiState,
    showTopAppBar: Boolean,
    onRefreshPosts: () -> Unit,
    onErrorDismiss: (Long) -> Unit,
    openDrawer: () -> Unit,
    reviewListLazyListState: LazyListState,
    scaffoldState: ScaffoldState,
    modifier: Modifier = Modifier,
    navController : NavController,
    onSelectPost: (String)->Unit,
    hasPostsContent: @Composable (
        uiState: ReviewUiState.HasPosts,
        modifier: Modifier
    ) -> Unit
) {
    Scaffold(
        scaffoldState = scaffoldState,
        snackbarHost = { UaepSnackbarHost(hostState = it) },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                text = {
                    Text(
                        text = stringResource(id = R.string.create_room),
                        color = MaterialTheme.colorScheme.onBackground,
                        fontFamily = FontFamily(Font(R.font.jua_regular)),
                        fontWeight = FontWeight.ExtraBold
                    )
                },
                icon = { Icon(Icons.Filled.AddCircle, stringResource(id = R.string.create_room)) },
                onClick = { navController.navigate(Screen.MatchCreation.route) },
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            )
        },
        topBar = {
            if (showTopAppBar) {
                CommonTopAppBar(
                    openDrawer = openDrawer,
                    navController = navController
                )
            }
        },
        modifier = modifier,
        bottomBar = {
            BottomNavigationBar(navController = navController)
        }
    ) { innerPadding ->
        val contentModifier = Modifier.padding(innerPadding)

        LoadingContent(
            empty = when (uiState) {
                is ReviewUiState.HasPosts -> false
                is ReviewUiState.NoPosts -> uiState.isLoading
            },
            emptyContent = { FullScreenLoading() },
            loading = uiState.isLoading,
            onRefresh = onRefreshPosts,
            content = {
                when (uiState) {
                    is ReviewUiState.HasPosts -> hasPostsContent(uiState, contentModifier)
                    is ReviewUiState.NoPosts -> {
                        if (uiState.errorMessages.isEmpty()) {

                            TextButton(
                                onClick = onRefreshPosts,
                                modifier.fillMaxSize()
                            ) {
                                Text(
                                    stringResource(id = R.string.home_tap_to_load_content),
                                    textAlign = TextAlign.Center
                                )
                            }
                        } else {

                            Box(contentModifier.fillMaxSize()) { /* empty screen */ }
                        }
                    }
                }
            }
        )
    }

    if (uiState.errorMessages.isNotEmpty()) {

        val errorMessage = remember(uiState) { uiState.errorMessages[0] }

        val errorMessageText: String = stringResource(errorMessage.messageId)
        val retryMessageText = stringResource(id = R.string.retry)

        val onRefreshPostsState by rememberUpdatedState(onRefreshPosts)
        val onErrorDismissState by rememberUpdatedState(onErrorDismiss)

        LaunchedEffect(errorMessageText, retryMessageText, scaffoldState) {
            val snackbarResult = scaffoldState.snackbarHostState.showSnackbar(
                message = errorMessageText,
                actionLabel = retryMessageText
            )
            if (snackbarResult == SnackbarResult.ActionPerformed) {
                onRefreshPostsState()
            }

            onErrorDismissState(errorMessage.id)
        }
    }
}



@Composable
private fun LoadingContent(
    empty: Boolean,
    emptyContent: @Composable () -> Unit,
    loading: Boolean,
    onRefresh: () -> Unit,
    content: @Composable () -> Unit
) {
    if (empty) {
        emptyContent()
    } else {
        SwipeRefresh(
            state = rememberSwipeRefreshState(loading),
            onRefresh = onRefresh,
            content = content,
        )
    }
}

@Composable
private fun FullScreenLoading() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center)
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun ReviewList(
    roomsFeed: RoomsFeed,
    onArticleTapped: (postId: String) -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    state: LazyListState = rememberLazyListState(),
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = contentPadding,
        state = state
    ) {
        item {
            PostListSimpleSection(
                roomsFeed.data,
                onArticleTapped
            )
        }
    }
}


@Composable
private fun PostListSimpleSection(
    rooms: List<Room>,
    navigateToArticle: (String) -> Unit
) {
    Column {
        rooms.forEach { room ->
            RoomCardSimple(
                room = room,
                navigateToArticle = navigateToArticle
            )
            PostListDivider()
        }
    }
}

@Composable
private fun PostListDivider() {
    Divider(
        modifier = Modifier.padding(horizontal = 14.dp),
        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.08f)
    )
}

@Composable
private fun HomeBottomAppBar(

) {

}

@Preview("Home list drawer screen")
@Composable
fun PreviewHomeListDrawerScreen() {

    UaepTheme {
        ReviewFeedScreen(
            uiState = ReviewUiState.HasPosts(
                roomsFeed = rooms,
                isLoading = false,
                errorMessages = emptyList(),
                isArticleOpen = false,
                selectedRoom = RoomDto("-1", Date(0,0,0,0,0),"Wrong Page","6vs6","-", "-", null, null, null, null, null)
            ),
            showTopAppBar = true,
            onSelectPost = {},
            onRefreshPosts = {},
            onErrorDismiss = {},
            openDrawer = {},
            reviewListLazyListState = rememberLazyListState(),
            scaffoldState = rememberScaffoldState(),
            navController = rememberNavController()
        )
    }
}
