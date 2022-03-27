package com.example.uaep.login

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextFieldDefaults.outlinedTextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun EmailOutlinedTextField(
    text: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: @Composable (() -> Unit)? = null,
    placeholder: @Composable (() -> Unit)? = null,
    singleLine: Boolean = false,
    color: Color,
) {
    OutlinedTextField(
        value = text,
        onValueChange = onValueChange,
        label = label,
        placeholder = placeholder,
        singleLine = singleLine,
        modifier = modifier.fillMaxWidth(0.8f),
        colors = outlinedTextFieldColors(
            unfocusedBorderColor = color,
            unfocusedLabelColor = color,
            focusedLabelColor = color,
            focusedBorderColor = color
        )
    )
}
