package com.example.uaep.ui.profile

import android.content.res.Configuration
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.uaep.ui.theme.UaepTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen() {
    Scaffold(
        containerColor = MaterialTheme.colorScheme.onBackground,
        contentColor = MaterialTheme.colorScheme.onBackground,
        topBar = {
        },
        bottomBar = {
        }
    ) {
        ProfileCard(
            ProfileDto(
                name = "김광진",
                position = "FK",
                address = "경기도 수원시 00대로",
                gender = "남자"
            )
        )
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
fun PreviewProfileScreen() {
    UaepTheme {
        ProfileScreen()
    }
}