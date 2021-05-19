package com.ch13mob.testapp.domain.usecase

import com.ch13mob.testapp.domain.error.Error
import com.ch13mob.testapp.domain.repository.ContentRepository
import com.ch13mob.testapp.domain.utils.Result
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class DeleteFavouritePostUseCase @Inject constructor(
    dispatcher: CoroutineDispatcher,
    private val contentRepository: ContentRepository
) : BaseUseCase(dispatcher) {

    suspend fun launch(postId: Long): Result<Any, Error> =
        wrapResult {
            contentRepository.deleteFavouritePost(postId)
        }
}