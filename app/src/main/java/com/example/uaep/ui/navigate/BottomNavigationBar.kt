package com.example.uaep.ui.navigate

import android.content.res.Configuration
import androidx.compose.material.Text
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.uaep.R
import com.example.uaep.ui.theme.UaepTheme



@Composable
fun BottomNavigationBar(navController: NavController) {
    val bottomNavItems = listOf(
        BottomNavItem.Participate,
        BottomNavItem.Review,
        BottomNavItem.Match
    )

    NavigationBar(
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.surface
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        bottomNavItems.forEachIndexed { index, bottomNavItem ->
            NavigationBarItem(
                icon = { Icon(bottomNavItem.icon, contentDescription = null) },
                label = { Text(
                    text = bottomNavItem.title,
                    color = if (bottomNavItems[index].route == currentRoute) MaterialTheme.colorScheme.surface else Color.DarkGray,
                    fontFamily = FontFamily(Font(R.font.jua_regular)),
                    fontWeight = FontWeight.ExtraBold
                ) },
                selected = bottomNavItems[index].route == currentRoute,
                onClick = {
                    navController.navigate(bottomNavItems[index].route)
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.secondary,
                    selectedTextColor = MaterialTheme.colorScheme.surface,
                )
            )
        }
    }
}

@Preview(
    name = "Light Mode",
    showBackground = true
)
@Preview(
    name = "Dark Mode",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun PreviewBottomNavigationBar() {
    UaepTheme {
        BottomNavigationBar(navController = rememberNavController())
    }
}