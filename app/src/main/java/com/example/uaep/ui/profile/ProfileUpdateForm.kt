package com.example.uaep.ui.profile

import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.uaep.R
import com.example.uaep.ui.theme.UaepTheme

@Composable
fun ProfileUpdateForm() {
    var name by rememberSaveable { mutableStateOf("") }
    var position by rememberSaveable { mutableStateOf("") }
    var address by rememberSaveable { mutableStateOf("") }

    Column(
        modifier = Modifier.padding(start = 30.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = {
                Text(
                    text = stringResource(id = R.string.name),
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = MaterialTheme.typography.labelLarge.fontSize,
                    fontWeight = FontWeight.Bold
                )
            },
            placeholder = {
                Text(
                    text = stringResource(id = R.string.update_name),
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = MaterialTheme.typography.labelLarge.fontSize,
                    fontWeight = FontWeight.Bold
                )
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                textColor = MaterialTheme.colorScheme.primary,
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = MaterialTheme.colorScheme.primary,
            )
        )
        OutlinedTextField(
            value = position,
            onValueChange = { position = it },
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
                    text = stringResource(id = R.string.update_position),
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = MaterialTheme.typography.labelLarge.fontSize,
                    fontWeight = FontWeight.Bold
                )
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                textColor = MaterialTheme.colorScheme.primary,
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = MaterialTheme.colorScheme.primary,
            )
        )
        OutlinedTextField(
            value = address,
            onValueChange = { address = it },
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
                    text = stringResource(id = R.string.update_address),
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = MaterialTheme.typography.labelLarge.fontSize,
                    fontWeight = FontWeight.Bold
                )
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                textColor = MaterialTheme.colorScheme.primary,
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = MaterialTheme.colorScheme.primary,
            )
        )
        Button(
            onClick = {
                // TODO: 프로필 수정 API에 HTTP 요청
                Log.d("http", name)
                Log.d("http", address)
                Log.d("http", position)
            }
        ) {
            Text(text = stringResource(id = R.string.update_profile))
        }
    }
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
fun PreviewProfileUpdateForm() {
    UaepTheme {
        ProfileUpdateForm()
    }
}