package com.example.uaep.presentation.signup

import android.util.Log
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
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.uaep.R
import com.example.uaep.navigation.Screen
import com.example.uaep.dto.AuthCodeRequestDto
import com.example.uaep.dto.UrlResponseDto
import com.example.uaep.network.UserApiService
import com.example.uaep.ui.theme.UaepTheme
import com.example.uaep.ui.theme.md_theme_light_onPrimary
import com.example.uaep.ui.theme.md_theme_light_primary
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun AuthCodeScreen (
    vm: AuthCodeViewModel,
    navController: NavController,
    email: String
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
                text = stringResource(id = R.string.auth_code),
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 2.sp
                ),
                fontSize = MaterialTheme.typography.h4.fontSize,
                color = md_theme_light_primary
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
                        unfocusedBorderColor = md_theme_light_primary,
                        unfocusedLabelColor = md_theme_light_primary,
                        focusedLabelColor = md_theme_light_primary,
                        focusedBorderColor = md_theme_light_primary
                    ),
                )
                Spacer(modifier = Modifier.padding(10.dp))
                Button(
                    onClick = {
                        vm.updateAuthCode(vm.authCode.value)

                        Log.i("POST to the server", vm.authCode.value)
                        val authCodeRequestDto = AuthCodeRequestDto(code = vm.authCode.value)
                        val userApiService = UserApiService.getInstance()
                        userApiService.verifyEmail(authCodeRequestDto = authCodeRequestDto).enqueue(object :
                            Callback<UrlResponseDto> {
                            override fun onResponse(
                                call: Call<UrlResponseDto>,
                                response: Response<UrlResponseDto>
                            ) {
                                if(response.isSuccessful) {
                                    Log.i("test", response.body().toString())
                                    // TODO: Status Code에 따라 다르게 구현
                                    var url = response.body() // GsonConverter를 사용해 데이터매핑
                                    navController.navigate(Screen.SignUp.passEmail(email))
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
                        backgroundColor = md_theme_light_primary,
                        contentColor = md_theme_light_onPrimary
                    )
                ) {
                    Text(
                        text = stringResource(R.string.verify),
                        fontSize = MaterialTheme.typography.button.fontSize
                    )
                }
            }
        }
    }
}

@Preview(
    showBackground = true, 
    widthDp = 320
)
@Composable
fun AuthCodePreview() {
    UaepTheme {
        AuthCodeScreen(
            vm = AuthCodeViewModel(),
            navController = rememberNavController(),
            email = "test@gmail.com"
        )
    }
}