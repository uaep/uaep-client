package com.example.uaep.ui.login

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material.*
import androidx.compose.material.TextFieldDefaults.outlinedTextFieldColors
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.uaep.viewmodel.SignUpViewModel

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
        // TODO : 성별 선택 시, 메뉴창 없애기
        DropdownMenu(
            expanded = vm.enabled.value,
            onDismissRequest = {
                vm.onEnabled(false)
            },
            modifier = Modifier.width(300.dp)
        ) {
            DropdownMenuItem(
                onClick = {
                    vm.updateGender("남성")
                }
            ) {
                Text("남성")
            }
            DropdownMenuItem(
                onClick = {
                    vm.updateGender("여성")
                }
            ) {
                Text("여성")
            }
        }
    }
}