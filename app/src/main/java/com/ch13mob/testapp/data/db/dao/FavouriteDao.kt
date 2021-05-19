package com.ch13mob.testapp.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.ch13mob.testapp.data.entity.FavouriteEntity
import com.ch13mob.testapp.data.entity.PostEntity

@Dao
interface FavouriteDao {
    @Insert(onConflict = REPLACE)
    fun saveFavouritePost(favouriteEntity: FavouriteEntity)

    @Delete
    fun deleteFavouritePost(favouriteEntity: FavouriteEntity)

    @Query("SELECT * FROM favourite_table")
    fun getFavouritePostIds(): List<Long>

    @Query("SELECT * FROM post_table INNER JOIN  favourite_table ON favourite_table.post_id = _id")
    fun getAllFavouritePosts(): List<PostEntity>
}