package com.example.uaep.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.uaep.presentation.signup.AuthCodeScreen
import com.example.uaep.presentation.signup.EmailAuthScreen
import com.example.uaep.presentation.signup.SignUpScreen
import com.example.uaep.presentation.login.LoginScreen
import com.example.uaep.presentation.signup.AuthCodeViewModel
import com.example.uaep.presentation.signup.EmailAuthViewModel
import com.example.uaep.presentation.login.LoginViewModel
import com.example.uaep.presentation.signup.SignUpViewModel

@Composable
fun SetupNavGraph(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Login.route
    ) {
        composable(
            route = Screen.Login.route
        ) {
            LoginScreen(
                vm = LoginViewModel(),
                navController = navController
            )
        }
        composable(
            route = Screen.EmailAuth.route
        ) {
            EmailAuthScreen(
                vm = EmailAuthViewModel(),
                navController = navController
            )
        }
        composable(
            route = Screen.AuthCode.route,
            arguments = listOf(navArgument(EMAIL) {
                type = NavType.StringType
            })
        ) {
            AuthCodeScreen(
                vm = AuthCodeViewModel(),
                navController = navController,
                email = it.arguments?.getString("email").toString()
            )
        }
        composable(
            route = Screen.SignUp.route,
            arguments = listOf(navArgument(EMAIL) {
                type = NavType.StringType
            })
        ) {
            SignUpScreen(
                vm = SignUpViewModel(),
                navController = navController,
                email = it.arguments?.getString("email").toString()
            )
        }
    }
}