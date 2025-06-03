package com.parinexus.newssample.di

import android.content.Context
import com.parinexus.data.local.ArticleDao
import com.parinexus.data.remote.ApiService
import com.parinexus.data.repository.FavoriteCategoryRepositoryImpl
import com.parinexus.data.repository.LocalRepositoryImpl
import com.parinexus.data.repository.RemoteRepositoryImpl
import com.parinexus.domain.repository.FavoriteCategoryRepository
import com.parinexus.domain.repository.LocalRepository
import com.parinexus.domain.repository.RemoteRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    @Singleton
    fun provideLocalRepository(dao: ArticleDao) : LocalRepository{
        return LocalRepositoryImpl(dao)
    }

    @Provides
    @Singleton
    fun provideRemoteRepository(apiService: ApiService) : RemoteRepository{
        return RemoteRepositoryImpl(apiService)
    }

    @Provides
    @Singleton
    fun provideFavoriteCategoryRepository(
        @ApplicationContext context: Context
    ): FavoriteCategoryRepository {
        return FavoriteCategoryRepositoryImpl(context)
    }
}