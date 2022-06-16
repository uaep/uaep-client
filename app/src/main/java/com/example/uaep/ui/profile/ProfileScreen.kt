@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.uaep.ui.profile

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.uaep.dto.UserDto
import com.example.uaep.ui.components.CommonTopAppBar
import com.example.uaep.ui.components.ErrorDialog
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
    viewModel.updateProvince(userDto.province)
    viewModel.updateTown(userDto.town)
    viewModel.updateGender(userDto.gender)
    viewModel.updateLevel(userDto.level)
    viewModel.updatePositionChangePoint(userDto.positionChangePoint)

    var openDialog by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    if (openDialog)
        ErrorDialog(
            onError = { openDialog = false },
            errorMessage = errorMessage
        )

    Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState())) {
        Scaffold(
            Modifier.height(800.dp),
            topBar = {
                CommonTopAppBar(navController = navController, openDrawer = {})
            },
            bottomBar = {
                BottomNavigationBar(navController = navController)
            }
        ) {
            Column(
                modifier = Modifier.padding(it)
            ) {
                Spacer(modifier = Modifier.padding(vertical = 5.dp))
                ProfileCard(
                    ProfileDto(
                        name = viewModel.name.value,
                        position = viewModel.position.value,
                        province = viewModel.province.value,
                        town = viewModel.town.value,
                        gender = viewModel.gender.value,
                        level = viewModel.level.value,
                        positionChangePoint = viewModel.positionChangePoint.value
                    ),
                    onUpdateUserInfo = { userUpdateDto ->
                        viewModel.updateProfile(userUpdateDto)
                    },
                    onError = { openDialog = true },
                    onErrorMessage = { msg ->
                        errorMessage = msg
                    }
                )
            }
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
            province = "서울",
            town = "강남구",
            position = "GK",
            level = "비기너1",
            positionChangePoint = 1000,
            games = emptyList()
        )
        ProfileScreen(
            userDto = userDto,
            navController = rememberNavController()
        )
    }
}