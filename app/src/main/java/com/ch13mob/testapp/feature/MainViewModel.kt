package com.ch13mob.testapp.feature

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ch13mob.testapp.domain.error.Error
import com.ch13mob.testapp.domain.usecase.GetUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getUserUseCase: GetUserUseCase,
) : ViewModel() {

    sealed class State {
        data class IsUserLoggedIn(val isUserLoggedIn: Boolean) : State()
        data class Fail(val error: Error) : State()
    }

    val state = MutableLiveData<State>()

    init {
        isUserLoggedIn()
    }

    private fun isUserLoggedIn() {
        viewModelScope.launch {
            getUserUseCase.launch().fold(
                onSuccess = {
                    state.value = State.IsUserLoggedIn(it)
                },
                onFailure = { state.value = State.Fail(it) }
            )
        }
    }
}