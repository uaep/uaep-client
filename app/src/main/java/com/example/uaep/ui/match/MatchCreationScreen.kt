package com.example.uaep.ui.match

import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import com.example.uaep.R
import com.example.uaep.ui.components.SpinnerView
import com.example.uaep.ui.components.SpinnerViewModel
import com.example.uaep.ui.theme.UaepTheme

@Composable
fun MatchCreationScreen (
    vm: MatchCreationScreenViewModel,
) {
    val spinnerViewModel = SpinnerViewModel()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp, bottom = 10.dp, start = 5.dp, end = 5.dp)
                .border(
                    border = BorderStroke(
                        width = 2.dp,
                        color = MaterialTheme.colorScheme.primaryContainer
                    ),
                    shape = MaterialTheme.shapes.extraLarge
                )
                .background(
                    color = MaterialTheme.colorScheme.onBackground,
                    shape = MaterialTheme.shapes.extraLarge
                ),
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(
                text = stringResource(id = R.string.app_name).uppercase(),
                modifier = Modifier,
                color = MaterialTheme.colorScheme.primary,
                fontSize = MaterialTheme.typography.headlineLarge.fontSize,
                fontWeight = FontWeight.Bold,
                letterSpacing = 0.05.em
            )
            Text(
                text = stringResource(id = R.string.create_room).uppercase(),
                modifier = Modifier,
                color = MaterialTheme.colorScheme.primary,
                fontSize = MaterialTheme.typography.headlineLarge.fontSize,
                fontWeight = FontWeight.Bold,
            )
            OutlinedTextField(
                value = vm.title.value,
                onValueChange = { vm.updateTitle(it) },
                label = {
                    Text(
                        text = stringResource(R.string.title),
                        color = MaterialTheme.colorScheme.primary,
                        fontSize = MaterialTheme.typography.labelLarge.fontSize,
                        fontWeight = FontWeight.Bold
                    )
                },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth(0.8f),
                shape = MaterialTheme.shapes.medium,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    textColor = MaterialTheme.colorScheme.primary,
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.primary,
                )
            )
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
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth(0.8f),
                shape = MaterialTheme.shapes.medium,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    textColor = MaterialTheme.colorScheme.primary,
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.primary,
                )
            )
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
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        textColor = MaterialTheme.colorScheme.primary,
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.primary,
                    ),
                    trailingIcon = {
                        Icon(
                            imageVector = vm.icon,
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
                            vm.updateNumPlayer("6vs6")
                            vm.onNumPlayerSelected()
                        }
                    ) {
                        Text(
                            text = "6vs6",
                            color = MaterialTheme.colorScheme.primary,
                            fontSize = MaterialTheme.typography.labelLarge.fontSize,
                            fontWeight = FontWeight.ExtraBold
                        )
                    }
                }
            }
            Column(
                verticalArrangement = Arrangement.spacedBy(15.dp)
            ) {
                SpinnerView(viewModel = spinnerViewModel)
                Button(
                    onClick = {
                        //                        navController.navigate(route = Screen.EmailAuth.route)
                        Log.d("year : ", spinnerViewModel.year.value.toString())
                        Log.d("month : ", spinnerViewModel.month.value.toString())
                        Log.d("day : ", spinnerViewModel.day.value.toString())
                        Log.d("hour : ", spinnerViewModel.hour.value.toString())
                        Log.d("minute : ", spinnerViewModel.minute.value.toString())
                        Log.d("title : ", vm.title.value)
                        Log.d("place : ", vm.place.value)
                        Log.d("num_of_users : ", vm.numPlayer.value)
                        // TODO: HTTP Request, Navigating to the home screen
                    },
                    modifier = Modifier
                        .fillMaxWidth(0.8f),
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
            vm = MatchCreationScreenViewModel()
        )
    }
}