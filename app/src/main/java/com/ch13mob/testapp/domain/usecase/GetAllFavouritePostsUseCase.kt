package com.ch13mob.testapp.domain.usecase

import com.ch13mob.testapp.domain.error.Error
import com.ch13mob.testapp.domain.model.PostItem
import com.ch13mob.testapp.domain.repository.ContentRepository
import com.ch13mob.testapp.domain.utils.Result
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class GetAllFavouritePostsUseCase @Inject constructor(
    dispatcher: CoroutineDispatcher,
    private val contentRepository: ContentRepository
) : BaseUseCase(dispatcher) {

    suspend fun launch(): Result<List<PostItem>, Error> =
        wrapResult {
            contentRepository.getAllFavouritePosts()
        }
}