package com.morozov.workarea.presentation.screens.splash

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.morozov.workarea.presentation.components.SystemUI
import com.morozov.workarea.presentation.navigation.AppScreens
import com.morozov.workarea.presentation.navigation.NavigationUtils.navigate
import com.morozov.workarea.presentation.screens.auth.AuthMainStates
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController, isWideScreen: Boolean) {
    SystemUI()
    Box(modifier = Modifier,
    contentAlignment = Alignment.Center) {
        Text(
            "Splash",
            style = MaterialTheme.typography.headlineLarge.copy(color = Color.White)
        )
        LaunchedEffect(Unit) {
            delay(1000)
            navController.navigate(AppScreens.HomeScreen.name) {
                popUpTo(navController.graph.id) { inclusive = true }
            }
            delay(200)
            navController.navigate(AppScreens.AuthScreen, AuthMainStates.Initial)
        }
    }
}

