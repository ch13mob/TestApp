package com.ch13mob.testapp.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.ch13mob.testapp.data.entity.PostEntity

@Dao
interface PostDao {
    @Query("SELECT * FROM post_table")
    suspend fun getAllPosts(): List<PostEntity>

    @Insert(onConflict = REPLACE)
    fun savePosts(posts: List<PostEntity>)
}