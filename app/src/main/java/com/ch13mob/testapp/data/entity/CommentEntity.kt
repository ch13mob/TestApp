package com.ch13mob.testapp.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "comment_table")
data class CommentEntity(
    @PrimaryKey
    @ColumnInfo(name = "_id")
    @SerializedName("id")
    val id: Long,
    @ColumnInfo(name = "post_id")
    @SerializedName("postId")
    val postId: Long,
    @ColumnInfo(name = "name")
    @SerializedName("name")
    val name: String,
    @ColumnInfo(name = "email")
    @SerializedName("email")
    val email: String,
    @ColumnInfo(name = "body")
    @SerializedName("body")
    val body: String
)