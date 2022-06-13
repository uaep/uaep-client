package com.example.uaep.ui.signup

import android.content.Context
import android.content.res.Configuration
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PinDrop
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.uaep.R
import com.example.uaep.dto.SignUpRequestDto
import com.example.uaep.dto.UrlResponseDto
import com.example.uaep.enums.Location
import com.example.uaep.enums.Position
import com.example.uaep.network.UserApiService
import com.example.uaep.ui.components.CustomOutlinedTextField
import com.example.uaep.ui.components.GenderExposedDropDownMenu
import com.example.uaep.ui.components.PasswordOutlinedTextField
import com.example.uaep.ui.navigate.Screen
import com.example.uaep.ui.theme.UaepTheme
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

    val locationList = listOf(
        Location.SEOUL,
        Location.GG,
        Location.INCHEON,
        Location.DSC,
        Location.DG,
        Location.BUG,
        Location.GJ,
        Location.JEJU
    )

    Column(Modifier.verticalScroll(rememberScrollState())) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .height(800.dp)
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.onBackground)
        ) {
            Text(
                text = stringResource(id = R.string.sign_up),
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
                CustomOutlinedTextField(
                    value = vm.name.value,
                    onValueChange = { vm.updateName(it) },
                    labelText = stringResource(id = R.string.name),
                    placeholderText = stringResource(id = R.string.name)
                )
                OutlinedTextField(
                    value = email,
                    onValueChange = { vm.updateEmail(it) },
                    enabled = false,
                    readOnly = true,
                    placeholder = { Text(stringResource(R.string.email)) },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(0.8f),
                    textStyle = MaterialTheme.typography.bodyLarge,
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        textColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedLabelColor = MaterialTheme.colorScheme.primary,
                    ),
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
                            text = stringResource(id = R.string.password),
                            color = MaterialTheme.colorScheme.primary,
                            fontSize = MaterialTheme.typography.labelLarge.fontSize,
                            fontWeight = FontWeight.Bold
                        )
                    },
                    color = MaterialTheme.colorScheme.primary
                )
                PasswordOutlinedTextField(
                    password = vm.matchingPassword.value,
                    onValueChange = { vm.updateMatchingPassword(it) },
                    label = {
                        Text(
                            text = stringResource(id = R.string.matching_password),
                            color = MaterialTheme.colorScheme.primary,
                            fontSize = MaterialTheme.typography.labelLarge.fontSize,
                            fontWeight = FontWeight.Bold
                        )
                    },
                    placeholder = {
                        Text(
                            text = stringResource(id = R.string.matching_password),
                            color = MaterialTheme.colorScheme.primary,
                            fontSize = MaterialTheme.typography.labelLarge.fontSize,
                            fontWeight = FontWeight.Bold
                        )
                    },
                    color = MaterialTheme.colorScheme.primary
                )
//                CustomOutlinedTextField(
//                    value = vm.address.value,
//                    onValueChange = { vm.updateAddress(it) },
//                    labelText = stringResource(id = R.string.address),
//                    placeholderText = stringResource(id = R.string.address)
//                )
                Column {
                    androidx.compose.material3.OutlinedTextField(
                        value = vm.address.value,
                        onValueChange = { vm.updateAddress(it) },
                        modifier = Modifier.fillMaxWidth(0.8f),
                        readOnly = true,
                        label = {
                            Text(
                                text = stringResource(id = R.string.address),
                                color = MaterialTheme.colorScheme.primary,
                                fontSize = MaterialTheme.typography.labelLarge.fontSize,
                                fontWeight = FontWeight.Bold
                            )
                        },
                        placeholder = {
                            Text(
                                text = stringResource(id = R.string.address),
                                color = MaterialTheme.colorScheme.primary,
                                fontSize = MaterialTheme.typography.labelLarge.fontSize,
                                fontWeight = FontWeight.Bold
                            )
                        },
                        colors = androidx.compose.material3.TextFieldDefaults.outlinedTextFieldColors(
                            textColor = MaterialTheme.colorScheme.primary,
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            unfocusedBorderColor = MaterialTheme.colorScheme.primary,
                        ),
                        trailingIcon = {
                            Icon(
                                imageVector = vm.icon3,
                                contentDescription = null,
                                Modifier.clickable {
                                    vm.onAddressEnabled(!vm.addressEnabled.value)
                                },
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    )
                    androidx.compose.material3.DropdownMenu(
                        expanded = vm.addressEnabled.value,
                        modifier = Modifier
                            .fillMaxWidth(0.8f)
                            .background(MaterialTheme.colorScheme.onBackground),
                        onDismissRequest = { vm.onAddressEnabled(false) }
                    ) {
                        locationList.forEach {
                            androidx.compose.material3.DropdownMenuItem(
                                text = {
                                    Text(
                                        text = it.value,
                                        fontWeight = FontWeight.ExtraBold
                                    )
                                },
                                onClick = {
                                    vm.updateAddress(it.value)
                                    vm.onAddressEnabled(false)
                                },
                                trailingIcon = {
                                    Icon(
                                        Icons.Filled.PinDrop,
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.primary
                                    )
                                },
                                colors = MenuDefaults.itemColors(textColor = MaterialTheme.colorScheme.primary)
                            )
                        }
                    }
                }
                GenderExposedDropDownMenu(
                    gender = vm.gender.value,
                    label = {
                        Text(
                            text = stringResource(id = R.string.gender),
                            color = MaterialTheme.colorScheme.primary,
                            fontSize = MaterialTheme.typography.labelLarge.fontSize,
                            fontWeight = FontWeight.Bold
                        )
                    },
                    placeholder = {
                        Text(
                            text = stringResource(id = R.string.gender),
                            color = MaterialTheme.colorScheme.primary,
                            fontSize = MaterialTheme.typography.labelLarge.fontSize,
                            fontWeight = FontWeight.Bold
                        )
                    },
                    color = MaterialTheme.colorScheme.primary,
                    vm = vm
                )
                Column {
                    Box {
                        OutlinedTextField(
                            value = vm.position.value,
                            readOnly = true,
                            onValueChange = {},
                            label = {
                                Text(
                                    text = stringResource(id = R.string.position),
                                    color = MaterialTheme.colorScheme.primary,
                                    fontSize = MaterialTheme.typography.labelLarge.fontSize,
                                    fontWeight = FontWeight.Bold
                                )
                            },
                            placeholder = {
                                Text(
                                    text = stringResource(id = R.string.position),
                                    color = MaterialTheme.colorScheme.primary,
                                    fontSize = MaterialTheme.typography.labelLarge.fontSize,
                                    fontWeight = FontWeight.Bold
                                )
                            },
                            modifier = Modifier
                                .fillMaxWidth(0.8f)
                                .clickable {
                                    vm.onPosEnabled(!vm.posEnabled.value)
                                },
                            textStyle = MaterialTheme.typography.bodyLarge,
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                textColor = MaterialTheme.colorScheme.primary,
                                unfocusedBorderColor = MaterialTheme.colorScheme.primary,
                                unfocusedLabelColor = MaterialTheme.colorScheme.primary,
                                focusedLabelColor = MaterialTheme.colorScheme.primary,
                                focusedBorderColor = MaterialTheme.colorScheme.primary
                            ),
                            trailingIcon = {
                                Icon(
                                    imageVector = vm.icon2,
                                    contentDescription = null,
                                    Modifier.clickable {
                                        vm.onPosEnabled(!vm.posEnabled.value)
                                    },
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            }
                        )
                    }
                    DropdownMenu(
                        expanded = vm.posEnabled.value,
                        onDismissRequest = {
                            vm.onPosEnabled(false)
                        },
                        modifier = Modifier
                            .width(300.dp)
                            .background(MaterialTheme.colorScheme.onBackground),
                    ) {
                        DropdownMenuItem(
                            onClick = {
                                vm.updatePosition(Position.GK)
                                vm.onPosEnabled(false)
                            },
                            modifier = Modifier.background(MaterialTheme.colorScheme.onBackground)
                        ) {
                            Text(
                                text = Position.GK.value,
                                color = MaterialTheme.colorScheme.primary,
                                fontWeight = FontWeight(1000)
                            )
                        }
                        DropdownMenuItem(
                            onClick = {
                                vm.updatePosition(Position.DF)
                                vm.onPosEnabled(false)
                            },
                            modifier = Modifier.background(MaterialTheme.colorScheme.onBackground)
                        ) {
                            Text(
                                text = Position.DF.value,
                                color = MaterialTheme.colorScheme.primary,
                                fontWeight = FontWeight(1000)
                            )
                        }
                        DropdownMenuItem(
                            onClick = {
                                vm.updatePosition(Position.MF)
                                vm.onPosEnabled(false)
                            },
                            modifier = Modifier.background(MaterialTheme.colorScheme.onBackground)
                        ) {
                            Text(
                                text = Position.MF.value,
                                color = MaterialTheme.colorScheme.primary,
                                fontWeight = FontWeight(1000)
                            )
                        }
                        DropdownMenuItem(
                            onClick = {
                                vm.updatePosition(Position.FW)
                                vm.onPosEnabled(false)
                            },
                            modifier = Modifier.background(MaterialTheme.colorScheme.onBackground)
                        ) {
                            Text(
                                text = Position.FW.value,
                                color = MaterialTheme.colorScheme.primary,
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
                        backgroundColor = MaterialTheme.colorScheme.primary,
                    )
                ) {
                    Text(
                        text = stringResource(id = R.string.sign_up),
                        color = MaterialTheme.colorScheme.onBackground
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
    name = "Light Mode",
    showBackground = true
)
@Preview(
    name = "Dark Mode",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun SignUpScreenPreview() {
    UaepTheme {
        SignUpScreen(
            vm = SignUpViewModel(),
            navController = rememberNavController(),
            email = "test@gmail.com",
            token = "32904823904"
        )
    }
}