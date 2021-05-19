package com.ch13mob.testapp.di

import android.content.Context
import androidx.preference.PreferenceManager
import androidx.room.Room
import com.ch13mob.testapp.BuildConfig
import com.ch13mob.testapp.DATABASE_NAME
import com.ch13mob.testapp.data.db.AppDatabase
import com.ch13mob.testapp.data.network.createPostApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers

@Module
@InstallIn(SingletonComponent::class)
object DiModules {

    @Provides
    fun provideRoomInstance(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(
        context,
        AppDatabase::class.java,
        DATABASE_NAME
    ).fallbackToDestructiveMigration().build()

    @Provides
    fun providePostDao(db: AppDatabase) = db.postDao()

    @Provides
    fun provideCommentDao(db: AppDatabase) = db.commentDao()

    @Provides
    fun provideFavouriteDao(db: AppDatabase) = db.favouriteDao()

    @Provides
    fun provideSharedPreferences(
        @ApplicationContext context: Context
    ) = PreferenceManager.getDefaultSharedPreferences(context)!!

    @Provides
    fun provideCoroutineDispatcher() = Dispatchers.IO

    @Provides
    fun providePostApi() =
        createPostApi(
            debug = BuildConfig.DEBUG,
            baseUrl = BuildConfig.API_BASE_URL
        )
}