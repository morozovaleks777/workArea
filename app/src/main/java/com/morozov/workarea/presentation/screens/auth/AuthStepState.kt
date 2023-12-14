package com.morozov.workarea.presentation.screens.auth

import androidx.annotation.StringRes


enum class AuthMainStates {
    Initial, Payment
}

sealed class AuthStepState(open val previous: AuthStepState? = null) {
    val hasPrevious
        get() = previous != null
}




object AuthNone : AuthStepState()

/**
 * Just skips the whole Auth-process and closes current screen
 */
object AuthSkip : AuthStepState()

/**
 * Initial state of the auth screen. Basically, gives you 2 options: Sign-Up or Sign-In
 */
data class AuthInitialStep(
    val startSignUp: () -> Unit,
    val startSignIn: () -> Unit,
) : AuthStepState()

/**
 * Year of birth input and validation
 */
data class AgeGateStep(
    override val previous: AuthStepState?,
    val confirmAge: (Int) -> Unit,
    val contactSupport: () -> Unit,
) : AuthStepState(previous)

/**
 * 2nd-A step of Sign-In. Fill-in credentials or use other options to login
 */
data class SignInCredentialsStep(
    override val previous: AuthStepState?,
    val email: EmailState = EmailState(),
    val password: PasswordState = PasswordState(),
    private val verifyEmail: SignInCredentialsStep.(String) -> Unit,
    private val verifyPassword: SignInCredentialsStep.(String) -> Unit,
    private val confirmCredentials: SignInCredentialsStep.(String, String) -> Unit,
    private val forgotPassword: SignInCredentialsStep.(String) -> Unit,
    val signUp: () -> Unit,
) : AuthStepState() {
    fun verifyEmail(email: String) {
        verifyEmail.invoke(this, email)
    }

    fun verifyPassword(password: String) {
        verifyPassword.invoke(this, password)
    }

    fun confirmCredentials(email: String, password: String) {
        confirmCredentials.invoke(this, email, password)
    }

    fun forgotPassword(email: String) {
        forgotPassword.invoke(this, email)
    }
}

/**
 * Usually called from Sign-In. Provide email and start recovery procedure
 */
data class ForgotPasswordState(
    override val previous: AuthStepState?,
    val email: EmailState = EmailState(),
    private val verifyEmail: ForgotPasswordState.(String) -> Unit,
    private val sendMail: ForgotPasswordState.(String) -> Unit,
) : AuthStepState() {
    fun verifyEmail(email: String) {
        verifyEmail.invoke(this, email)
    }

    fun sendMail(newEmail: String) {
        sendMail.invoke(this, newEmail)
    }
}

/**
 * Your mail was sent. Check Email
 */
data class MailSent(
    override val previous: AuthStepState?,
    val email: String,
    val tryAgain: () -> Unit,
    val resend: () -> Unit,
    val openInbox: () -> Unit,
) : AuthStepState(previous)

/**
 * 1st step of Sign-Up. Select between Kid and Parent options
 */
data class SignUpStep(
    override val previous: AuthStepState?,
    val calledFrom: CalledFrom,
    val continueSignUP: () -> Unit,
) : AuthStepState(previous) {
    enum class CalledFrom {
        SignUp, Profile
    }
}

/**
 * 3rd step of Sign-Up. Fill-in credentials and validate them
 */
data class SignUpCredentialsStep(
    override val previous: AuthStepState?,
    val email: EmailState = EmailState(),
    val password: PasswordState = PasswordState(),
    private val confirmCredentials: SignUpCredentialsStep.(String, String) -> Unit,
    private val verifyEmail: SignUpCredentialsStep.(String) -> Unit,
    private val verifyPassword: SignUpCredentialsStep.(String) -> Unit,
    val signIn: () -> Unit,
    val viewTerms: () -> Unit,
    val viewNotice: () -> Unit,
    val viewChildrenNotice: () -> Unit,
) : AuthStepState() {
    fun verifyEmail(email: String) {
        verifyEmail.invoke(this, email)
    }

    fun verifyPassword(password: String) {
        verifyPassword.invoke(this, password)
    }

    fun confirmCredentials(email: String, password: String) {
        confirmCredentials.invoke(this, email, password)
    }
}

/**
 * Restrict any further actions with authentication. Used when user is an underage
 */
class AgeGateRestricktedStep(
    override val previous: AuthStepState?,
    val tryAgain: () -> Unit,
) : AuthStepState()

/**
 * Restrict any further actions with authentication. For 15 minutes or so
 */
data class AgeLockedStep(val lockedMinutes: Int = LOCKDOWN_TIME_MINUTES) : AuthStepState(null) {
    companion object {
        const val LOCKDOWN_TIME_MINUTES = 15
    }
}

/**
 * Loading stage. Be ready to either go back if something fails or go to #DoneStep
 */
class LoadingStep(override val previous: AuthStepState?) : AuthStepState()

/**
 * Everything is awesome - briefly show user info and go back to the app content
 */
data class DoneStep(
    @StringRes val title: Int,
    @StringRes val description: Int,
    val avatar: String? = null,
) : AuthStepState(null)

data class PurchaseStep(
    val onDecline: () -> Unit,
    val onPurchase: (String) -> Unit,
    val onRestore: (String) -> Unit,
  //  val purchasePromo: SubscriptionPromo,
) : AuthStepState(AuthSkip)

/**
 * Plan purchase is confirmed - show user AdventurePass info and go back to the main content
 */
data class PurchaseDoneStep(
   // val billing: BillingInfo,
    val onStartWatching: () -> Unit,
) : AuthStepState(AuthSkip)

/**
 * Opens bottom sheet with legal content
 */
data class LegalContent(
    override val previous: AuthStepState?,
  //  val legal: LegalItem,
    val onDoneClick: () -> Unit,
) : AuthStepState()