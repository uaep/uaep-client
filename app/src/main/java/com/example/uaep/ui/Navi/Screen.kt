package com.example.uaep.ui.Navi

const val EMAIL = "email"
const val TOKEN = "token"

// sealed 클래스는 자식 클래스들을 정의하여 컴파일러에게 알려줄 수 있다.
sealed class Screen(val route: String) {
    object Login: Screen(route = "login_screen")
    object EmailAuth: Screen(route = "email_auth_screen")
    object AuthCode: Screen(route = "auth_code_screen/{$EMAIL}/{$TOKEN}") {
        fun passEmailAndToken(email: String, token: String): String {
            return "auth_code_screen/$email/$token"
        }
    }
    object SignUp: Screen(route = "sign_up_screen/{$EMAIL}/{$TOKEN}") {
        fun passEmailAndToken(email: String, token: String): String {
            return "sign_up_screen/$email/$token"
        }
    }
    object Home: Screen(route = "home")
    //object Room: Screen(route = "room")
}
