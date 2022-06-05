package com.example.uaep.ui.login

import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.uaep.R
import com.example.uaep.dto.ErrorResponse
import com.example.uaep.dto.LoginRequestDto
import com.example.uaep.dto.LoginResponseDto
import com.example.uaep.network.AuthService
import com.example.uaep.network.CookieChanger
import com.example.uaep.ui.components.PasswordOutlinedTextField
import com.example.uaep.ui.navigate.Screen
import com.example.uaep.ui.theme.UaepTheme
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun LoginScreen(
    vm: LoginViewModel = viewModel(),
    navController: NavController,
) {
    Box {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.onBackground)
        ) {
            Text(
                text = stringResource(id = R.string.login),
                color = MaterialTheme.colorScheme.primary,
                fontSize = MaterialTheme.typography.headlineLarge.fontSize,
                fontWeight = FontWeight.ExtraBold
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
                    label = {
                        Text(
                            text = stringResource(id = R.string.email),
                            color = MaterialTheme.colorScheme.primary,
                            fontSize = MaterialTheme.typography.labelLarge.fontSize,
                            fontWeight = FontWeight.Bold
                        )
                    },
                    placeholder = {
                        Text(
                            text = stringResource(id = R.string.enter_login_email),
                            color = MaterialTheme.colorScheme.primary,
                            fontSize = MaterialTheme.typography.labelLarge.fontSize,
                            fontWeight = FontWeight.Bold
                        )
                    },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(0.8f),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        textColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedLabelColor = MaterialTheme.colorScheme.primary,
                        focusedLabelColor = MaterialTheme.colorScheme.primary,
                        focusedBorderColor = MaterialTheme.colorScheme.primary
                    ),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
                )
                PasswordOutlinedTextField(
                    password = vm.password.value,
                    onValueChange = { vm.updatePassword(it) },
                    label = {
                        Text(
                            text = stringResource(id = R.string.password),
                            color = MaterialTheme.colorScheme.primary,
                            fontSize = MaterialTheme.typography.labelLarge.fontSize,
                            fontWeight = FontWeight.Bold
                        )
                    },
                    placeholder = {
                        Text(
                            text = stringResource(id = R.string.enter_login_password),
                            color = MaterialTheme.colorScheme.primary,
                            fontSize = MaterialTheme.typography.labelLarge.fontSize,
                            fontWeight = FontWeight.Bold
                        )
                    },
                    color = MaterialTheme.colorScheme.primary
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
                                } else {
                                    val errorResponse: ErrorResponse? =
                                        Gson().fromJson(
                                            response.errorBody()!!.charStream(),
                                            object : TypeToken<ErrorResponse>() {}.type
                                        )
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
                        backgroundColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    )
                ) {
                    Text(
                        text = stringResource(id = R.string.login),
                        color = MaterialTheme.colorScheme.onBackground,
                        fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                        fontWeight = FontWeight.ExtraBold
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
                        backgroundColor = MaterialTheme.colorScheme.secondary,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    ),
                ) {
                    Text(
                        text = stringResource(id = R.string.sign_up),
                        color = MaterialTheme.colorScheme.onBackground,
                        fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                        fontWeight = FontWeight.ExtraBold
                    )
                }
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
fun PreviewLoginScreen() {
    UaepTheme {
        LoginScreen(navController = rememberNavController())
    }
}