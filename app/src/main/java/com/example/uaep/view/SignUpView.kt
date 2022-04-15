package com.example.uaep.view

import android.widget.Toast
import androidx.compose.foundation.background
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.uaep.ui.theme.UaepTheme
import com.example.uaep.ui.theme.md_theme_light_primary
import com.example.uaep.view.EmailOutlinedTextField
import com.example.uaep.view.PasswordOutlinedTextField
import com.example.uaep.view.GenderExposedDropDownMenu
import com.example.uaep.view.NameOutlinedTextField
import com.example.uaep.ui.theme.md_theme_light_onPrimary
import com.example.uaep.viewmodel.SignUpViewModel

@Preview
@Composable
fun SignUpView () {

    val vm = viewModel<SignUpViewModel>()
    val context = LocalContext.current

    UaepTheme() {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(md_theme_light_onPrimary)
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
                    text = "회원가입",
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 2.sp
                    ),
                    fontSize = 30.sp,
                    color = md_theme_light_primary
                )
                Spacer(modifier = Modifier.padding(20.dp))
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    NameOutlinedTextField(
                        text = vm.name.value,
                        onValueChange = { vm.updateName(it) },
                        label = { Text("이름") },
                        placeholder = { Text("이름을 입력하세요.") },
                        color = md_theme_light_primary
                    )
                    EmailOutlinedTextField(
                        text = vm.email.value,
                        onValueChange = { vm.updateEmail(it) },
                        label = { Text("이메일") },
                        placeholder = { Text("이메일을 입력하세요.") },
                        singleLine = true,
                        color = md_theme_light_primary
                    )
                    PasswordOutlinedTextField(
                        text = vm.password.value,
                        onValueChange = { vm.updatePassword(it) },
                        label = { Text(text = "비밀번호") },
                        placeholder = { Text(text = "비밀번호를 입력하세요.") },
                        color = md_theme_light_primary
                    )
                    PasswordOutlinedTextField(
                        text = vm.confirmedPassword.value,
                        onValueChange = { vm.updateConfirmedPassword(it) },
                        label = { Text(text = "비밀번호 확인") },
                        placeholder = { Text(text = "비밀번호를 확인하세요.") },
                        color = md_theme_light_primary
                    )
                    GenderExposedDropDownMenu(
                        gender = vm.gender.value,
                        label = { Text(text = "성별") },
                        placeholder = { Text(text = "성별") },
                        color = md_theme_light_primary,
                        vm = vm
                    )
                    Spacer(modifier = Modifier.padding(10.dp))
                    Button(
                        onClick = {
                            if (!vm.isValidEmail(vm.email.value)) {
                                Toast.makeText(context, "올바른 이메일 형식을 입력하세요.", Toast.LENGTH_LONG).show()
                            } else if (!vm.isSamePassword(vm.password.value, vm.confirmedPassword.value)) {
                                Toast.makeText(context, "비밀번호가 일치한지 확인하세요.", Toast.LENGTH_LONG).show()
                            } else if (!vm.isMinLength(vm.password.value, vm.confirmedPassword.value)) {
                                Toast.makeText(context, "비밀번호는 최소 8자 이상입니다.", Toast.LENGTH_LONG).show()
                            } else {
                                // TODO: HTTP Request to Server
                                Toast.makeText(context, "서버에 전송합니다.", Toast.LENGTH_LONG).show()
                            }
//                            Log.d("Name : ", vm.name.value)
//                            Log.d("Email : ", vm.email.value)
//                            Log.d("Password : ", vm.password.value)
//                            Log.d("Confirmed password : ", vm.confirmedPassword.value)
//                            Log.d("Gender : ", vm.gender.value)
                        },
                        modifier = Modifier
                            .fillMaxWidth(0.8f)
                            .height(50.dp),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = md_theme_light_primary,
                            contentColor = md_theme_light_onPrimary
                        )
                    ) {
                        Text(text = "회원가입", fontSize = 20.sp)
                    }
                }
            }
        }
    }
}