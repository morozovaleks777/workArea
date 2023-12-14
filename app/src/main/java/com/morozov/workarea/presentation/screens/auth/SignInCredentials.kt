package com.morozov.workarea.presentation.screens.auth

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import com.morozov.workarea.R
import com.morozov.workarea.presentation.components.DefaultEmailInputField
import com.morozov.workarea.presentation.components.DefaultInputField

@Preview
@Composable
fun PreviewSignIn() {
    SignInCredentials(
        onLoginClick = { _, _ -> },
        onForgotPasswordClick = {},
        email = EmailState(),
        onEmailComplete = {},
        password = PasswordState(),
        onPasswordComplete = {},
        onExit = {},
    )
}

@Composable
fun SignInCredentials(
    onLoginClick: (String, String) -> Unit,
    onForgotPasswordClick: (String) -> Unit,
    email: EmailState,
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

    AuthForm(
        modifier = Modifier.fillMaxSize(),
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
                modifier = Modifier.padding(vertical = dimensionResource(id = R.dimen.auth_spacing_vertical))
            )
        },
        primaryButtonText = stringResource(R.string.log_in),
        primaryButtonClick = {
            focusManager.clearFocus()
            onLoginClick(emailInput.email, passwordInput.password)
        },
        primaryButtonEnabled = emailInput.isValid && passwordInput.isValid,
    )
}
