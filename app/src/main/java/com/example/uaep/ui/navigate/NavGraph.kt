package com.example.uaep.ui.navigate

import android.util.Log
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.uaep.dto.UserDto
import com.example.uaep.ui.home.HomeRoute
import com.example.uaep.ui.home.HomeViewModel
import com.example.uaep.ui.login.LoginScreen
import com.example.uaep.ui.login.LoginViewModel
import com.example.uaep.ui.match.MatchCreationScreen
import com.example.uaep.ui.profile.ProfileScreen
import com.example.uaep.ui.rank.RankingScreen
import com.example.uaep.ui.review.ReviewRoute
import com.example.uaep.ui.review.ReviewViewModel
import com.example.uaep.ui.signup.AuthCodeScreen
import com.example.uaep.ui.signup.AuthCodeViewModel
import com.example.uaep.ui.signup.EmailAuthScreen
import com.example.uaep.ui.signup.EmailAuthViewModel
import com.example.uaep.ui.signup.SignUpScreen
import com.example.uaep.ui.signup.SignUpViewModel
import com.google.gson.Gson

@ExperimentalMaterial3Api
@Composable
fun UaepNavGraph(
    isExpandedScreen: Boolean,
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    openDrawer: () -> Unit = {},
    startDestination: String = Screen.Login.route
) {

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {

        val participating =
            mutableStateOf(false)

        val auto =
            mutableStateOf(false)

        composable(route = Screen.Login.route) {
            LoginScreen(
                vm = LoginViewModel(),
                navController = navController
            )
        }
        composable(route = Screen.EmailAuth.route) {
            EmailAuthScreen(
                vm = EmailAuthViewModel(),
                navController = navController
            )
        }
        composable(
            route = Screen.AuthCode.route,
            arguments = listOf(
                navArgument(EMAIL) {
                    type = NavType.StringType
                },
                navArgument(TOKEN) {
                    type = NavType.StringType
                }
            )
        ) {
            AuthCodeScreen(
                vm = AuthCodeViewModel(),
                navController = navController,
                email = it.arguments?.getString("email").toString(),
                token = it.arguments?.getString("token").toString()
            )
        }
        composable(
            route = Screen.SignUp.route,
            arguments = listOf(
                navArgument(EMAIL) {
                    type = NavType.StringType
                },
                navArgument(TOKEN) {
                    type = NavType.StringType
                }
            )
        ) {
            SignUpScreen(
                vm = SignUpViewModel(),
                navController = navController,
                email = it.arguments?.getString("email").toString(),
                token = it.arguments?.getString("token").toString()
            )
        }
        composable(route = Screen.Home.route) {
            participating.value = false
            auto.value = false
            val homeViewModel: HomeViewModel = viewModel(
                factory = HomeViewModel.provideFactory(false, false)
            )
            HomeRoute(
                homeViewModel = homeViewModel,
                isExpandedScreen = isExpandedScreen,
                openDrawer = { /*TODO*/ },
                navController = navController,
                participating = participating.value,
                auto = auto.value
            )
        }
        composable(
            route = Screen.Profile.route,
            arguments = listOf(navArgument("user"){
                type = NavType.StringType
            })
        ) {
            val userJson = it.arguments?.getString("user")
            val userDto = Gson().fromJson(userJson, UserDto::class.java)
            Log.i("profile_enter_navi",userJson ?: "none")
            ProfileScreen(
                userDto = userDto,
                navController = navController
            )
        }
        composable(
            route = Screen.MatchCreation.route,
        ) {
            MatchCreationScreen(
                navController = navController
            )
        }
        composable(
            route = BottomNavItem.Review.route
        ) {
            val reviewViewModel: ReviewViewModel = viewModel(
                factory = ReviewViewModel.provideFactory()
            )
            ReviewRoute(
                reviewViewModel = reviewViewModel,
                isExpandedScreen = isExpandedScreen,
                openDrawer = { /*TODO*/ },
                navController = navController
            )
        }
        composable(
            route = BottomNavItem.Participating.route
        ) {
            participating.value = true
            auto.value = false
            val homeViewModel: HomeViewModel = viewModel(
                factory = HomeViewModel.provideFactory(true, false)
            )
            HomeRoute(
                homeViewModel = homeViewModel,
                isExpandedScreen = isExpandedScreen,
                openDrawer = { /*TODO*/ },
                navController = navController,
                participating = participating.value,
                auto = auto.value
            )
        }

        composable(
            route = BottomNavItem.Ranking.route
        ) {
            RankingScreen(
                navController = navController
            )
        }

        composable(
            route = BottomNavItem.AutoMatching.route
        ) {
            participating.value = false
            auto.value = true
            val homeViewModel: HomeViewModel = viewModel(
                factory = HomeViewModel.provideFactory(false, true)
            )
            HomeRoute(
                homeViewModel = homeViewModel,
                isExpandedScreen = isExpandedScreen,
                openDrawer = { /*TODO*/ },
                navController = navController,
                participating = participating.value,
                auto = auto.value
            )
        }
    }
}