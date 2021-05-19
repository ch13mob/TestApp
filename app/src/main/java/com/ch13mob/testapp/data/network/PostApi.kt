package com.ch13mob.testapp.data.network

import com.ch13mob.testapp.data.entity.CommentEntity
import com.ch13mob.testapp.data.entity.PostEntity
import retrofit2.http.GET
import retrofit2.http.Path

interface PostApi {
    @GET("/posts")
    suspend fun getPosts(): List<PostEntity>

    @GET("/posts/{post_id}/comments")
    suspend fun getCommentsByPostId(@Path("post_id") postId: Long): List<CommentEntity>
}