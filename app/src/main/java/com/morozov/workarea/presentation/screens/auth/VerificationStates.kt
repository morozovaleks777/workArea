package com.morozov.workarea.presentation.screens.auth

import androidx.annotation.StringRes
import androidx.core.content.ContextCompat.getString

import com.morozov.workarea.R

data class EmailState(val email: String = "", val error: EmailError? = null) {
    val isValid
        get() = email.isNotBlank() && error == null
}

sealed class EmailError(
    @StringRes val internalMessage: Int,
    val externalMessage: String = ""
)

object EmailErrorInvalid : EmailError(R.string.please_enter_a_valid_email_address)
object EmailErrorAlreadyRegistered : EmailError(R.string.this_email_is_already_registered_to_an_account)
object EmailIsEmpty : EmailError(R.string.oops_you_forgot_something)

class GenericEmailError(msg: String) : EmailError(-1, msg)

data class PasswordState(val password: String = "", val errors: List<PasswordError> = emptyList()) {
    val isValid
        get() = password.isNotBlank() && !errors.contains(PasswordErrorMinimum()) && !errors.contains(
            PasswordErrorThreeOfFour
        )
}

sealed class PasswordError(@StringRes val internalMessage: Int, val externalMessage: String = "") {
    companion object {
        val visualRequirements = listOf(
            PasswordErrorMinimum(),
            PasswordErrorThreeOfFour,
            PasswordErrorUpperCase,
            PasswordErrorLowerCase,
            PasswordErrorNumeric,
            PasswordErrorSpecialChar(),
        )
    }
}

object PasswordErrorThreeOfFour :
    PasswordError(R.string.contain_at_least_3_of_the_following_4_types_of_characters)

data class PasswordErrorMinimum(val required: Int = PASSWORD_MIN) :
    PasswordError(R.string.error_password_min_characters) {
    companion object {
        const val PASSWORD_MIN = 8
    }
}

object PasswordErrorUpperCase :
    PasswordError(R.string.upper_case)

object PasswordErrorLowerCase :
    PasswordError(R.string.lower_case)

data class PasswordErrorSpecialChar(
    val required: List<Char> = PASSWORD_CHAR
) : PasswordError(R.string.error_password_special_characters) {
    companion object {
        val PASSWORD_CHAR = listOf(
            '!',
            '@',
            '#',
            '$',
            '%',
            '^',
            '&',
            '*'
        )
    }
}

object PasswordErrorNumeric : PasswordError(R.string.error_password_numeric)

object PasswordEmpty : PasswordError(R.string.password_error_empty)

class GenericPasswordError(msg: String) : PasswordError(-1, msg)
