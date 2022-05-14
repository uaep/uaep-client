package com.example.uaep.ui.signup

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
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.uaep.R
import com.example.uaep.dto.SignUpRequestDto
import com.example.uaep.dto.UrlResponseDto
import com.example.uaep.enums.Position
import com.example.uaep.network.UserApiService
import com.example.uaep.ui.Navi.Screen
import com.example.uaep.ui.components.GenderExposedDropDownMenu
import com.example.uaep.ui.components.PasswordOutlinedTextField
import com.example.uaep.uitmp.md_theme_light_onPrimary
import com.example.uaep.uitmp.md_theme_light_primary
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


@Composable
fun SignUpScreen (
    vm: SignUpViewModel,
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
                OutlinedTextField(
                    value = vm.address.value,
                    onValueChange = { vm.updateAddress(it) },
                    label = { Text(stringResource(R.string.address)) },
                    placeholder = { Text(stringResource(R.string.address))},
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(0.8f),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        unfocusedBorderColor = md_theme_light_primary,
                        unfocusedLabelColor = md_theme_light_primary,
                        focusedLabelColor = md_theme_light_primary,
                        focusedBorderColor = md_theme_light_primary
                    ),
                )
                GenderExposedDropDownMenu(
                    gender = vm.gender.value,
                    label = { Text(stringResource(R.string.gender)) },
                    placeholder = { Text(stringResource(R.string.gender)) },
                    color = md_theme_light_primary,
                    vm = vm
                )
                Column {
                    Box {
                        OutlinedTextField(
                            value = vm.position.value,
                            readOnly = true,
                            onValueChange = {},
                            label = { Text(stringResource(R.string.position))},
                            placeholder = { Text(stringResource(R.string.position))},
                            modifier = Modifier
                                .fillMaxWidth(0.8f)
                                .clickable {
                                    vm.onPosEnabled(!vm.posEnabled.value)
                                },
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                unfocusedBorderColor = md_theme_light_primary,
                                unfocusedLabelColor = md_theme_light_primary,
                                focusedLabelColor = md_theme_light_primary,
                                focusedBorderColor = md_theme_light_primary
                            ),
                            trailingIcon = {
                                Icon(
                                    imageVector = vm.icon2,
                                    contentDescription = null,
                                    Modifier.clickable {
                                        vm.onPosEnabled(!vm.posEnabled.value)
                                    }
                                )
                            }
                        )
                    }
                    DropdownMenu(
                        expanded = vm.posEnabled.value,
                        onDismissRequest = {
                            vm.onPosEnabled(false)
                        },
                        modifier = Modifier.width(300.dp),
                    ) {
                        DropdownMenuItem(
                            onClick = {
                                vm.updatePosition(Position.GK)
                                vm.onPosEnabled(false)
                            }
                        ) {
                            Text(
                                text = Position.GK.value,
                                color = md_theme_light_primary,
                                fontWeight = FontWeight(1000)
                            )
                        }
                        DropdownMenuItem(
                            onClick = {
                                vm.updatePosition(Position.DF)
                                vm.onPosEnabled(false)
                            }
                        ) {
                            Text(
                                text = Position.DF.value,
                                color = md_theme_light_primary,
                                fontWeight = FontWeight(1000)
                            )
                        }
                        DropdownMenuItem(
                            onClick = {
                                vm.updatePosition(Position.MF)
                                vm.onPosEnabled(false)
                            }
                        ) {
                            Text(
                                text = Position.MF.value,
                                color = md_theme_light_primary,
                                fontWeight = FontWeight(1000)
                            )
                        }
                        DropdownMenuItem(
                            onClick = {
                                vm.updatePosition(Position.FW)
                                vm.onPosEnabled(false)
                            }
                        ) {
                            Text(
                                text = Position.FW.value,
                                color = md_theme_light_primary,
                                fontWeight = FontWeight(1000)
                            )
                        }

                    }
                }
                Spacer(modifier = Modifier.padding(10.dp))
                Button(
                    onClick = {
                        if (!vm.isSamePassword(vm.password.value, vm.matchingPassword.value)) {
                            mToast(context, "비밀번호가 일치하지 않습니다.")
                        } else {
                            mToast(context, "회원가입을 진행 중입니다.")

                            Log.i("Name", vm.name.value)
                            Log.i("Password", vm.password.value)
                            Log.i("Matching password", vm.matchingPassword.value)
                            Log.i("Gender", vm.gender.value)
                            Log.i("Address", vm.address.value)
                            Log.i("Position", vm.position.value)
                            val signUpRequestDto = SignUpRequestDto(
                                name = vm.name.value,
                                password = vm.password.value,
                                matchingPassword = vm.matchingPassword.value,
                                gender = vm.gender.value,
                                address =  vm.address.value,
                                position = vm.position.value
                            )
                            val userApiService = UserApiService.getInstance()
                            userApiService.signup(
                                signUpRequestDto = signUpRequestDto,
                                token = token
                            ).enqueue(object :
                                Callback<UrlResponseDto> {
                                override fun onResponse(
                                    call: Call<UrlResponseDto>,
                                    response: Response<UrlResponseDto>
                                ) {
                                    if(response.isSuccessful) {
                                        navController.navigate(Screen.Login.route)
                                    } else {
                                        // TODO: 배열로 넘오는 json 에러 값 처리하기
                                        val jObjError = JSONObject(
                                            response.errorBody()!!.string()
                                        )
                                        Log.d("Error Test", jObjError.toString())

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

private fun mToast(context: Context, msg: String){
    Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
}

//@Preview(
//    showBackground = true,
//    widthDp = 320
//)
//@Composable
//fun SignUpScreenPreview() {
//    UaepTheme {
//        SignUpScreen(
//            vm = SignUpViewModel(),
//            navController = rememberNavController(),
//            email = "test@gmail.com",
//            token = "32904823904"
//        )
//    }
//}