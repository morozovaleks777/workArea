package com.morozov.workarea.presentation.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavDeepLink
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.morozov.workarea.presentation.navigation.NavigationUtils.getExtrasViewModel
import com.morozov.workarea.presentation.screens.auth.AuthScreen
import com.morozov.workarea.presentation.screens.homeScreen.HomeScreen
import com.morozov.workarea.presentation.screens.splash.SplashScreen

private const val ANIMATION_SPEED = 900


@OptIn(
    ExperimentalAnimationApi::class,
)
@ExperimentalComposeUiApi
@Composable
fun AppNavigation(
    navController: NavHostController,
    isWideScreen: Boolean,
    showUpdateAppBanner: Boolean,
) {

    NavHost(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
        navController = navController,
        startDestination = AppScreens.SplashScreen.name,
    ) {
        composableAnimated(
            route = AppScreens.SplashScreen.name,
        ) {
            SplashScreen(navController = navController, isWideScreen = isWideScreen)
        }
        composableAnimated(AppScreens.HomeScreen.name) {
            val transition = rememberUpdatedState(newValue = transition)
            HomeScreen(
                navController = navController,
                homeViewModel = hiltViewModel(),
                sharedViewModel = navController.getExtrasViewModel(navEntry = it),
                showUpdateAppBanner = showUpdateAppBanner,
                isWideScreen = isWideScreen
            )
        }

        composableAnimated(
            route = AppScreens.AuthScreen.name,
            enterTransition = Transitions.FADE_IN,
            exitTransition = Transitions.FADE_OUT,
        ) {
            AuthScreen(
                navController = navController,
                viewModel = hiltViewModel(),
                isWideScreen = isWideScreen,
            )
        }
    }
}



private fun NavGraphBuilder.composableAnimated(
    route: String,
    arguments: List<NamedNavArgument> = emptyList(),
    deepLinks: List<NavDeepLink> = emptyList(),
    enterTransition: AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition? = Transitions.SLIDE_IN,
    exitTransition: AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition? = Transitions.SLIDE_OUT,
    content: @Composable AnimatedVisibilityScope.(NavBackStackEntry) -> Unit,
) {
    composable(
        route = route,
        arguments = arguments,
        deepLinks = deepLinks,
        enterTransition = enterTransition,
        exitTransition = exitTransition,
        content = content
    )
}

object Transitions {
    val SLIDE_IN: AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition? = {
        slideIntoContainer(
            towards = AnimatedContentTransitionScope.SlideDirection.Up,
            animationSpec = tween(ANIMATION_SPEED)
        )
    }
    val SLIDE_OUT: AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition? = {
        slideOutOfContainer(
            towards = AnimatedContentTransitionScope.SlideDirection.Down,
            animationSpec = tween(ANIMATION_SPEED)
        )
    }
    val FADE_IN: AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition? = {
        fadeIn(
            animationSpec = tween(ANIMATION_SPEED)
        )
    }
    val FADE_OUT: AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition? = {
        fadeOut(
            animationSpec = tween(ANIMATION_SPEED)
        )
    }
}

object NavigationUtils {

    fun NavController.navigate(screens: AppScreens, vararg arguments: Any) {
        var basicRoute = screens.name


        basicRoute += when (arguments.isEmpty()) {
            true -> when (screens) {
                AppScreens.AuthScreen -> "/"
                else -> ""
            }

            false -> when (screens) {
                AppScreens.AuthScreen -> "/?${NavigationArguments.ARG_AUTH_STATE}=${arguments[0]}"
                else -> arguments.joinToString(separator = "/")
            }
        }

        navigate(route = basicRoute)
    }

    @Composable
    fun NavController.getExtrasViewModel(navEntry: NavBackStackEntry): SharedViewModel {
        val parentEntry =
            remember(navEntry) { getBackStackEntry(AppScreens.HomeScreen.name) }

        return hiltViewModel(parentEntry)
    }

}

object NavigationData {
    const val SELECTED_SHOW = "selected_show_"
    const val SELECTED_EPISODE = "selected_episode_"
    const val SELECTED_SHOW_EPISODES = "selected_show_episodes"
}

object NavigationArguments {
    const val ARG_AUTH_STATE = "arg_auth_state_"
}
