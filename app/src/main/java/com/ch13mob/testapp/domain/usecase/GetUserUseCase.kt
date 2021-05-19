package com.ch13mob.testapp.domain.usecase

import com.ch13mob.testapp.domain.error.Error
import com.ch13mob.testapp.domain.repository.UserRepository
import com.ch13mob.testapp.domain.utils.Result
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class GetUserUseCase @Inject constructor(
    dispatcher: CoroutineDispatcher,
    private val userRepository: UserRepository
) : BaseUseCase(dispatcher) {

    suspend fun launch(): Result<Boolean, Error> =
        wrapResult {
            userRepository.isUserLoggedIn()
        }
}