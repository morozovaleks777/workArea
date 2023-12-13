package com.morozov.workarea.presentation.screens.splash

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.navigation.NavHostController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
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
        navController.navigate(AppScreens.HomeScreen.name)
    }
}