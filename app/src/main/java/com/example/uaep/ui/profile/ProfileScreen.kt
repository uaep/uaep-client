package com.example.uaep.ui.profile

import android.content.res.Configuration
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.uaep.dto.UserDto
import com.example.uaep.ui.theme.UaepTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    userDto: UserDto,
    viewModel: ProfileViewModel = ProfileViewModel()
) {
    viewModel.updateName(userDto.name)
    viewModel.updatePosition(userDto.position)
    viewModel.updateAddress(userDto.address)
    viewModel.updateGender(userDto.gender)

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
//        ProfileScreen()
    }
}