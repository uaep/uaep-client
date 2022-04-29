package com.example.uaep.ui.login

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.uaep.uitmp.md_theme_light_onPrimary
import com.example.uaep.uitmp.md_theme_light_primary
import com.example.uaep.viewmodel.LoginViewModel

@Composable
fun LoginView() {

    val vm = viewModel<LoginViewModel>()
    val navController = rememberNavController()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(md_theme_light_onPrimary),
    ) {
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
            Spacer(modifier = Modifier.padding(20.dp))
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                EmailOutlinedTextField(
                    text = vm.email.value,
                    onValueChange = { vm.updateEmail(it) },
                    label = { Text("이메일을 입력하세요.")},
                    placeholder = { Text("이메일을 입력하세요.")},
                    singleLine = true,
                    color = md_theme_light_primary
                )
                PasswordOutlinedTextField(
                    text = vm.password.value,
                    onValueChange = { vm.updatePassword(it) },
                    label = { Text(text = "비밀번호를 입력하세요.") },
                    placeholder = { Text(text = "비밀번호를 입력하세요.") },
                    color = md_theme_light_primary
                )
                Spacer(modifier = Modifier.padding(10.dp))
                // 로그인 버튼
                Button(
                    onClick = {
                        Log.i("Email value : ", vm.email.value)
                        Log.i("Password value : ", vm.password.value)
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
                Spacer(modifier = Modifier.padding(10.dp))
                Text(
                    text = "회원가입",
                    modifier = Modifier.clickable {
                        Log.d("회원가입", "CLICK")
                    },
                    fontSize = 20.sp
                )
            }
        }
    }
}