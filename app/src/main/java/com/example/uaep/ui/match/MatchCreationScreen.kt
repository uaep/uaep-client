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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.uaep.R
import com.example.uaep.dto.GameCreateDto
import com.example.uaep.enums.Gender
import com.example.uaep.enums.NumPlayers
import com.example.uaep.ui.components.CommonTopAppBar
import com.example.uaep.ui.components.SpinnerView
import com.example.uaep.ui.components.SpinnerViewModel
import com.example.uaep.ui.navigate.BottomNavigationBar
import com.example.uaep.ui.theme.UaepTheme

@Composable
fun MatchCreationScreen (
    vm: MatchCreationScreenViewModel = MatchCreationScreenViewModel(),
    navController: NavController
) {
    val spinnerViewModel = SpinnerViewModel()
    Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState())) {
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
                Spacer(modifier = Modifier.padding(vertical = 30.dp))
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
                    // TODO: 레벨 제한
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
                            )
                            Log.i("create_date", newGame.toString())
                            vm.postGameCreation(
                                newGame,
                                navController
                            )
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