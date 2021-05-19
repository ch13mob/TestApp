package com.ch13mob.testapp.data.store

import com.ch13mob.testapp.data.db.dao.CommentDao
import com.ch13mob.testapp.data.db.dao.FavouriteDao
import com.ch13mob.testapp.data.db.dao.PostDao
import com.ch13mob.testapp.data.entity.CommentEntity
import com.ch13mob.testapp.data.entity.PostEntity
import com.ch13mob.testapp.data.mapper.CommentMapper
import com.ch13mob.testapp.data.mapper.FavouriteMapper
import com.ch13mob.testapp.data.mapper.PostMapper
import com.ch13mob.testapp.domain.model.Comment
import com.ch13mob.testapp.domain.model.Post
import javax.inject.Inject

class LocalContentStore @Inject constructor(
    private val postDao: PostDao,
    private val commentDao: CommentDao,
    private val favouriteDao: FavouriteDao
) {
    fun savePosts(posts: List<PostEntity>) {
        postDao.savePosts(posts)
    }

    suspend fun getPosts(): List<Post> {
        return postDao.getAllPosts().map { PostMapper.toDomain(it) }
    }

    fun saveComments(comments: List<CommentEntity>) {
        commentDao.saveComments(comments)
    }

    suspend fun getCommentsByPostId(postId: Long): List<Comment> {
        return commentDao.getCommentsByPostId(postId).map { CommentMapper.toDomain(it) }
    }

    fun saveFavouritePost(postId: Long) {
        favouriteDao.saveFavouritePost(FavouriteMapper.toEntity(postId))
    }

    fun deleteFavouritePost(postId: Long) {
        favouriteDao.deleteFavouritePost(FavouriteMapper.toEntity(postId))
    }

    fun getFavouritePostIds(): List<Long> {
        return favouriteDao.getFavouritePostIds()
    }

    fun getAllFavouritePosts(): List<Post> {
        return favouriteDao.getAllFavouritePosts().map { PostMapper.toDomain(it) }
    }
}