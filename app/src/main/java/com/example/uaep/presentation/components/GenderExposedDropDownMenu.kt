package com.example.uaep.presentation.components

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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.uaep.ui.theme.md_theme_light_primary
import com.example.uaep.presentation.signup.SignUpViewModel

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
                        vm.onEnabled(!vm.enabled.value)
                    },
                colors = outlinedTextFieldColors(
                    unfocusedBorderColor = color,
                    unfocusedLabelColor = color,
                    focusedLabelColor = color,
                    focusedBorderColor = color
                ),
                trailingIcon = {
                    Icon(
                        imageVector = vm.icon,
                        contentDescription = null,
                        Modifier.clickable {
                            vm.onEnabled(!vm.enabled.value)
                        }
                    )
                }
            )
        }
        DropdownMenu(
            expanded = vm.enabled.value,
            onDismissRequest = {
                vm.onEnabled(false)
            },
            modifier = Modifier.width(300.dp),
        ) {
            DropdownMenuItem(
                onClick = {
                    vm.updateGender("male")
                    vm.onEnabled(false)
                }
            ) {
                Text(
                    text = "남성",
                    color = md_theme_light_primary,
                    fontWeight = FontWeight(1000)
                )
            }
            DropdownMenuItem(
                onClick = {
                    vm.updateGender("female")
                    vm.onEnabled(false)
                }
            ) {
                Text(
                    text = "여성",
                    color = md_theme_light_primary,
                    fontWeight = FontWeight(1000)
                )
            }
        }
    }
}