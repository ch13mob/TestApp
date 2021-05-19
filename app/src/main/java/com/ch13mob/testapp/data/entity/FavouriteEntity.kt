package com.ch13mob.testapp.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favourite_table")
data class FavouriteEntity(
    @PrimaryKey
    @ColumnInfo(name = "post_id")
    val postId: Long
)