package com.ch13mob.testapp.domain.usecase

import com.ch13mob.testapp.domain.error.toError
import com.ch13mob.testapp.domain.utils.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

open class BaseUseCase(
    private val dispatcher: CoroutineDispatcher
) {

    /**
     * Wraps [block] execution into [Result] in [dispatcher]
     */
    protected suspend fun <T> wrapResult(block: suspend () -> T) = withContext(dispatcher) {
        try {
            Result.Success(block())
        } catch (e: Exception) {
            Result.Failure(e.toError)
        }
    }
}