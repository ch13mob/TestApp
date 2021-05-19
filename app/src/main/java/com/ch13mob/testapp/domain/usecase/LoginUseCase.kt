package com.ch13mob.testapp.domain.usecase

import kotlinx.coroutines.CoroutineDispatcher
import com.ch13mob.testapp.domain.error.Error
import com.ch13mob.testapp.domain.utils.Result
import com.ch13mob.testapp.domain.repository.UserRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    dispatcher: CoroutineDispatcher,
    private val userRepository: UserRepository
) : BaseUseCase(dispatcher) {

    suspend fun launch(email: String, password: String): Result<Any, Error> =
        wrapResult {
            userRepository.login(email, password)
        }
}