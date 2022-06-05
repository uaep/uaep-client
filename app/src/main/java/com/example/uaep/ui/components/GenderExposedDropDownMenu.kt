package com.example.uaep.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults.outlinedTextFieldColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.uaep.R
import com.example.uaep.ui.signup.SignUpViewModel

@Composable
fun GenderExposedDropDownMenu(
    gender: String,
    label: @Composable (() -> Unit)? = null,
    placeholder: @Composable (() -> Unit)? = null,
    color: Color,
    vm : SignUpViewModel
) {
    Column {
        Box {
            OutlinedTextField(
                value = gender,
                readOnly = true,
                onValueChange = {},
                label = label,
                placeholder = placeholder,
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .clickable {
                        vm.onGenderEnabled(!vm.genderEnabled.value)
                    },
                colors = outlinedTextFieldColors(
                    textColor = color,
                    unfocusedBorderColor = color,
                    unfocusedLabelColor = color,
                    focusedLabelColor = color,
                    focusedBorderColor = color
                ),
                trailingIcon = {
                    Icon(
                        imageVector = vm.icon1,
                        contentDescription = null,
                        Modifier.clickable {
                            vm.onGenderEnabled(!vm.genderEnabled.value)
                        },
                        tint = color
                    )
                }
            )
        }
        DropdownMenu(
            expanded = vm.genderEnabled.value,
            onDismissRequest = {
                vm.onGenderEnabled(false)
            },
            modifier = Modifier.width(300.dp).background(MaterialTheme.colorScheme.onBackground),
        ) {
            DropdownMenuItem(
                onClick = {
                    vm.updateGender("남성")
                    vm.onGenderEnabled(false)
                },
                modifier = Modifier.background(MaterialTheme.colorScheme.onBackground)
            ) {
                Text(
                    text = stringResource(R.string.man),
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight(1000)
                )
            }
            DropdownMenuItem(
                onClick = {
                    vm.updateGender("여성")
                    vm.onGenderEnabled(false)
                },
                modifier = Modifier.background(MaterialTheme.colorScheme.onBackground)
            ) {
                Text(
                    text = stringResource(R.string.woman),
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight(1000)
                )
            }
        }
    }
}