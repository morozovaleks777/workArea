package com.morozov.workarea.presentation.screens.splash

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import com.morozov.workarea.presentation.components.SystemUI
import com.morozov.workarea.presentation.navigation.AppScreens
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavHostController, isWideScreen: Boolean) {
    SystemUI()

Text("Splash",
style = MaterialTheme.typography.headlineLarge.copy(color = Color.White))
    LaunchedEffect(Unit){
        delay(1000)
        navController.navigate(AppScreens.AuthScreen.name)
    }
}