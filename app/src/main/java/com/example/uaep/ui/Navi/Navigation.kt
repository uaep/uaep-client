package com.example.uaep.ui.Navi

import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController

object UaepDestinations {
    const val HOME_ROUTE = "home"
}


class UaepNavigationActions(navController: NavHostController) {
    val navigateToHome: () -> Unit = {
        navController.navigate(UaepDestinations.HOME_ROUTE) {
            // Pop up to the start destination of the graph to
            // avoid building up a large stack of destinations
            // on the back stack as users select items
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            // Avoid multiple copies of the same destination when
            // reselecting the same item
            launchSingleTop = true
            // Restore state when reselecting a previously selected item
            restoreState = true
        }
    }

}
