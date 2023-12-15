
package com.morozov.workarea.presentation.screens.auth


import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.morozov.workarea.R
import com.morozov.workarea.presentation.screens.homeScreen.components.NavigationItem
import com.morozov.workarea.ui.theme.AppTheme
import com.morozov.workarea.ui.theme.ColorsExtra
import kotlinx.coroutines.delay
import timber.log.Timber

const val STEP_TRANSITION_DURATION: Int = 300

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthScreen(
    navController: NavHostController,
    viewModel: AuthViewModel,
    isWideScreen: Boolean,
) {


    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val currentStep = uiState.step

    val sheetState = rememberBottomSheetScaffoldState(
        bottomSheetState = rememberStandardBottomSheetState(
            initialValue = SheetValue.PartiallyExpanded,
        )
    )
    val logoImage by remember {
        mutableIntStateOf( R.drawable.logo_small)
    }
    val logoImageBlack by remember {
        mutableIntStateOf( R.drawable.logo_small_black)
    }
    val loginText by remember {
        mutableStateOf("BE PART OF \nTHE CULTURE")
    }
    LaunchedEffect(key1 = currentStep) {

        // Bottomsheet behavior
        when (currentStep) {
            is AuthInitialStep -> {
                /*
                Sometimes, while navigating back, user might trigger settling for animation. In that case, our incoming state animations will be canceled.
                We're adding a dumb delay to wait for sheet to settle before trying to animate it's state.
                If you have any better option for this - you're welcome
                */
                delay(STEP_TRANSITION_DURATION.toLong())
                sheetState.bottomSheetState.partialExpand()
            }

            is AuthSkip -> navController.popBackStack()

            else -> sheetState.bottomSheetState.expand()
        }

        when (currentStep) {

            is PurchaseDoneStep -> {}
//                AnalyticsManager.Purchase.subscriptionPaid(
//                context = context,
//                planCode = currentStep.billing.planCode,
//                calculatePrice = currentStep.billing::planPriceAsNumber
//            )

            else -> {}
        }
    }

    BottomSheetScaffold(
        sheetContent = {
            AnimateSheetContent(targetState = uiState) {
                AuthFlowSheet(
                    it,
                    onExit = { navController.popBackStack().also { viewModel.reset() } },
                    viewModel = viewModel,
                    logoImageBlack = logoImageBlack,
                    isWideScreen = isWideScreen
                )
            }
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
            isWideScreen = isWideScreen,
            loginText = loginText,
            logoImage = logoImage)
    }
}


@Composable
fun ContentForFirstPage(
    isWideScreen: Boolean,
    loginText: String,
    logoImage: Int

) {
    val gradient = Brush.linearGradient(
        colors = listOf(Color.Black, Color.Transparent),
        start = Offset(0.0f, Float.POSITIVE_INFINITY),
        end = Offset(Float.POSITIVE_INFINITY, 0.0f)
    )

//    Image(
//        painter = rememberAsyncImagePainter(imageUrl),
//        contentDescription = null,
//        modifier = Modifier
//            .fillMaxSize()
//            .scale(1f),
//        contentScale = ContentScale.Crop
//    )
    Image(
        painter = if (isWideScreen) {
            // needs to be changed
            painterResource(R.drawable.image_for_login_screen)
        } else {
            painterResource(R.drawable.image_for_login_screen)
        },
        contentDescription = "",
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .fillMaxSize()
    )
    Box(
        modifier = Modifier
            .fillMaxSize()
         //  .background(gradient )
    ) {

        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(id = logoImage),
                contentDescription = "",
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .padding(bottom = 411.dp, start = 4.dp)
                    .size(149.dp)
                    .scale(0.7f)
                    .align(if (isWideScreen) Alignment.BottomCenter else Alignment.BottomStart)
            )
            Text(
                text = loginText,
                style = MaterialTheme.typography.headlineLarge.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 36.sp,
                    lineHeight = 43.sp,
                    color = Color.White,
                ),
                textAlign = if (isWideScreen) TextAlign.Center else TextAlign.Start,
                modifier = Modifier
                    .padding(bottom = 312.dp, start = 24.dp)
                    .align(if (isWideScreen) Alignment.BottomCenter else Alignment.BottomStart)
            )

            Row(
                modifier = Modifier
                    .padding(bottom = 248.dp, start = 24.dp)
                    .align(if (isWideScreen) Alignment.BottomCenter else Alignment.BottomStart)
            ) {
                IconsRow()
            }

        }
    }
}



@Composable
private fun IconsRow() {
    val items = listOf(
        Triple(R.drawable.ellipse_29, R.drawable.listen, "click1"),
        Triple(R.drawable.ellipse_29, R.drawable.play, "click2"),
        Triple(R.drawable.ellipse_29, R.drawable.inactive, "click3")
    )
    CircularProgressIndicatorSample(
        image = R.drawable.active,
        progress = 0.25f,
        size = 17.dp,
        onClick = { Timber.tag("auth").d("ContentForFirstPage: click") }
    )
    Spacer(modifier = Modifier.width(8.dp))
    items.forEach { (mainImage, subImage, clickTag) ->

        Box(
            modifier = Modifier
                .wrapContentSize()
                .clickable {
                    Timber
                        .tag("auth")
                        .d("ContentForFirstPage: $clickTag")
                },
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = mainImage),
                contentDescription = null,
                modifier = Modifier.size(40.dp)
            )
            Image(
                painter = painterResource(id = subImage),
                contentDescription = null,
                modifier = Modifier
                    .size(20.dp)
                    .clickable {
                        Timber
                            .tag("auth")
                            .d("ContentForFirstPage: $clickTag")
                    }
            )
        }

        Spacer(modifier = Modifier.width(8.dp))
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

@Composable
private fun <T : AuthStepState> AnimateSheetContent(
    targetState: AuthViewModel.AuthUiState<T>,
    content: @Composable AnimatedContentScope.(AuthViewModel.AuthUiState<T>) -> Unit,
) {
    AnimatedContent(
        targetState = targetState,
        contentKey = AuthViewModel.AuthUiState<T>::direction,
        transitionSpec = {
            val targetOffsetK =
                if (targetState.direction is AuthViewModel.AuthUiState.Next) 1 else -1

            slideInHorizontally(
                animationSpec = tween(STEP_TRANSITION_DURATION),
                initialOffsetX = { fullWidth -> fullWidth * targetOffsetK }
            ) togetherWith
                    slideOutHorizontally(
                        animationSpec = tween(STEP_TRANSITION_DURATION),
                        targetOffsetX = { fullWidth -> -fullWidth * targetOffsetK }
                    )
        },
        label = "step-by-step"
    ) {
        content(it)
    }
}

@Composable
fun <T : AuthStepState> AuthFlowSheet(
    uiState: AuthViewModel.AuthUiState<T>,
    currentState: AuthStepState = uiState.step,
    isWideScreen: Boolean,
    logoImageBlack: Int,
    onExit: () -> Unit,
    viewModel: AuthViewModel,
) {
    val context = LocalContext.current

    val exit: (String) -> Unit = { label ->
       // AnalyticsManager.Purchase.ctaClicked(context = context, element = label)
        viewModel.reset()
    }

    Column(
        modifier = Modifier
            .fillMaxHeight(AppTheme.dimens.bottomSheetHeightFraction)
            .fillMaxWidth()
         //   .background(
//                brush = Brush.verticalGradient(
//                    listOf(
//                        Color.Transparent,
//                        Color.Black
//                    )
//                )
//            )
    ) {
        when (currentState) {
            is AuthInitialStep -> {
                AuthActions(
                    modifier = Modifier
                        .padding(horizontal = 32.dp)
                        .padding(top = 32.dp, bottom = 40.dp),
                    primaryButtonText = stringResource(R.string.sign_up),
                    primaryButtonClick = { label ->
                     //   AnalyticsManager.Purchase.ctaClicked(context = context, element = label)
                        currentState.startSignUp()
                    },
                    secondaryButtonText = stringResource(R.string.log_in),
                    secondaryButtonClick = { label ->
                    //    AnalyticsManager.Purchase.ctaClicked(context = context, element = label)
                        currentState.startSignIn()
                    }
                )
            }

            is LoadingStep -> {} //AuthLoading()

            is DoneStep -> {
                LoginScreen(isWideScreen = isWideScreen, navController = NavController(LocalContext.current) )

            }


            is PurchaseStep -> {}


            is PurchaseDoneStep -> {}


            is SignInCredentialsStep -> {
                LaunchedEffect(Unit) {
                //    AnalyticsManager.Default.view(context, SignInRoute)
                }
                SignInCredentials(
                    onLoginClick = { email, password ->
                        currentState.confirmCredentials(
                            email,
                            password
                        )
                    },
                    onLoginWithLink = {},
                    logoImageBlack = logoImageBlack,
                    isWideScreen = isWideScreen,
                    onForgotPasswordClick = currentState::forgotPassword,
                    email = currentState.email,
                    password = currentState.password,
                    onEmailComplete = currentState::verifyEmail,
                    onPasswordComplete = currentState::verifyPassword,
                    onExit = { label -> exit(label) },
                )
            }

            is ForgotPasswordState -> {}

            is MailSent -> {}


            is SignUpStep -> {
//                LaunchedEffect(Unit) {
//                    AnalyticsManager.Default.view(context, SignUpRoute)
//                }
//                SingUpStep1ParentOrKid(
//                    title = when (currentState.calledFrom) {
//                        SignUpStep.CalledFrom.SignUp -> stringResource(R.string.sign_up_create_account_title)
//                        SignUpStep.CalledFrom.Profile -> stringResource(R.string.age_gate_manage_profile_title)
//                    },
//                    showCloseButton = currentState.calledFrom != SignUpStep.CalledFrom.Profile,
//                    onParentClick = currentState::continueAsParent.invoke(),
//                    onKidClick = currentState::continueAsKid.invoke(),
//                    onExit = { label -> exit(label) }
//                )
            }

            is SignUpCredentialsStep -> {
//                LaunchedEffect(Unit) {
//                    AnalyticsManager.Default.view(context, SignUpFormRoute)
//                }
//                AuthCreateAccount(
//                    onSignUpClick = { email, pass, label ->
//                        AnalyticsManager.Purchase.ctaClicked(context = context, element = label)
//                        currentState.confirmCredentials(email, pass)
//                    },
//                    email = currentState.email,
//                    onEmailComplete = currentState::verifyEmail,
//                    password = currentState.password,
//                    onPasswordComplete = currentState::verifyPassword,
//                    onTermsClick = {
//                        AnalyticsManager.Default.view(context, TermsRoute)
//                        currentState.viewTerms()
//                    },
//                    onNoticeClick = {
//                        AnalyticsManager.Default.view(context, PrivacyRoute)
//                        currentState.viewNotice()
//                    },
//                    onChildrenNoticeClick = {
//                        AnalyticsManager.Default.view(context, ChildrenPrivacyPolicyRoute)
//                        currentState.viewChildrenNotice()
//                    },
//                    onExit = { label -> exit(label) },
//                )
            }
            is LegalContent -> {}

            else -> {}
        }
    }
}

@Composable
fun CircularProgressIndicatorSample(progress: Float = 0.85f, image:Int, size: Dp, onClick: () -> Unit) {

    Row(
        modifier = Modifier
            .wrapContentSize()
            .clickable { onClick() },
        horizontalArrangement = Arrangement.Start
    ) {


        Box(
            modifier = Modifier.size(40.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
            var progress by remember { mutableStateOf(progress) }
            val animatedProgress = animateFloatAsState(
                targetValue = progress,
                animationSpec = ProgressIndicatorDefaults.ProgressAnimationSpec
            ).value
            Box(
                modifier = Modifier.size(40.dp),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = image),
                    contentDescription = null,
                    modifier = Modifier.size(size)
                )

            }


            CircularProgressIndicator(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape),
                progress = animatedProgress,
                color = Color.Red,
                trackColor = ColorsExtra.ForElipseIcon,
                strokeWidth = 1.dp
            )
        }

    }
}

