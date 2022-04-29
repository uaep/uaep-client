package com.example.uaep

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import com.example.uaep.ui.theme.UaepTheme
import com.example.uaep.view.CommonScreenView
import com.example.uaep.view.LoginView
import com.example.uaep.view.SignUpView
import com.example.uaep.viewmodel.LoginViewModel
import com.example.uaep.viewmodel.SignUpViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { // to define layout
            UaepTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background,
                ) {
//                    LoginView(LoginViewModel())
                    SignUpView(SignUpViewModel())
//                    CommonScreenView()
                }
            }
        }
    }
}