package com.example.uaep.ui.components

import android.util.Log
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import com.example.uaep.R
import com.example.uaep.dto.ErrorResponse
import com.example.uaep.dto.UserDto
import com.example.uaep.network.UserApiService
import com.example.uaep.ui.navigate.Screen
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun CommonTopAppBar(
    openDrawer: () -> Unit,
    navController: NavController
) {
    CenterAlignedTopAppBar(
        title = {
            Text(
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
                                Log.i("profile_enter", response.raw().toString())
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