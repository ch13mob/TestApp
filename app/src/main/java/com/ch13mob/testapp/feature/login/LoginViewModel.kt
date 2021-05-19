package com.ch13mob.testapp.feature.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ch13mob.testapp.domain.error.Error
import com.ch13mob.testapp.domain.usecase.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : ViewModel() {

    sealed class State {
        object Loading : State()
        object LaunchMain : State()
        object BadEmail : State()
        object BadPassword : State()
        data class Fail(val error: Error) : State()
    }

    private var email: String = ""
    private var password: String = ""

    val state = MutableLiveData<State>()
    val buttonState = MutableLiveData(false)

    fun onEmailChanged(email: String) {
        if (email.isNotEmailValid()) {
            state.value = State.BadEmail
            buttonState.value = false
        } else {
            this.email = email

            updateButtonState()
        }
    }

    fun onPasswordChanged(password: String) {
        if (password.isNotPasswordValid()) {
            state.value = State.BadPassword
            buttonState.value = false
        } else {
            this.password = password

            updateButtonState()
        }
    }

    fun login(email: String, password: String) {
        state.value = State.Loading
        viewModelScope.launch {
            loginUseCase.launch(email, password).fold(
                onSuccess = { state.value = State.LaunchMain },
                onFailure = { state.value = State.Fail(it) }
            )
        }
    }

    private fun updateButtonState() {
        buttonState.value = email.isNotEmpty() && password.isNotEmpty()
    }

    private fun String.isNotEmailValid(): Boolean {
        val emailRegex = Regex("[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,8}")

        return isEmpty() || !emailRegex.matches(this)
    }

    private fun String.isNotPasswordValid(): Boolean = isEmpty() || length !in 9..14
}