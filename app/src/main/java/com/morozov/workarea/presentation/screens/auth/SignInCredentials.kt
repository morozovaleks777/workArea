package com.morozov.workarea.presentation.screens.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.morozov.workarea.R
import com.morozov.workarea.presentation.components.DefaultEmailInputField
import com.morozov.workarea.presentation.components.DefaultInputField


@Composable
fun SignInCredentials(
    onLoginClick: (String, String) -> Unit,
    onForgotPasswordClick: (String) -> Unit,
    onLoginWithLink: () -> Unit,
    logoImageBlack: Int,
    email: EmailState,
    isWideScreen: Boolean,
    onEmailComplete: (String) -> Unit,
    password: PasswordState,
    onPasswordComplete: (String) -> Unit,
    onExit: (String) -> Unit,
) {
    var emailInput by remember(email) {
        mutableStateOf(email)
    }
    var passwordInput by remember(password) {
        mutableStateOf(password)
    }
    val focusManager = LocalFocusManager.current
    Box(modifier = Modifier.fillMaxSize().padding(horizontal = 12.dp)) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            ClickableLink(
                linkText = stringResource(R.string.don_t_have_an_account),
                onLinkClicked = { },
                modifier = Modifier.padding(top = 40.dp, end = 20.dp)
            )
        }

        Box(modifier = Modifier.wrapContentSize()) {
            Image(
                painter = painterResource(id = logoImageBlack),
                contentDescription = "",
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .padding(top = 127.dp, start = 32.dp)
                    .size(149.dp)
                    .scale(1.3f)
                    .align(if (isWideScreen) Alignment.BottomCenter else Alignment.BottomStart)
            )
        }

        AuthForm(
            modifier = Modifier.fillMaxSize().padding(top = 193.dp),
            title = stringResource(R.string.sign_in_title),
            description = "",
            onExit = onExit,
            content = {
                DefaultEmailInputField(
                    imeAction = ImeAction.Next,
                    modifier = Modifier.fillMaxWidth(),
                    emailState = emailInput,
                    onValueChange = { emailInput = EmailState(it) },
                    onInputComplete = onEmailComplete,
                )

                DefaultInputField(
                    textHint = stringResource(R.string.password),
                    keyboardType = KeyboardType.Password,
                    text = passwordInput.password,
                    imeAction = ImeAction.Done,
                    modifier = Modifier.fillMaxWidth(),
                    errorText = passwordInput.errors.firstOrNull()
                        ?.let { if (it.internalMessage != -1) stringResource(id = it.internalMessage) else it.externalMessage }
                        ?: "",
                    onValueChange = { passwordInput = PasswordState(it) },
                    onInputComplete = onPasswordComplete,
                )

                ClickableLink(
                    linkText = stringResource(R.string.forgot_password),
                    onLinkClicked = { onForgotPasswordClick(emailInput.email) },
                    modifier = Modifier.padding(start = 22.dp, top = 12.dp)
                )
            },
            primaryButtonText = stringResource(R.string.log_in),
            primaryButtonClick = {
                focusManager.clearFocus()
                onLoginClick(emailInput.email, passwordInput.password)
            },
            primaryButtonEnabled = emailInput.isValid && passwordInput.isValid,
            secondaryButtonClick = {
                onLoginWithLink()
            },
            secondaryButtonText = stringResource(R.string.log_in_with_link),
            authHeaderStyle =  MaterialTheme.typography.bodySmall
                .copy(fontSize = 16.sp,
                    fontWeight = FontWeight.W400,
                    fontFamily = FontFamily( Font(R.font.libre_franklin_medium, FontWeight.W400))
                )
        )
    }
}