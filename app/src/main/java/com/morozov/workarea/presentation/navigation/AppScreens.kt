package com.morozov.workarea.presentation.navigation

enum class AppScreens {
    SplashScreen,
    HomeScreen,
    AuthScreen
    ;

    companion object {
        fun String.asAppScreen(): AppScreens {
            return values().find { contains(it.name, ignoreCase = true) }
                ?: throw IllegalArgumentException("This string is NOT a route")
        }


    }
}