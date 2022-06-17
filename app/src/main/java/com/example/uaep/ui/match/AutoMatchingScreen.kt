package com.example.uaep.ui.match

import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.uaep.dto.DummyResponse
import com.example.uaep.dto.ErrorResponse
import com.example.uaep.dto.FormationRequestDto
import com.example.uaep.dto.RoomDto
import com.example.uaep.model.Room
import com.example.uaep.network.AuthService
import com.example.uaep.network.CookieChanger
import com.example.uaep.network.ReAuthService
import com.example.uaep.ui.components.CommonTopAppBar
import com.example.uaep.ui.navigate.BottomNavItem
import com.example.uaep.ui.navigate.BottomNavigationBar
import com.example.uaep.ui.theme.UaepTheme
import com.example.uaep.ui.theme.md_theme_light_primary
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


@Composable
fun AutoMatchingScreen(
    showTopAppBar: Boolean,
    openDrawer: () -> Unit,
    navController: NavController,
    onSelectRoom: (String) -> Unit
) {
    AutoMatchingWithList(
        showTopAppBar = showTopAppBar,
        openDrawer = openDrawer,
        navController = navController,
        onSelectRoom = onSelectRoom
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AutoMatchingWithList(
    showTopAppBar: Boolean,
    openDrawer: () -> Unit,
    navController: NavController,
    onSelectRoom: (String) -> Unit
){
    Scaffold (
        topBar = {
            if (showTopAppBar) {
                CommonTopAppBar(
                    openDrawer = openDrawer,
                    navController = navController
                )
            }
        },
        bottomBar = {
            BottomNavigationBar(navController = navController)
        }
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colorScheme.inverseOnSurface),
                onClick = {

                    AuthService.getInstance().getRecommend().enqueue(object :
                        Callback<Room> {
                        override fun onResponse(
                            call: Call<Room>,
                            response: Response<Room>
                        ) {
                            if (response.isSuccessful) {
                                Log.i("Auto_Matching", response.body().toString())
                                if (response.body() != null && response.body()!!.id != null)
                                    onSelectRoom(response.body()!!.id)
                            } else {
                                Log.i("Auto_Matching_fail_raw", response.raw().toString())
                                Log.i("Auto_Matching_fail_head", response.headers().toString())

                                val errorResponse: ErrorResponse? =
                                    Gson().fromJson(
                                        response.errorBody()!!.charStream(),
                                        object : TypeToken<ErrorResponse>() {}.type
                                    )
                                if (errorResponse!!.message != null && (errorResponse!!.statusCode == 401)) {
                                    ReAuthService.getInstance().reauth().enqueue(object :
                                        Callback<DummyResponse> {

                                        override fun onResponse(
                                            call: Call<DummyResponse>,
                                            response: Response<DummyResponse>
                                        ) {
                                            if (response.isSuccessful) {
                                                val tokens =
                                                    CookieChanger<DummyResponse>().change(response)
                                                AuthService.getCookieJar().saveToken(tokens)
                                            }
                                        }

                                        override fun onFailure(
                                            call: Call<DummyResponse>,
                                            t: Throwable
                                        ) {
                                            Log.i("test", "실패$t")
                                        }
                                    })
                                }
                            }
                        }

                        override fun onFailure(call: Call<Room>, t: Throwable) {
                            Log.i("test", "실패$t")
                        }
                    })

                }
            ) {
                Text(
                    "Auto_Matching",
                    color = md_theme_light_primary,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.ExtraBold
                )

            }
        }
    }
}

@ExperimentalMaterial3Api
@Preview(
    name = "Light Mode",
    showBackground = true
)
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    name = "Dark Mode",
    showBackground = true
)
@Composable
fun PreviewParticipatingScreen() {
    UaepTheme {
        AutoMatchingScreen(
            showTopAppBar = true,
            openDrawer = {},
            navController = rememberNavController(),
            onSelectRoom = {}
        )
    }
}