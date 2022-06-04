@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.uaep.ui.profile

import android.content.res.Configuration
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.uaep.R
import com.example.uaep.dto.UserDto
import com.example.uaep.ui.navigate.Screen
import com.example.uaep.ui.theme.UaepTheme

@Composable
fun ProfileScreen(
    userDto: UserDto,
    viewModel: ProfileViewModel = ProfileViewModel(),
    navController: NavController
) {
    viewModel.updateName(userDto.name)
    viewModel.updatePosition(userDto.position)
    viewModel.updateAddress(userDto.address)
    viewModel.updateGender(userDto.gender)

    Scaffold(
        containerColor = MaterialTheme.colorScheme.onBackground,
        contentColor = MaterialTheme.colorScheme.onBackground,
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.app_name),
                        fontWeight = FontWeight.ExtraBold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.navigate(route = Screen.Home.route) }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Back to Home"
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onBackground,
                    titleContentColor = MaterialTheme.colorScheme.onBackground
                )
            )
        },
        bottomBar = {
        }
    ) {
        ProfileCard(
            ProfileDto(
                name = viewModel.name.value,
                position = viewModel.position.value,
                address = viewModel.address.value,
                gender = viewModel.gender.value
            ),
            onUpdateUserInfo = { userUpdateDto ->
                viewModel.updateProfile(userUpdateDto)
            }
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
        val userDto = UserDto(
            email = "",
            name = "김광진",
            gender = "남성",
            address = "경기도 수원시 중부대로",
            position = "GK",
            levelPoint = 1,
            positionChangePoint = 1000,
            games = emptyList()
        )
        ProfileScreen(
            userDto = userDto,
            navController = rememberNavController()
        )
    }
}