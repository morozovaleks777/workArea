package com.morozov.workarea.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.morozov.workarea.presentation.navigation.AppNavigation
import com.morozov.workarea.ui.theme.WorkAreaTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalComposeUiApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            WorkAreaTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.Black
                ) {
                ConceptApp(
                    navController = navController,
                    isWideScreen = false,
                    showUpdateAppBanner = false,
                )
            }
            }
            }
        }
    }



@ExperimentalComposeUiApi
@Composable
fun ConceptApp(
    navController: NavHostController,
    isWideScreen: Boolean,
    showUpdateAppBanner: Boolean,
) {
    Column(
        modifier = Modifier.background(Color.Transparent),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AppNavigation(
            navController = navController,
            isWideScreen = isWideScreen,
            showUpdateAppBanner = showUpdateAppBanner,
        )
    }
}