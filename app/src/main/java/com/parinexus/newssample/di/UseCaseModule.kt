package com.parinexus.newssample.di

import android.content.Context
import com.parinexus.data.repository.FavoriteCategoryRepositoryImpl
import com.parinexus.domain.repository.FavoriteCategoryRepository
import com.parinexus.domain.usecase.ArticleUseCase
import com.parinexus.domain.repository.LocalRepository
import com.parinexus.domain.repository.RemoteRepository
import com.parinexus.domain.usecase.FavoriteCategoryUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {

    @Provides
    @Singleton
    fun provideArticleUseCase(
        localRepository: LocalRepository,
        remoteRepository: RemoteRepository
    ): ArticleUseCase {
        return ArticleUseCase(localRepository, remoteRepository)
    }

    @Provides
    @Singleton
    fun provideFavoriteCategoryUseCase(
        repository: FavoriteCategoryRepository
    ): FavoriteCategoryUseCase {
        return FavoriteCategoryUseCase(repository)
    }
}