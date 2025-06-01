package com.parinexus.newssample.di

import android.content.Context
import androidx.room.Room
import com.parinexus.data.local.ArticleDao
import com.parinexus.data.local.ArticleDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): ArticleDataBase {
        return Room.databaseBuilder(
            context,
            ArticleDataBase::class.java,
            "news_db"
        ).build()
    }

    @Provides
    fun provideArticleDao(database: ArticleDataBase): ArticleDao {
        return database.getArticleDao()
    }

}