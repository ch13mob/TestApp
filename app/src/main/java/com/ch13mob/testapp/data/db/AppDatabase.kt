package com.ch13mob.testapp.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ch13mob.testapp.data.db.dao.CommentDao
import com.ch13mob.testapp.data.db.dao.FavouriteDao
import com.ch13mob.testapp.data.db.dao.PostDao
import com.ch13mob.testapp.data.entity.CommentEntity
import com.ch13mob.testapp.data.entity.FavouriteEntity
import com.ch13mob.testapp.data.entity.PostEntity

@Database(
    entities = [PostEntity::class, CommentEntity::class, FavouriteEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun postDao(): PostDao
    abstract fun commentDao(): CommentDao
    abstract fun favouriteDao(): FavouriteDao
}