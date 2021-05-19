package com.ch13mob.testapp.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.ch13mob.testapp.data.entity.CommentEntity

@Dao
interface CommentDao {
    @Query("SELECT * FROM comment_table WHERE post_id=:postId")
    suspend fun getCommentsByPostId(postId: Long): List<CommentEntity>

    @Insert(onConflict = REPLACE)
    fun saveComments(posts: List<CommentEntity>)
}