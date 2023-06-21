package com.example.donasiapk.ui.theme.navigation

sealed class Screen(val route : String){
    object Home : Screen("home")
    object Profile : Screen("Profile")
    object Detail : Screen("home/{donasiId}"){
        fun createRoute(donasiId : String) = "home/$donasiId"
    }
}
