package com.example.uaep.ui.login

import android.content.Context
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.uaep.R
import com.example.uaep.dto.ErrorResponse
import com.example.uaep.dto.LoginRequestDto
import com.example.uaep.dto.LoginResponseDto
import com.example.uaep.network.AuthService
import com.example.uaep.network.CookieChanger
import com.example.uaep.ui.components.PasswordOutlinedTextField
import com.example.uaep.ui.navigate.Screen
import com.example.uaep.uitmp.md_theme_light_onPrimary
import com.example.uaep.uitmp.md_theme_light_primary
import com.example.uaep.uitmp.md_theme_light_secondary
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.Cookie
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun LoginScreen(
    vm: LoginViewModel,
    navController: NavController,
    context: Context = LocalContext.current
) {
    Box {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(2f)
                .background(md_theme_light_onPrimary)
                .padding(10.dp)
        ) {
            Text(
                text = stringResource(id = R.string.login),
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 2.sp
                ),
                fontSize = MaterialTheme.typography.h4.fontSize,
                color = md_theme_light_primary
            )
            Spacer(
                modifier = Modifier.padding(20.dp)
            )
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                OutlinedTextField(
                    value = vm.email.value,
                    onValueChange = { vm.updateEmail(it) },
                    label = { Text(stringResource(R.string.email)) },
                    placeholder = { Text(stringResource(R.string.email))},
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(0.8f),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        unfocusedBorderColor = md_theme_light_primary,
                        unfocusedLabelColor = md_theme_light_primary,
                        focusedLabelColor = md_theme_light_primary,
                        focusedBorderColor = md_theme_light_primary
                    ),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
                )
                PasswordOutlinedTextField(
                    password = vm.password.value,
                    onValueChange = { vm.updatePassword(it) },
                    label = { Text(text = stringResource(R.string.password)) },
                    placeholder = { Text(text = stringResource(R.string.password)) },
                    color = md_theme_light_primary
                )
                Spacer(
                    modifier = Modifier.padding(7.dp)
                )
                Button(
                    onClick = {
                        Log.i("Email", vm.email.value)
                        Log.i("Password", vm.password.value)
                        // HTTP Request to Server
                        val loginRequestDto = LoginRequestDto(
                            email=vm.email.value,
                            password = vm.password.value
                        )
                        //vm.loginCallback(UserApiService.getInstance(), loginRequestDto, navController, context)
                        AuthService.getInstance().login(loginRequestDto).enqueue(object :
                            Callback<LoginResponseDto> {
                            override fun onResponse(
                                call: Call<LoginResponseDto>,
                                response: Response<LoginResponseDto>
                            ) {
                                if (response.isSuccessful) {
                                    val tokens = CookieChanger<LoginResponseDto>().change(response)
                                    AuthService.getCookieJar().saveToken(tokens)
                                    navController.navigate(Screen.Home.route)

                                    //navController.navigate(Screen.SignUp.passEmailAndToken(email, token))
                                } else {
                                    val errorResponse: ErrorResponse? =
                                        Gson().fromJson(
                                            response.errorBody()!!.charStream(),
                                            object : TypeToken<ErrorResponse>() {}.type
                                        )
                                    //mToast(context, errorResponse!!.message)
                                }
                            }
                            override fun onFailure(call: Call<LoginResponseDto>, t: Throwable) {
                                Log.i("test", "실패$t")
                            }
                        })
                        },
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = md_theme_light_primary,
                        contentColor = md_theme_light_onPrimary
                    )
                ) {
                    Text(
                        text = stringResource(id = R.string.login),
                        fontSize = MaterialTheme.typography.button.fontSize
                    )
                }
                Spacer(
                    modifier = Modifier.padding(5.dp)
                )
                Button(
                    onClick = {
                        navController.navigate(route = Screen.EmailAuth.route)
                    },
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = md_theme_light_secondary,
                        contentColor = md_theme_light_onPrimary
                    ),
                ) {
                    Text(
                        text = stringResource(id = R.string.sign_up),
                        fontSize = MaterialTheme.typography.button.fontSize
                    )
                }
            }
        }
    }
}