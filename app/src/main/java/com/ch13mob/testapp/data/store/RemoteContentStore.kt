package com.ch13mob.testapp.data.store

import com.ch13mob.testapp.data.entity.CommentEntity
import com.ch13mob.testapp.data.entity.PostEntity
import com.ch13mob.testapp.data.network.PostApi
import javax.inject.Inject

class RemoteContentStore @Inject constructor(
    private val postApi: PostApi
) {
    suspend fun getPosts(): List<PostEntity> = postApi.getPosts()

    suspend fun getCommentsByPostId(postId: Long): List<CommentEntity> =
        postApi.getCommentsByPostId(postId)
}