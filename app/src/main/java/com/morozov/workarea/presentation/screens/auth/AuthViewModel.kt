package com.morozov.workarea.presentation.screens.auth

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.morozov.workarea.presentation.navigation.NavigationArguments
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.regex.Pattern
import javax.inject.Inject

@HiltViewModel
class AuthViewModel@Inject constructor(
    savedStateHandle: SavedStateHandle,
): ViewModel() {

    private val _error = MutableStateFlow<Throwable?>(null)
    private val _stepState = MutableStateFlow<AuthStepState>(AuthNone)
    private val _action = MutableStateFlow<AuthUiState.AuthAction>(AuthUiState.None)
    private val _direction = MutableStateFlow<AuthUiState.Direction>(AuthUiState.Next())


    val uiState = combine(
        _stepState,
        _action,
        _direction,
        _error
    ) { state, action, direction, error ->
        AuthUiState(
            step = state,
            action = action,
            direction = direction,
            error = error,
        )
    }
        .map { it }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = AuthUiState(step = AuthNone)
        )

    private fun prepareInitialStep(): AuthInitialStep = AuthInitialStep(
        startSignUp = {
            _stepState.next = prepareSignUpStep1()
        }, startSignIn = {
            _stepState.next = prepareSignInStep()
        }
    )
    val initialStep: AuthMainStates =
        savedStateHandle[NavigationArguments.ARG_AUTH_STATE] ?: AuthMainStates.Initial
    init {
        _stepState.value = when (initialStep) {
            AuthMainStates.Initial -> prepareInitialStep()
            AuthMainStates.Payment -> AuthNone //preparePurchaseStep()
        }
    }

    private fun prepareSignInStep() =
        SignInCredentialsStep(previous = _stepState.value,
            verifyEmail = { submittedEmail: String ->
                _stepState.update = this.copy(email = validateEmail(submittedEmail))
            },
            verifyPassword = { submittedPassword: String ->
                _stepState.update = this.copy(password = validatePassword(submittedPassword, true))
            },
            confirmCredentials = { submittedEmail: String, submittedPassword: String ->
                val (newEmailState, newPasswordState) = validateEmail(submittedEmail) to
                        validatePassword(submittedPassword, true)
                _stepState.update = this.copy(email = newEmailState, password = newPasswordState)

                if (newEmailState.isValid && newPasswordState.isValid) {
                    // Next
                  loginWCredentials(newEmailState.email, submittedPassword)
                }
            },
            forgotPassword = { submittedEmail: String ->
                // Save current email
                _stepState.update = this.copy(email = EmailState(submittedEmail))
                _stepState.next =  AuthNone    //prepareForgotPassword(this)
            },
            signUp = {
                _stepState.next = prepareSignUpStep1()
            }
        )

    private fun prepareSignUpStep1() =
        SignUpStep(previous = _stepState.value,
            calledFrom = SignUpStep.CalledFrom.SignUp,
            continueSignUP = {
                _stepState.next   //= prepareSignUpStep2()
            },
        )


    /**
     * Update current state of screen. If the screen is in different state then current - update rejected
     */
    private var MutableStateFlow<AuthStepState>.update: AuthStepState
        get() = throw IllegalAccessException("Unable to access future state")
        set(value) {
            val currentState = _stepState.value
            if (currentState::class.java == value::class.java) {
                _stepState.value = value
            } else {
                Timber.w("You trying to UPDATE current state ${currentState::class.java.name} with ${value::class.java.name}. Are you sure you're in the correct state?")
            }
        }

    /**
     * Move to next state
     */
    private var MutableStateFlow<AuthStepState>.next: AuthStepState
        get() = throw IllegalAccessException("Unable to access future state")
        set(value) {
            _stepState.value = value
            _direction.value = AuthUiState.Next()
        }

    private fun loginWCredentials(email: String, password: String) {
        _stepState.next = LoadingStep(_stepState.value)

        viewModelScope.launch(Dispatchers.IO) {
delay(1500)
            _stepState.next = DoneStep(0,0,null)
            delay(2000)

            done()
        }
    }
    fun reset() {

        _stepState.next = prepareInitialStep()

    }
    private fun done() {
        _stepState.next = AuthSkip
    }

    private fun validateEmail(email: String): EmailState {
        if (email.isEmpty()) {
            return EmailState(email = email, error = EmailIsEmpty)
        }
        if (!email.isValidEmail()) {
            return EmailState(email = email, error = EmailErrorInvalid)
        }
        return EmailState(email)
    }

    fun String.isValidEmail(): Boolean {
        val emailAddressPattern = Pattern.compile(
            "[a-zA-Z0-9\\+\\.\\_\\%\\-]{1,256}" +
                    "\\@" +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                    "(" +
                    "\\." +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                    ")+"
        )
        return emailAddressPattern.matcher(this).matches()
    }

    private fun validatePassword(password: String, isSimple: Boolean = false): PasswordState {
        val errorList = mutableListOf<PasswordError>()
        val conditionalErrorList = mutableListOf<PasswordError>()

        if (password.isBlank() && isSimple) {
            errorList.add(PasswordEmpty)
        }

        if (!isSimple) {
            val requireLength = PasswordErrorMinimum.PASSWORD_MIN
            val specialCharacters = PasswordErrorSpecialChar.PASSWORD_CHAR

            if (password.length < requireLength) {
                errorList.add(PasswordErrorMinimum(requireLength))
            }

            if (!(password.any(Char::isUpperCase))) {
                errorList.add(PasswordErrorUpperCase)
                conditionalErrorList.add(PasswordErrorUpperCase)
            }

            if (!(password.any(Char::isLowerCase))) {
                errorList.add(PasswordErrorLowerCase)
                conditionalErrorList.add(PasswordErrorLowerCase)
            }

            if (!password.any(Char::isDigit)) {
                errorList.add(PasswordErrorNumeric)
                conditionalErrorList.add(PasswordErrorNumeric)
            }

            if (!password.any(specialCharacters::contains)) {
                errorList.add(PasswordErrorSpecialChar())
                conditionalErrorList.add(PasswordErrorSpecialChar())
            }

            if (conditionalErrorList.size > 1) {
                errorList.add(PasswordErrorThreeOfFour)
            }
        }

        return PasswordState(password = password, errors = errorList)
    }

    private fun rollbackState() {
        val currentState = _stepState.value
        if (currentState.hasPrevious) {
            val previous = currentState.previous!!

            if (previous is LoadingStep) {
                rollbackState()
                return
            }

            _stepState.value = updateErrors(previous)
            _direction.value = AuthUiState.Previous()
        }
    }

    private fun updateErrors(stepState: AuthStepState): AuthStepState {
        val errMsg = _error.value?.message.orEmpty()
        return if (errMsg.isNotEmpty()) {
            val emailError: EmailError = GenericEmailError(errMsg)
            val passWordErrors: List<PasswordError> = listOf(GenericPasswordError(errMsg))
            return when (stepState) {
                is ForgotPasswordState -> stepState.copy(
                    email = stepState.email.copy(
                        error = emailError,
                    )
                )

                is SignInCredentialsStep -> stepState.copy(
                    email = stepState.email.copy(
                        error = emailError,
                    ),
                    password = stepState.password.copy(
                        errors = passWordErrors,
                    ),
                )

                is SignUpCredentialsStep -> stepState.copy(
                    email = stepState.email.copy(
                        error = emailError,
                    ),
                    password = stepState.password.copy(
                        errors = passWordErrors,
                    ),
                )

                else -> stepState
            }.also { handleError() }
        } else {
            stepState
        }
    }

    fun handleError() {
        _error.value = null
    }


    data class AuthUiState<T : AuthStepState>(
        val step: T,
        val action: AuthAction = AuthUiState.None,
        val direction: Direction = Next(),
        val error: Throwable? = null,
    ) {
        sealed class Direction
        data class Previous(val time: Long = System.currentTimeMillis()) : Direction()
        data class Next(val time: Long = System.currentTimeMillis()) : Direction()

        sealed class AuthAction
        object None : AuthAction()
        object OpenEmail : AuthAction()
        object ContactSupport : AuthAction()
        object RegistrationComplete : AuthAction()
        object OpenAccountScreen : AuthAction()
        object StartWatching : AuthAction()

        data class ShowAuthSuccessExtra(
            val form: ExtrasForm,
        ) : AuthAction() {
            data class ExtrasForm(
                val title: String,
                val message: String,
                val positiveAction: String,
                val negativeAction: String,
                val positiveClick: () -> Unit,
                val negativeClick: () -> Unit,
            )
        }
    }
}