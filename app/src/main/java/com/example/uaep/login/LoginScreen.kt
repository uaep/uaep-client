package com.example.uaep.login

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.uaep.ui.theme.Orange100
import com.example.uaep.ui.theme.UaepTheme

@Composable
fun LoginScreen() {

    var emailValue by remember { mutableStateOf("") }
    var passwordValue by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(2f)
                .background(Color.White)
                .padding(10.dp)
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "로그인",
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 2.sp
                    ),
                    fontSize = 30.sp,
                    color = Orange100
                )
                Spacer(modifier = Modifier.padding(20.dp))
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    EmailOutlinedTextField(
                        text = emailValue,
                        onValueChange = { emailValue = it },
                        label = { Text("이메일을 입력하세요.")},
                        placeholder = { Text("이메일을 입력하세요.")},
                        singleLine = true,
                        color = Orange100
                    )
                    PasswordOutlinedTextField(
                        text = passwordValue,
                        onValueChange = { passwordValue = it },
                        label = { Text(text = "비밀번호를 입력하세요.") },
                        placeholder = { Text(text = "비밀번호를 입력하세요.") },
                        color = Orange100
                    )
                    Spacer(modifier = Modifier.padding(10.dp))
                    // 로그인 버튼
                    Button(
                        onClick = {
                            Log.i("Email value : ", emailValue)
                            Log.i("Password value : ", passwordValue)
                            // TODO: HTTP Request to Server
                        },
                        modifier = Modifier
                            .fillMaxWidth(0.8f)
                            .height(50.dp)
                        ,
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Orange100,
                            contentColor = Color.White
                        )
                    ) {
                        Text(text = "로그인", fontSize = 20.sp)
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun Login() {
    UaepTheme {
        LoginScreen()
    }
}