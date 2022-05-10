package com.example.uaep.presentation.signup

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.uaep.enums.Domain
import com.example.uaep.R
import com.example.uaep.navigation.Screen
import com.example.uaep.dto.EmailRequestDto
import com.example.uaep.dto.ErrorResponse
import com.example.uaep.dto.UrlResponseDto
import com.example.uaep.network.UserApiService
import com.example.uaep.ui.theme.UaepTheme
import com.example.uaep.ui.theme.md_theme_light_onPrimary
import com.example.uaep.ui.theme.md_theme_light_primary
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun EmailAuthScreen (
    vm: EmailAuthViewModel,
    navController: NavController
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
                text = "이메일인증",
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
                    value = vm.id.value,
                    onValueChange = { vm.updateId(it) },
                    label = { Text(stringResource(R.string.id)) },
                    placeholder = { Text(stringResource(R.string.id)) },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(0.8f),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        unfocusedBorderColor = md_theme_light_primary,
                        unfocusedLabelColor = md_theme_light_primary,
                        focusedLabelColor = md_theme_light_primary,
                        focusedBorderColor = md_theme_light_primary
                    ),
                )
                Column {
                    Box {
                        OutlinedTextField(
                            value = vm.domain.value,
                            readOnly = true,
                            onValueChange = {},
                            label = { Text(stringResource(R.string.domain)) } ,
                            placeholder = { Text(stringResource(R.string.domain)) },
                            modifier = Modifier
                                .fillMaxWidth(0.8f)
                                .clickable {
                                    vm.onEnabled(!vm.enabled.value)
                                },
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                unfocusedBorderColor = md_theme_light_primary,
                                unfocusedLabelColor = md_theme_light_primary,
                                focusedLabelColor = md_theme_light_primary,
                                focusedBorderColor = md_theme_light_primary
                            ),
                            trailingIcon = {
                                Icon(
                                    imageVector = vm.icon,
                                    contentDescription = null,
                                    Modifier.clickable {
                                        vm.onEnabled(!vm.enabled.value)
                                    }
                                )
                            }
                        )
                    }
                    DropdownMenu(
                        expanded = vm.enabled.value,
                        onDismissRequest = {
                            vm.onEnabled(false)
                        },
                        modifier = Modifier.width(300.dp),
                    ) {
                        DropdownMenuItem(
                            onClick = {
                                vm.updateDomain(Domain.GMAIL.value)
                                vm.onEnabled(false)
                            }
                        ) {
                            Text(
                                text = Domain.GMAIL.value,
                                color = md_theme_light_primary,
                                fontWeight = FontWeight(1000)
                            )
                        }
                        DropdownMenuItem(
                            onClick = {
                                vm.updateDomain(Domain.NAVER.value)
                                vm.onEnabled(false)
                            }
                        ) {
                            Text(
                                text = Domain.NAVER.value,
                                color = md_theme_light_primary,
                                fontWeight = FontWeight(1000)
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.padding(10.dp))
                Button(
                    onClick = {
                        vm.updateEmail(vm.id.value + "@" + vm.domain.value)

                        Log.i("POST to the server", vm.email.value)
                        val emailRequestDto = EmailRequestDto(email = vm.email.value)
                        val userApiService = UserApiService.getInstance()
                        userApiService.checkEmailValidity(emailRequestDto = emailRequestDto).enqueue(object :
                            Callback<UrlResponseDto> {
                            override fun onResponse(
                                call: Call<UrlResponseDto>,
                                response: Response<UrlResponseDto>
                            ) {
                                if(response.isSuccessful) {
                                    val token = response.body()?.url.toString().split("=")[1];
                                    navController.navigate(route = Screen.AuthCode.passEmailAndToken(vm.email.value, token))
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
                                Log.e("test", "실패$t")
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
                        text = stringResource(R.string.send_auth_code),
                        fontSize = MaterialTheme.typography.button.fontSize
                    )
                }
            }
        }
    }
}

private fun mToast(context: Context, msg: String){
    Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
}

@Preview(
    showBackground = true,
    widthDp = 320
)
@Composable
fun EmailAuthPreview() {
    UaepTheme {
        EmailAuthScreen(
            vm = EmailAuthViewModel(),
            navController = rememberNavController()
        )
    }
}