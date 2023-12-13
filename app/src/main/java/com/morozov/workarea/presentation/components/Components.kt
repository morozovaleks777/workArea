package com.morozov.workarea.presentation.components

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.res.Configuration
import android.os.Build
import android.view.View
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.navigation.NavController
import com.google.accompanist.systemuicontroller.SystemUiController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.morozov.workarea.R


fun Context.getActivity(): Activity? = when (this) {
    is Activity -> this
    is ContextWrapper -> baseContext.getActivity()
    else -> null
}

@Composable
fun SystemUI(
    view: View = LocalView.current,
    systemUiController: SystemUiController = rememberSystemUiController(),
    configuration: Configuration = LocalConfiguration.current

) {
    SideEffect {

        systemUiController.setSystemBarsColor(
            color = Color.Transparent,
            darkIcons = false, isNavigationBarContrastEnforced = true,
            transformColorForLightContent = { Color.Transparent })

        //  keeps hidden and on swipe transparent bars are shown
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            systemUiController.isNavigationBarVisible = false
            systemUiController.systemBarsBehavior =
                WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R) {
            val window = view.context.getActivity()!!.window
            window.navigationBarColor = Color.Transparent.toArgb()
            val decorView = window.decorView
            decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
                    View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
                    View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        }
    }



    val window = view.context.getActivity()!!.window
    WindowCompat.setDecorFitsSystemWindows(window, false)
    WindowInsetsControllerCompat(window, window.decorView).apply {
        systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_DEFAULT
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DWAppBar(
    title: @Composable () -> Unit,
    icon: ImageVector? = null,
    showProfile: Boolean = true,
    showLogo: Boolean = showProfile,
    navController: NavController,
    search:() -> Unit = {},
    getNotification:() -> Unit = {},
    onBackArrowClicked:() -> Unit = {}
) {

    TopAppBar(
        title = {
            Row(modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween){
                title.invoke()

            }
        },
        actions = {
            Row(
                modifier = Modifier,
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ){
                IconButton(
                    onClick = {search()},
                    enabled = true


                ) {
                    if (showProfile) Row() {
                        Image(
                            painter = painterResource(id = R.drawable.ion_notifications_outline_search),
                            contentDescription = null,
                            modifier = Modifier.size(42.dp)
                        )
                    } else Box {}
                }
                IconButton(
                    onClick = {getNotification()},
                    enabled = true

                ) {
                    if (showProfile) Row() {
                        Image(
                            painter = painterResource(id = R.drawable.icon_notifications_outline),
                            contentDescription = null,
                            modifier = Modifier.size(42.dp)
                        )
                    } else Box {}
                }
            }

        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent ),
    )
}