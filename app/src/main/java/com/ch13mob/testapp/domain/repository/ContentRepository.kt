package com.ch13mob.testapp.domain.repository

import com.ch13mob.testapp.data.store.LocalContentStore
import com.ch13mob.testapp.data.store.RemoteContentStore
import com.ch13mob.testapp.domain.model.Comment
import com.ch13mob.testapp.domain.model.PostItem
import timber.log.Timber
import javax.inject.Inject

class ContentRepository @Inject constructor(
    private val localContentStore: LocalContentStore,
    private val remoteContentStore: RemoteContentStore
) {

    suspend fun getPosts(): List<PostItem> {
        try {
            localContentStore.savePosts(
                remoteContentStore.getPosts()
            )
        } catch (exception: Exception) {
            Timber.e(exception, "Failed to fetch posts from network")
        }

        val favouritePostIds = localContentStore.getFavouritePostIds()

        return localContentStore.getPosts().map {
            PostItem(
                id = it.id,
                title = it.title,
                body = it.body,
                userId = it.userId,
                isFavourite = favouritePostIds.contains(it.id)
            )
        }
    }

    suspend fun getCommentsByPostId(postId: Long): List<Comment> {
        try {
            localContentStore.saveComments(
                remoteContentStore.getCommentsByPostId(postId)
            )
        } catch (exception: Exception) {
            Timber.e(exception, "Failed to fetch comments from network")
        }

        return localContentStore.getCommentsByPostId(postId)
    }

    fun saveFavouritePost(postId: Long) {
        localContentStore.saveFavouritePost(postId)
    }

    fun deleteFavouritePost(postId: Long) {
        localContentStore.deleteFavouritePost(postId)
    }

    fun getAllFavouritePosts(): List<PostItem> {
        return localContentStore.getAllFavouritePosts().map {
            PostItem(
                id = it.id,
                title = it.title,
                body = it.body,
                userId = it.userId,
                isFavourite = true
            )
        }
    }
}