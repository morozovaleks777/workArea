package com.morozov.workarea.presentation.screens.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.morozov.workarea.R
import com.morozov.workarea.presentation.navigation.AppScreens
import com.morozov.workarea.ui.theme.ColorsExtra
import kotlinx.coroutines.delay


@Composable
fun LoginScreen(
    isWideScreen: Boolean,
    navController: NavController


){

    LaunchedEffect(Unit){
delay(3000)
navController.navigate(AppScreens.HomeScreen.name)
    }
    Box(modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {

            Image(
                painter = painterResource(id = R.drawable.ic_done),
                contentDescription = "",
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .size(50.dp)
                    .scale(1.3f)

            )
            Spacer(modifier = Modifier.height(48.dp))
            Text(text = "LOGGED IN",
            style = MaterialTheme.typography.labelMedium.copy(fontFamily =
                FontFamily(Font(R.font.libre_franklin_black, FontWeight(600))),
                fontSize = 14.sp,
                color = ColorsExtra.gray_7
            )
            )
        }
    }
}