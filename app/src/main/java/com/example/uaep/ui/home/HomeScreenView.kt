package com.example.uaep.ui.home

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.SnackbarResult
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
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
import com.example.uaep.dto.ErrorResponse
import com.example.uaep.dto.UserDto
import com.example.uaep.model.Room
import com.example.uaep.network.UserApiService
import com.example.uaep.ui.components.UaepSnackbarHost
import com.example.uaep.ui.navigate.BottomNavigationBar
import com.example.uaep.ui.navigate.Screen
import com.example.uaep.ui.rememberContentPaddingForScreen
import com.example.uaep.ui.theme.UaepTheme
import com.example.ueap.model.RoomsFeed
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun HomeFeedScreen(
    uiState: HomeUiState,
    showTopAppBar: Boolean,
    onSelectPost: (String) -> Unit,
    onRefreshPosts: () -> Unit,
    onErrorDismiss: (Long) -> Unit,
    openDrawer: () -> Unit,
    homeListLazyListState: LazyListState,
    scaffoldState: ScaffoldState,
    modifier: Modifier = Modifier,
    navController : NavController
) {
    HomeScreenWithList(
        uiState = uiState,
        showTopAppBar = showTopAppBar,
        onRefreshPosts = onRefreshPosts,
        onErrorDismiss = onErrorDismiss,
        openDrawer = openDrawer,
        homeListLazyListState = homeListLazyListState,
        scaffoldState = scaffoldState,
        modifier = modifier,
        navController = navController
    ) { hasPostsUiState, contentModifier ->
        PostList(
            roomsFeed = hasPostsUiState.roomsFeed,
            onArticleTapped = onSelectPost,
            contentPadding = rememberContentPaddingForScreen(
                additionalTop = if (showTopAppBar) 0.dp else 8.dp
            ),
            modifier = contentModifier,
            state = homeListLazyListState
        )
    }
}

@Composable
private fun HomeScreenWithList(
    uiState: HomeUiState,
    showTopAppBar: Boolean,
    onRefreshPosts: () -> Unit,
    onErrorDismiss: (Long) -> Unit,
    openDrawer: () -> Unit,
    homeListLazyListState: LazyListState,
    scaffoldState: ScaffoldState,
    modifier: Modifier = Modifier,
    navController : NavController,
    hasPostsContent: @Composable (
        uiState: HomeUiState.HasPosts,
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
                HomeTopAppBar(
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
                is HomeUiState.HasPosts -> false
                is HomeUiState.NoPosts -> uiState.isLoading
            },
            emptyContent = { FullScreenLoading() },
            loading = uiState.isLoading,
            onRefresh = onRefreshPosts,
            content = {
                when (uiState) {
                    is HomeUiState.HasPosts -> hasPostsContent(uiState, contentModifier)
                    is HomeUiState.NoPosts -> {
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
private fun PostList(
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
private fun HomeTopAppBar(
    openDrawer: () -> Unit,
    navController: NavController
) {
    CenterAlignedTopAppBar(
        title = {
            androidx.compose.material3.Text(
                text = stringResource(id = R.string.app_name),
                fontWeight = FontWeight.ExtraBold
            )
        },
        navigationIcon = {
            IconButton(onClick = openDrawer) {
                Icon(
                    imageVector = Icons.Filled.Menu,
                    contentDescription = stringResource(R.string.cd_open_navigation_drawer),
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }
        },
        actions = {
            IconButton(
                onClick = {
                    // TODO: HTTP, Navigation
                    UserApiService.getInstance().getUser().enqueue(object:
                        Callback<UserDto> {
                        override fun onResponse(
                            call: Call<UserDto>,
                            response: Response<UserDto>
                        ) {
                            if (response.isSuccessful) {
                                val userJson = Gson().toJson(response.body())
                                navController.navigate(Screen.Profile.route.replace("{user}", userJson))
                            } else {
                                val errorResponse: ErrorResponse? =
                                    Gson().fromJson(
                                        response.errorBody()!!.charStream(),
                                        object : TypeToken<ErrorResponse>() {}.type
                                    )
                            }
                        }
                        override fun onFailure(call: Call<UserDto>, t: Throwable) {
                            Log.i("test", "실패$t")
                        }
                    })
                }
            ) {
                Icon(
                    imageVector = Icons.Filled.AccountCircle,
                    contentDescription = stringResource(R.string.cd_profile),
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }

        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            navigationIconContentColor = MaterialTheme.colorScheme.onBackground,
            titleContentColor = MaterialTheme.colorScheme.onBackground,
            actionIconContentColor = MaterialTheme.colorScheme.onBackground
        )
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
        HomeFeedScreen(
            uiState = HomeUiState.HasPosts(
                roomsFeed = rooms,
                isLoading = false,
                errorMessages = emptyList(),
                isArticleOpen = false,
                selectedRoom = rooms.data[0]
            ),
            showTopAppBar = true,
            onSelectPost = {},
            onRefreshPosts = {},
            onErrorDismiss = {},
            openDrawer = {},
            homeListLazyListState = rememberLazyListState(),
            scaffoldState = rememberScaffoldState(),
            navController = rememberNavController()
        )
    }
}
