package com.example.uaep.ui.signup

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.uaep.R
import com.example.uaep.dto.AuthCodeRequestDto
import com.example.uaep.dto.ErrorResponse
import com.example.uaep.dto.UrlResponseDto
import com.example.uaep.network.UserApiService
import com.example.uaep.ui.navigate.Screen
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


@Composable
fun AuthCodeScreen (
    vm: AuthCodeViewModel,
    navController: NavController,
    email: String,
    token: String
) {
    val context = LocalContext.current

    Box {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(2f)
                .background(MaterialTheme.colorScheme.onBackground)
                .padding(10.dp)
        ) {
            Text(
                text = stringResource(id = R.string.auth_code),
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 2.sp
                ),
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.padding(20.dp))
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                OutlinedTextField(
                    value = vm.authCode.value,
                    onValueChange = { vm.updateAuthCode(it) },
                    label = { Text(stringResource(R.string.auth_code)) },
                    placeholder = { Text(stringResource((R.string.auth_code))) },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(0.8f),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        unfocusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedLabelColor = MaterialTheme.colorScheme.primary,
                        focusedLabelColor = MaterialTheme.colorScheme.primary,
                        focusedBorderColor = MaterialTheme.colorScheme.primary
                    ),
                )
                Spacer(modifier = Modifier.padding(10.dp))
                Button(
                    onClick = {
                        vm.updateAuthCode(vm.authCode.value)

                        Log.i("POST to the server", vm.authCode.value)
                        val authCodeRequestDto = AuthCodeRequestDto(code = vm.authCode.value)
                        val userApiService = UserApiService.getInstance()
                        userApiService.verifyEmail(
                            authCodeRequestDto = authCodeRequestDto,
                            token = token
                        ).enqueue(object :
                            Callback<UrlResponseDto> {
                            override fun onResponse(
                                call: Call<UrlResponseDto>,
                                response: Response<UrlResponseDto>
                            ) {
                                if (response.body() != null && response.isSuccessful) {
                                    navController.navigate(Screen.SignUp.passEmailAndToken(email, token))
                                } else {
                                    val errorResponse: ErrorResponse? =
                                        Gson().fromJson(
                                            response.errorBody()!!.charStream(),
                                            object : TypeToken<ErrorResponse>() {}.type
                                        )

                                    mToast(context, errorResponse!!.message)
                                }
                            }

                            override fun onFailure(call: Call<UrlResponseDto>, t: Throwable) {
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
                        text = stringResource(R.string.verify),
                    )
                }
            }
        }
    }
}

private fun mToast(context: Context, msg: String){
    Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
}