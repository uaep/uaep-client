package com.example.uaep.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DoDisturbAlt
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.uaep.R
import com.example.uaep.ui.theme.UaepTheme

@Composable
fun ErrorDialog(
    onError: () -> Unit,
    errorMessage: String
) {
    AlertDialog(
        onDismissRequest = {
            // Dismiss the dialog when the user clicks outside the dialog or on the back
            // button. If you want to disable that functionality, simply use an empty
            // onDismissRequest.
            onError()
        },
        icon = {
            Icon(
                imageVector = Icons.Filled.DoDisturbAlt,
                modifier = Modifier.defaultMinSize(60.dp, 60.dp),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.error
            )
        },
        title = {
            Text(text = errorMessage)
        },
        confirmButton = {
            TextButton(
                onClick = onError
            ) {
                Text(stringResource(R.string.confirm))
            }
        },
    )
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
fun PreviewErrorDialog() {
    UaepTheme {
        ErrorDialog(
            onError = {},
            errorMessage = "ㅅㅂ"
        )
    }
}