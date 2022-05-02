package com.example.uaep.navigation

const val EMAIL = "email"

// sealed 클래스는 자식 클래스들을 정의하여 컴파일러에게 알려줄 수 있다.
sealed class Screen(val route: String) {
    object Login: Screen(route = "login_screen")
    object EmailAuth: Screen(route = "email_auth_screen")
    object AuthCode: Screen(route = "auth_code_screen/{$EMAIL}") {
        fun passEmail(email: String): String {
            return this.route.replace(oldValue = "{$EMAIL}", newValue = email)
        }
    }
    object SignUp: Screen(route = "sign_up_screen/{$EMAIL}") {
        fun passEmail(email: String): String {
            return this.route.replace(oldValue = "{$EMAIL}", newValue = email)
        }
    }
}
