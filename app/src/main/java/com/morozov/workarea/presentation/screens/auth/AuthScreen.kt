
package com.morozov.workarea.presentation.screens.auth


import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.morozov.workarea.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthScreen(
    navController: NavHostController,
    viewModel: AuthViewModel,
    isWideScreen: Boolean,
) {


   // val uiState by viewModel.uiState.collectAsStateWithLifecycle()
   // val currentStep = uiState.step

    val sheetState = rememberBottomSheetScaffoldState(
        bottomSheetState = rememberStandardBottomSheetState(
            initialValue = SheetValue.PartiallyExpanded,
        )
    )

    BottomSheetScaffold(
        sheetContent = {
//            AnimateSheetContent(targetState = uiState) {
//                AuthFlowSheet(
//                    it,
//                    onExit = { navController.popBackStack().also { viewModel.reset() } },
//                    viewModel = viewModel
//                )
//            }
        },
        scaffoldState = sheetState,
        sheetPeekHeight = 200.dp,
        contentColor = Color.Black,
        containerColor = Color.White,
        sheetContentColor = Color.Black,
        sheetContainerColor = Color.White,
        sheetDragHandle = {
           // if (currentStep.hasPrevious) {
                SheetDragHandler()
         //   }
        },
        sheetSwipeEnabled = true, //currentStep.hasPrevious,
        sheetShape = RoundedCornerShape(topEnd = 0.dp, topStart = 0.dp),
    ) {
        ContentForFirstPage(
            isWideScreen = isWideScreen)
    }
}


@Composable
fun ContentForFirstPage(
    isWideScreen: Boolean,

) {
    val gradient = Brush.linearGradient(
        colors = listOf(Color.Transparent, Color.Transparent),
        start = Offset(0.0f, Float.POSITIVE_INFINITY),
        end = Offset(Float.POSITIVE_INFINITY, 0.0f)
    )

    Image(
        painter = if (isWideScreen) {
            // needs to changed
            painterResource(R.drawable.portrait_login_image)
        } else {
            painterResource(R.drawable.portrait_login_image)
        },
        contentDescription = "",
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .fillMaxSize()
    )
    Box(
        modifier = Modifier
            .fillMaxSize()
           // .background(gradient)
    ) {

        Box(modifier = Modifier.fillMaxSize()) {
      //      Image(
//                painter = painterResource(id = R.drawable.portrait_login_image),
//                contentDescription = "",
//                contentScale = ContentScale.FillWidth,
//                modifier = Modifier
//                    .padding(bottom = 310.dp, start = 12.dp)
//                    .size(149.dp)
//                    .align(if (isWideScreen) Alignment.BottomCenter else Alignment.BottomStart)
//            )
            Text(
                text = "",
                style = MaterialTheme.typography.headlineLarge.copy(
                    fontWeight = FontWeight.Bold,
                    lineHeight = 36.sp,
                    color = Color.White,
                ),
                textAlign = if (isWideScreen) TextAlign.Center else TextAlign.Start,
                modifier = Modifier
                    .padding(bottom = 220.dp, start = 32.dp)
                    .align(if (isWideScreen) Alignment.BottomCenter else Alignment.BottomStart)
            )
        }
    }
}

@Composable
fun SheetDragHandler() {
    Box(
        modifier = Modifier
            .padding(4.dp)
            .width(36.dp)
            .height(4.dp)
            .background(
                shape = RoundedCornerShape(3.dp),
                color = Color.White.copy(alpha = 0.5f)
            )
    )
}

//@Composable
//private fun <T : AuthStepState> AnimateSheetContent(
//    targetState: AuthViewModel.AuthUiState<T>,
//    content: @Composable AnimatedContentScope.(AuthViewModel.AuthUiState<T>) -> Unit,
//) {
//    AnimatedContent(
//        targetState = targetState,
//        contentKey = AuthViewModel.AuthUiState<T>::direction,
//        transitionSpec = {
//            val targetOffsetK =
//                if (targetState.direction is AuthViewModel.AuthUiState.Next) 1 else -1
//
//            slideInHorizontally(
//                animationSpec = tween(STEP_TRANSITION_DURATION),
//                initialOffsetX = { fullWidth -> fullWidth * targetOffsetK }
//            ) togetherWith
//                    slideOutHorizontally(
//                        animationSpec = tween(STEP_TRANSITION_DURATION),
//                        targetOffsetX = { fullWidth -> -fullWidth * targetOffsetK }
//                    )
//        },
//        label = "step-by-step"
//    ) {
//        content(it)
//    }
//}

