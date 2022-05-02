package com.example.uaep.presentation.signup

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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.uaep.R
import com.example.uaep.navigation.Screen
import com.example.uaep.dto.SignUpRequestDto
import com.example.uaep.dto.UrlResponseDto
import com.example.uaep.network.UserApiService
import com.example.uaep.ui.theme.UaepTheme
import com.example.uaep.ui.theme.md_theme_light_onPrimary
import com.example.uaep.ui.theme.md_theme_light_primary
import com.example.uaep.presentation.components.GenderExposedDropDownMenu
import com.example.uaep.presentation.components.PasswordOutlinedTextField
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun SignUpScreen (
    vm: SignUpViewModel,
    navController: NavController,
    email: String
) {
    val context = LocalContext.current

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
                text = stringResource(id = R.string.sign_up),
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
                    value = vm.name.value,
                    onValueChange = { vm.updateName(it) },
                    label = { Text(stringResource(R.string.name)) },
                    placeholder = { Text(stringResource(R.string.name))},
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(0.8f),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        unfocusedBorderColor = md_theme_light_primary,
                        unfocusedLabelColor = md_theme_light_primary,
                        focusedLabelColor = md_theme_light_primary,
                        focusedBorderColor = md_theme_light_primary
                    ),
                )
                OutlinedTextField(
                    value = email,
                    onValueChange = { vm.updateEmail(it) },
                    enabled = false,
                    readOnly = true,
                    label = { Text(stringResource(R.string.email)) },
                    placeholder = { Text(stringResource(R.string.email)) },
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
                    label = { Text(stringResource(R.string.password)) },
                    placeholder = { Text(stringResource(R.string.password)) },
                    color = md_theme_light_primary
                )
                PasswordOutlinedTextField(
                    password = vm.matchingPassword.value,
                    onValueChange = { vm.updateMatchingPassword(it) },
                    label = { Text(stringResource(R.string.matching_password)) },
                    placeholder = { Text(stringResource(R.string.matching_password)) },
                    color = md_theme_light_primary
                )
                GenderExposedDropDownMenu(
                    gender = vm.gender.value,
                    label = { Text(stringResource(R.string.gender)) },
                    placeholder = { Text(stringResource(R.string.gender)) },
                    color = md_theme_light_primary,
                    vm = vm
                )
                Spacer(modifier = Modifier.padding(10.dp))
                Button(
                    onClick = {
                        if (!vm.isSamePassword(vm.password.value, vm.matchingPassword.value)) {
                            Toast.makeText(context, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_LONG).show()
                        } else {
                            // TODO: HTTP Request to Server
                            Toast.makeText(context, "회원가입을 진행 중입니다.", Toast.LENGTH_LONG).show()

                            Log.i("Name", vm.name.value)
                            Log.i("Password", vm.password.value)
                            Log.i("Matching password", vm.matchingPassword.value)
                            Log.i("Gender", vm.gender.value)
                            val signUpRequestDto = SignUpRequestDto(
                                name = vm.name.value,
                                password = vm.password.value,
                                matchingPassword = vm.matchingPassword.value,
                                gender = vm.gender.value
                            )
                            val userApiService = UserApiService.getInstance()
                            userApiService.signup(signUpRequestDto = signUpRequestDto).enqueue(object :
                                Callback<UrlResponseDto> {
                                override fun onResponse(
                                    call: Call<UrlResponseDto>,
                                    response: Response<UrlResponseDto>
                                ) {
                                    if(response.isSuccessful) {
                                        Log.i("test", response.body().toString())
                                        // TODO: Status code에 따라 다르게 구현
                                        var url = response.body() // GsonConverter를 사용해 데이터매핑

                                        navController.navigate(Screen.Login.route)
                                    }
                                }

                                override fun onFailure(call: Call<UrlResponseDto>, t: Throwable) {
                                    Log.e("test", "실패$t")
                                }
                            })
                        }
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
                        text = stringResource(id = R.string.sign_up),
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
fun SignUpScreenPreview() {
    UaepTheme {
        SignUpScreen(
            vm = SignUpViewModel(),
            navController = rememberNavController(),
            email = "test@gmail.com"
        )
    }
}