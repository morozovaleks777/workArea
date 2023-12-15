package com.morozov.workarea.presentation.components

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.res.Configuration
import android.os.Build
import android.view.View
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.navigation.NavController
import com.google.accompanist.systemuicontroller.SystemUiController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.morozov.workarea.R
import com.morozov.workarea.presentation.screens.auth.EmailState
import com.morozov.workarea.utils.StringUtils


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


@Composable
fun BaseOutlinedButton(
    onButtonClick: () -> Unit,
    buttonText: String,
    modifier: Modifier = Modifier,
    isButtonEnabled: Boolean = true,
    containerColor: Color = Color.Black, //ColorsExtra.SolidLight100,
    disabledContainerColor: Color =  Color.Gray,//ColorsExtra.Gray600,
    textColor: Color = Color.White, //ColorsExtra.SolidDark100,
    textColorDisabled: Color = Color.Black, //ColorsExtra.SolidDisabled100,
    radius: Dp = 4.dp,
    borderColor: Color = Color.Transparent,
    textStyle: TextStyle = MaterialTheme.typography.labelLarge,
) {
    OutlinedButton(
        shape = RoundedCornerShape(radius),
        border = BorderStroke(1.dp, borderColor),
        colors = ButtonDefaults.buttonColors(
            contentColor = textColor,
            disabledContentColor = textColorDisabled,
            containerColor = containerColor,
            disabledContainerColor = disabledContainerColor
        ),
        enabled = isButtonEnabled,
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp),
        onClick = { onButtonClick() }
    ) {
        Text(
            text = buttonText,
            style = textStyle,
        )
    }
}


@Composable
fun CloseIcon(
    modifier: Modifier = Modifier,
    onClosedIconClicked: (String) -> Unit,
    containerColor: Color =  Color.Transparent, //ColorsExtra.TransparentLight10,
    iconColor: Color = Color.White //ColorsExtra.SolidLight100,
) {
    Surface(
        modifier = modifier
            .size(48.dp)
            .clickable { onClosedIconClicked("X") }
            .padding(8.dp),
        color = containerColor,
        shape = CircleShape
    ) {
        Icon(
            imageVector = Icons.Default.Close,
            contentDescription = "close",
            tint = iconColor,
            modifier = Modifier
                .padding(6.dp)
        )
    }
}
@Composable
fun rememberKeyboardState(): State<Boolean> {
    val isImeVisible = WindowInsets.ime.getBottom(LocalDensity.current) > 0
    return rememberUpdatedState(isImeVisible)
}

@Composable
fun DefaultEmailInputField(
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit = {},
    onInputComplete: (String) -> Unit = {},
    emailState: EmailState = EmailState(),
    hint: String = stringResource(R.string.email_short),
    supportText: String = "",
    imeAction: ImeAction = ImeAction.Done,
) {
    DefaultInputField(
        textHint = hint,
        keyboardType = KeyboardType.Email,
        imeAction = imeAction,
        onValueChange = onValueChange,
        errorText = emailState.error?.let {
            if (it.internalMessage != -1) stringResource(id = it.internalMessage)
            else null   // We dont't support generic messages for email
        }
            ?: "",
        forceError = emailState.error != null,
        supportText = supportText,
        text = emailState.email,
        onInputComplete = onInputComplete,
        modifier = modifier,
    )
}

/**
 * Default input field. Stylized.
 * @param onInputComplete - will be called when user presses Done/Next action or when control looses focus
 * @param onValueChange - will be called only when value changes
 */
@Composable
fun DefaultInputField(
    modifier: Modifier = Modifier,
    text: String = "",
    textHint: String = "",
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Done,
    forceError: Boolean = false,
    errorText: String = "",
    supportText: String = "",
    onInputComplete: (String) -> Unit = {},
    onValueChange: (String) -> Unit = {},
    maxLines: Int = 1,
) {
    var editTextInput by remember { mutableStateOf(text) }
    val focusManager = LocalFocusManager.current
    var showPassword by remember { mutableStateOf(false) }
    var wasFocused by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = editTextInput,
        onValueChange = { filledText ->
            editTextInput = filledText.also {
                onValueChange(it)
            }
        },
        modifier = modifier
            .onFocusChanged {
                if (!it.isFocused && wasFocused) {
                    onInputComplete(editTextInput)
                }

                wasFocused = it.isFocused
            },
        textStyle = MaterialTheme.typography.bodyLarge,
        label = {
            Text(
                text = textHint,
            )
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType,
            imeAction = imeAction
        ),
        keyboardActions = KeyboardActions(
            onDone = {
//                onInputComplete(editTextInput)
                focusManager.clearFocus()
            },
            onNext = {
//                onInputComplete(editTextInput)
                focusManager.moveFocus(FocusDirection.Down)
            }
        ),
        singleLine = true,
        shape = RoundedCornerShape(4.dp),
        colors = OutlinedTextFieldDefaults.colors(
//            focusedTextColor = ColorsExtra.SolidLight100,
//            unfocusedTextColor = ColorsExtra.SolidLight100,
//            errorTextColor = ColorsExtra.SolidLight100,
//
//            focusedContainerColor = ColorsExtra.SolidLight40,
//            unfocusedContainerColor = ColorsExtra.SolidLight30,
//            errorContainerColor = ColorsExtra.SolidLight10,
//
//            focusedBorderColor = ColorsExtra.SolidLight80,
//            unfocusedBorderColor = ColorsExtra.SolidLight30,
//            errorBorderColor = ColorsExtra.SolidRed100,
//
//            focusedLabelColor = ColorsExtra.TransparentLight50,
//            unfocusedLabelColor = ColorsExtra.SolidLight50,
//            errorLabelColor = ColorsExtra.SolidRed100,
//
//            errorSupportingTextColor = ColorsExtra.SolidRed100,
//            focusedSupportingTextColor = ColorsExtra.SolidLight80,
//            unfocusedSupportingTextColor = ColorsExtra.SolidLight80,
        ),
        isError = errorText.isNotEmpty() or forceError,
        supportingText = {
            val extraText = errorText.ifEmpty { supportText }
            if (extraText.isNotEmpty()) {
                Text(text = extraText)
            }
        },
        visualTransformation = if (showPassword || keyboardType != KeyboardType.Password) {
            VisualTransformation.None
        } else {
            PasswordVisualTransformation()
        },
        trailingIcon = {
            if (keyboardType == KeyboardType.Password) {
                IconButton(onClick = { showPassword = !showPassword }) {
                    Icon(
                        painter = if (showPassword) {
                            painterResource(R.drawable.ic_eye_open)
                        } else {
                            painterResource(R.drawable.ic_eye_open)
                        },
                        contentDescription = "password visibility",
                        modifier = Modifier.size(24.dp),
                        tint = Color.Black  //ColorsExtra.SolidLight100
                    )
                }
            }
        },
        maxLines = maxLines,
    )
}


/**
 * Create default behavior for clickable texts.
 * @param clickableTexts - list of clickable texts & annotations for this texts
 */
@Composable
fun DefaultClickableText(
    text: String,
    clickableTexts: List<Pair<String, String>>,
    modifier: Modifier = Modifier,
    textStyle: SpanStyle = SpanStyle(
        color = Color.Gray, //ColorsExtra.SolidLight100,
        fontSize = 14.sp,
        fontWeight = FontWeight(600),
        fontFamily = FontFamily(Font(R.font.libre_franklin_black)),
        textDecoration = TextDecoration.Underline,
    ),
    clickableTextStyle: TextStyle = MaterialTheme.typography.bodyMedium.copy(
        color = Color.White.copy(alpha = 0.8f), //ColorsExtra.TransparentLight80,
        textAlign = TextAlign.Center
    ),
    onTextClick: (String) -> Unit,
) {
    val (annotatedString, tag) = StringUtils.multiLinkAnnotatedString(
        text, clickableTexts,
        style = textStyle
    )
    ClickableText(
        modifier = modifier,
        text = annotatedString,
        style = clickableTextStyle,
        onClick = { offset ->
            annotatedString.getStringAnnotations(
                tag = tag, start = offset, end = offset
            ).firstOrNull()?.let { annotation ->
                onTextClick(annotation.item)
            }
        }
    )
}