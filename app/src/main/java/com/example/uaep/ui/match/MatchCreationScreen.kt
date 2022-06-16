@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.uaep.ui.match

import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.uaep.R
import com.example.uaep.dto.DummyResponse
import com.example.uaep.dto.ErrorResponse
import com.example.uaep.dto.GameCreateDto
import com.example.uaep.dto.UrlResponseDto
import com.example.uaep.enums.Gender
import com.example.uaep.enums.NumPlayers
import com.example.uaep.network.AuthService
import com.example.uaep.network.CookieChanger
import com.example.uaep.network.ReAuthService
import com.example.uaep.ui.components.CommonTopAppBar
import com.example.uaep.ui.components.ErrorDialog
import com.example.uaep.ui.components.SpinnerView
import com.example.uaep.ui.components.SpinnerViewModel
import com.example.uaep.ui.navigate.BottomNavigationBar
import com.example.uaep.ui.navigate.Screen
import com.example.uaep.ui.theme.UaepTheme
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

enum class Limitaion (val value: String) {
    ALL("모든 레벨"),
    BELOW_B3("비기너3 이하"),
    HIGHER_SP1("세미프로1 이상")
}

@Composable
fun MatchCreationScreen (
    vm: MatchCreationScreenViewModel = MatchCreationScreenViewModel(),
    navController: NavController
) {
    val spinnerViewModel = SpinnerViewModel()
    val limitaionList = listOf(
        Limitaion.ALL,
        Limitaion.BELOW_B3,
        Limitaion.HIGHER_SP1
    )

    var openDialog by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    if (openDialog)
        ErrorDialog(
            onError = { openDialog = false },
            errorMessage = errorMessage
        )

    Column(
        Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())) {
        Scaffold(
            Modifier.height(800.dp),
            topBar = {
                CommonTopAppBar(
                    openDrawer = {},
                    navController = navController
                )
            },
            bottomBar = {
                BottomNavigationBar(navController = navController)
            }
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize(),
            ) {
                Spacer(modifier = Modifier.padding(vertical = 20.dp))
                Text(
                    text = stringResource(id = R.string.create_room).uppercase(),
                    modifier = Modifier,
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = MaterialTheme.typography.headlineLarge.fontSize,
                    fontWeight = FontWeight.Bold,
                )
                Spacer(modifier = Modifier.padding(vertical = 20.dp))
                OutlinedTextField(
                    value = vm.place.value,
                    onValueChange = { vm.updatePlace(it) },
                    label = {
                        Text(
                            text = stringResource(R.string.location),
                            color = MaterialTheme.colorScheme.primary,
                            fontSize = MaterialTheme.typography.labelLarge.fontSize,
                            fontWeight = FontWeight.Bold
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth(0.8f),
                    shape = MaterialTheme.shapes.medium,
                    textStyle = MaterialTheme.typography.bodyLarge,
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        textColor = MaterialTheme.colorScheme.primary,
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.primary,
                    )
                )
                Spacer(modifier = Modifier.padding(vertical = 10.dp))
                Column {
                    OutlinedTextField(
                        value = vm.numPlayer.value,
                        readOnly = true,
                        onValueChange = {},
                        label = {
                            Text(
                                text = stringResource(R.string.num_player),
                                color = MaterialTheme.colorScheme.primary,
                                fontSize = MaterialTheme.typography.labelLarge.fontSize,
                                fontWeight = FontWeight.Bold
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth(0.8f)
                            .clickable {
                                vm.onNumPlayerSelected()
                            },
                        shape = MaterialTheme.shapes.medium,
                        textStyle = MaterialTheme.typography.bodyLarge,
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            textColor = MaterialTheme.colorScheme.primary,
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            unfocusedBorderColor = MaterialTheme.colorScheme.primary,
                        ),
                        trailingIcon = {
                            Icon(
                                imageVector = vm.icon1,
                                contentDescription = null,
                                Modifier.clickable {
                                    vm.onNumPlayerSelected()
                                },
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    )
                    DropdownMenu(
                        expanded = vm.numPlayerSelected.value,
                        onDismissRequest = {
                            vm.onNumPlayerSelected()
                        },
                        modifier = Modifier
                            .fillMaxWidth(0.8f)
                            .background(color = MaterialTheme.colorScheme.onBackground)
                    ) {
                        DropdownMenuItem(
                            onClick = {
                                vm.updateNumPlayer(NumPlayers.FIVE.value)
                                vm.onNumPlayerSelected()
                            }
                        ) {
                            Text(
                                text = NumPlayers.FIVE.value,
                                color = MaterialTheme.colorScheme.primary,
                                fontSize = MaterialTheme.typography.labelLarge.fontSize,
                                fontWeight = FontWeight.ExtraBold
                            )
                        }
                        DropdownMenuItem(
                            onClick = {
                                vm.updateNumPlayer(NumPlayers.SIX.value)
                                vm.onNumPlayerSelected()
                            }
                        ) {
                            Text(
                                text = NumPlayers.SIX.value,
                                color = MaterialTheme.colorScheme.primary,
                                fontSize = MaterialTheme.typography.labelLarge.fontSize,
                                fontWeight = FontWeight.ExtraBold
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.padding(vertical = 10.dp))
                Column {
                    OutlinedTextField(
                        value = vm.gender.value,
                        readOnly = true,
                        onValueChange = {},
                        label = {
                            Text(
                                text = stringResource(R.string.gender),
                                color = MaterialTheme.colorScheme.primary,
                                fontSize = MaterialTheme.typography.labelLarge.fontSize,
                                fontWeight = FontWeight.Bold
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth(0.8f)
                            .clickable {
                                vm.onGenderSelected()
                            },
                        shape = MaterialTheme.shapes.medium,
                        textStyle = MaterialTheme.typography.bodyLarge,
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            textColor = MaterialTheme.colorScheme.primary,
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            unfocusedBorderColor = MaterialTheme.colorScheme.primary,
                        ),
                        trailingIcon = {
                            Icon(
                                imageVector = vm.icon2,
                                contentDescription = null,
                                Modifier.clickable {
                                    vm.onGenderSelected()
                                },
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    )
                    DropdownMenu(
                        expanded = vm.genderSelected.value,
                        onDismissRequest = {
                            vm.onGenderSelected()
                        },
                        modifier = Modifier
                            .fillMaxWidth(0.8f)
                            .background(color = MaterialTheme.colorScheme.onBackground)
                    ) {
                        DropdownMenuItem(
                            onClick = {
                                vm.updateGender(Gender.MALE.value)
                                vm.onGenderSelected()
                            }
                        ) {
                            Text(
                                text = Gender.MALE.value,
                                color = MaterialTheme.colorScheme.primary,
                                fontSize = MaterialTheme.typography.labelLarge.fontSize,
                                fontWeight = FontWeight.ExtraBold
                            )
                        }
                        DropdownMenuItem(
                            onClick = {
                                vm.updateGender(Gender.FEMALE.value)
                                vm.onGenderSelected()
                            }
                        ) {
                            Text(
                                text = Gender.FEMALE.value,
                                color = MaterialTheme.colorScheme.primary,
                                fontSize = MaterialTheme.typography.labelLarge.fontSize,
                                fontWeight = FontWeight.ExtraBold
                            )
                        }
                        DropdownMenuItem(
                            onClick = {
                                vm.updateGender(Gender.ANY.value)
                                vm.onGenderSelected()
                            }
                        ) {
                            Text(
                                text = Gender.ANY.value,
                                color = MaterialTheme.colorScheme.primary,
                                fontSize = MaterialTheme.typography.labelLarge.fontSize,
                                fontWeight = FontWeight.ExtraBold
                            )
                        }
                    }
                    Spacer(modifier = Modifier.padding(vertical = 10.dp))
                    Column {
                        OutlinedTextField(
                            value = vm.limitation.value,
                            readOnly = true,
                            onValueChange = {},
                            label = {
                                Text(
                                    text = stringResource(R.string.level_limitation),
                                    color = MaterialTheme.colorScheme.primary,
                                    fontSize = MaterialTheme.typography.labelLarge.fontSize,
                                    fontWeight = FontWeight.Bold
                                )
                            },
                            modifier = Modifier
                                .fillMaxWidth(0.8f)
                                .clickable {
                                    vm.onGenderSelected()
                                },
                            shape = MaterialTheme.shapes.medium,
                            textStyle = MaterialTheme.typography.bodyLarge,
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                textColor = MaterialTheme.colorScheme.primary,
                                focusedBorderColor = MaterialTheme.colorScheme.primary,
                                unfocusedBorderColor = MaterialTheme.colorScheme.primary,
                            ),
                            trailingIcon = {
                                Icon(
                                    imageVector = vm.icon3,
                                    contentDescription = null,
                                    Modifier.clickable {
                                        vm.onLimitationSelected()
                                    },
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            }
                        )
                        DropdownMenu(
                            expanded = vm.limitationSelected.value,
                            onDismissRequest = {
                                vm.onLimitationSelected()
                            },
                            modifier = Modifier
                                .fillMaxWidth(0.8f)
                                .background(color = MaterialTheme.colorScheme.onBackground)
                        ) {
                            limitaionList.forEach {
                                DropdownMenuItem(
                                    onClick = {
                                        vm.updateLimitaion(it.value)
                                        vm.onLimitationSelected()
                                    }
                                ) {
                                    Text(
                                        text = it.value,
                                        color = MaterialTheme.colorScheme.primary,
                                        fontSize = MaterialTheme.typography.labelLarge.fontSize,
                                        fontWeight = FontWeight.ExtraBold
                                    )
                                }
                            }
                        }
                    }
                    Spacer(modifier = Modifier.padding(vertical = 10.dp))
                    SpinnerView(viewModel = spinnerViewModel)
                    Spacer(modifier = Modifier.padding(vertical = 10.dp))
                    Button(
                        onClick = {
                            val newGame = GameCreateDto(
                                year = spinnerViewModel.year.value,
                                month = spinnerViewModel.month.value,
                                day = spinnerViewModel.day.value,
                                hour = spinnerViewModel.hour.value,
                                minute = spinnerViewModel.minute.value,
                                place = vm.place.value,
                                numberOfUsers = vm.numPlayer.value,
                                gender = vm.gender.value,
                                limitaion =  vm.limitation.value
                            )
                            vm.gameApiService.create(newGame).enqueue(object:
                                Callback<UrlResponseDto> {
                                override fun onResponse(
                                    call: Call<UrlResponseDto>,
                                    response: Response<UrlResponseDto>
                                ) {
                                    if (response.isSuccessful) {
                                        navController.navigate(Screen.Home.route)
                                    } else {
                                        val errorResponse = Gson().fromJson(
                                            response.errorBody()?.string(),
                                            ErrorResponse::class.java
                                        )

                                        when (errorResponse.message) {
                                            is String -> {
                                                openDialog = true
                                                var message = "";

                                                if (errorResponse.message == "You can't create a game for 여성") {
                                                    message = "여성 전용 경기를 생성할 수 없습니다."
                                                } else if (errorResponse.message == "You can't create a game for 남성") {
                                                    message = "남성 전용 경기를 생성할 수 없습니다."
                                                } else if (errorResponse.message == "5vs5 game can only be created by users with 세미프로1 level or higher.") {
                                                    message = "5vs5 경기는 세미프로1 이상만 생성할 수 있습니다."
                                                } else if (errorResponse.message == "You can't create this game - Level limit 세미프로1 이상") {
                                                    message = "세미프로 1 이상인 사람만 생성 가능합니다."
                                                }

                                                errorMessage = message
                                            }
                                            is List<*> -> {
                                                openDialog = true
                                                var message = "";

                                                if (errorResponse.message[0].toString() == "place should not be empty") {
                                                    message = "경기 장소를 입력하세요."
                                                } else if (errorResponse.message[0].toString() == "number_of_users should not be empty") {
                                                    message = "경기 인원수를 선택하세요."
                                                } else if (errorResponse.message[0].toString() == "gender should not be empty") {
                                                    message = "성별을 선택하세요."
                                                } else if (errorResponse.message[0].toString() == "level_limit must be a valid enum value") {
                                                    message = "레벨 제한을 선택하세요."
                                                }
                                                errorMessage = message
                                            }
                                            else -> {
                                                Log.d("Any", errorResponse.message.toString())
                                            }
                                        }

                                        if (errorResponse.message == "Expired access token") {
                                            ReAuthService.getInstance().reauth().enqueue(object :
                                                Callback<DummyResponse> {
                                                override fun onResponse(
                                                    call: Call<DummyResponse>,
                                                    response: Response<DummyResponse>
                                                ) {
                                                    if (response.isSuccessful) {
                                                        val tokens = CookieChanger<DummyResponse>().change(response)
                                                        AuthService.getCookieJar().saveToken(tokens)
                                                    }
                                                }
                                                override fun onFailure(
                                                    call: Call<DummyResponse>,
                                                    t: Throwable
                                                ) {
                                                    Log.i("test", "실패$t")
                                                }
                                            })
                                        }
                                    }
                                }
                                override fun onFailure(call: Call<UrlResponseDto>, t: Throwable) {
                                    Log.i("test", "실패$t")
                                }
                            })
                        },
                        modifier = Modifier
                            .fillMaxWidth(0.8f)
                            .height(intrinsicSize = IntrinsicSize.Max),
                        shape = MaterialTheme.shapes.medium,
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = MaterialTheme.colorScheme.secondary,
                        ),
                    ) {
                        Text(
                            text = stringResource(id = R.string.create_room),
                            color = MaterialTheme.colorScheme.onPrimary,
                            fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                            fontWeight = FontWeight.ExtraBold
                        )
                    }
                }
            }
        }
    }
}

@Preview(
    name = "Light Mode",
    showBackground = true
)
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    name = "Dark Mode",
    showBackground = true
)
@Composable
fun PreviewMatchCreation() {
    UaepTheme {
        MatchCreationScreen(
            vm = MatchCreationScreenViewModel(),
            navController = rememberNavController()
        )
    }
}