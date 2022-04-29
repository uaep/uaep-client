package com.example.uaep.view

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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.uaep.ui.theme.UaepTheme
import com.example.uaep.ui.theme.md_theme_light_onPrimary
import com.example.uaep.ui.theme.md_theme_light_primary
import com.example.uaep.ui.theme.md_theme_light_secondary
import com.example.uaep.viewmodel.LoginViewModel

@Composable
fun LoginView(
    vm: LoginViewModel
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
                text = "로그인",
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 2.sp
                ),
                fontSize = 30.sp,
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
                    label = { Text("이메일을 입력하세요.") },
                    placeholder = { Text("이메일을 입력하세요.")},
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
                    label = { Text(text = "비밀번호를 입력하세요.") },
                    placeholder = { Text(text = "비밀번호를 입력하세요.") },
                    color = md_theme_light_primary
                )
                Spacer(modifier = Modifier.padding(7.dp))
                Button(
                    onClick = {
                        Log.i("Email", vm.email.value)
                        Log.i("Password", vm.password.value)
                        // TODO: HTTP Request to Server
                    },
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = md_theme_light_primary,
                        contentColor = md_theme_light_onPrimary
                    )
                ) {
                    Text(text = "로그인", fontSize = 20.sp)
                }
                Spacer(modifier = Modifier.padding(5.dp))
                Button(
                    onClick = {
                        // TODO: 이메일 인증 화면으로 전환하기
                    },
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = md_theme_light_secondary,
                        contentColor = md_theme_light_onPrimary
                    )
                ) {
                    Text(text = "회원가입", fontSize = 20.sp)
                }
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 320)
@Composable
fun LoginScreenPreview() {
    UaepTheme {
        LoginView(LoginViewModel())
    }
}