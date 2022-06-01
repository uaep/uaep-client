package com.example.uaep

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat
import com.example.uaep.ui.UaepApp
import com.example.uaep.utils.rememberWindowSizeClass


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, true)

        setContent {

            val windowSizeClass = rememberWindowSizeClass()
            UaepApp(windowSize = windowSizeClass)

        }
    }
}

//@Composable
//fun HelloScreen() {
//    var name by rememberSaveable { mutableStateOf("") }
//
//    HelloContent(name = name, onNameChange = { name = it })
//}
//
//@Composable
//fun HelloContent(name: String, onNameChange: (String) -> Unit) {
//    Column(modifier = Modifier.padding(16.dp)) {
//        Text(
//            text = "Hello, $name",
//            modifier = Modifier.padding(bottom = 8.dp),
//            style = MaterialTheme.typography.h5
//        )
//        OutlinedTextField(
//            value = name,
//            onValueChange = onNameChange,
//            label = { Text("Name") }
//        )
//    }
//}