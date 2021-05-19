package com.ch13mob.testapp.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "post_table")
data class PostEntity(
    @PrimaryKey
    @ColumnInfo(name = "_id")
    @SerializedName("id")
    val id: Long,
    @ColumnInfo(name = "title")
    @SerializedName("title")
    val title: String,
    @ColumnInfo(name = "body")
    @SerializedName("body")
    val body: String,
    @ColumnInfo(name = "user_id")
    @SerializedName("userId")
    val userId: Long
)