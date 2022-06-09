@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.uaep.ui.profile

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.uaep.dto.UserDto
import com.example.uaep.ui.components.CommonTopAppBar
import com.example.uaep.ui.navigate.BottomNavigationBar
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
    viewModel.updateLevelPoint(userDto.levelPoint)
    viewModel.updatePositionChangePoint(userDto.positionChangePoint)

    Scaffold(
        topBar = {
           CommonTopAppBar(navController = navController, openDrawer = {})
        },
        bottomBar = {
            BottomNavigationBar(navController = navController,{})
        }
    ) {
        Column(
            modifier = Modifier.padding(it)
        ){
            Spacer(modifier = Modifier.padding(vertical = 5.dp))
            ProfileCard(
                ProfileDto(
                    name = viewModel.name.value,
                    position = viewModel.position.value,
                    address = viewModel.address.value,
                    gender = viewModel.gender.value,
                    levelPoint = viewModel.levelPoint.value,
                    positionChangePoint = viewModel.positionChangePoint.value
                ),
                onUpdateUserInfo = { userUpdateDto ->
                    viewModel.updateProfile(userUpdateDto)
                }
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