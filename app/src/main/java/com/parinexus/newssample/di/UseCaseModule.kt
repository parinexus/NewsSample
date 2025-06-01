package com.parinexus.newssample.di

import com.parinexus.domain.ArticleUseCase
import com.parinexus.domain.repository.LocalRepository
import com.parinexus.domain.repository.RemoteRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {

    @Provides
    @Singleton
    fun bindArticleUseCase(
        localRepository: LocalRepository,
        remoteRepository: RemoteRepository
    ): ArticleUseCase {
        return ArticleUseCase(localRepository, remoteRepository)
    }
}