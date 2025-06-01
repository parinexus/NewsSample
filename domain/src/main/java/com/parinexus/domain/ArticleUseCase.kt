package com.parinexus.domain

import com.parinexus.domain.model.DomainResultApi
import com.parinexus.domain.repository.LocalRepository
import com.parinexus.domain.repository.RemoteRepository
import com.parinexus.domain.state.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ArticleUseCase(
    private val localRepository: LocalRepository,
    private val remoteRepository: RemoteRepository
) {

    fun getArticles(category: String, nextPage: String?): Flow<Resource<DomainResultApi>> = flow {
        emit(Resource.Loading)

        var nextPageFromApi: String? = null
        var fetchFailed = false

        // Step 1: fetch from remote and cache locally
        remoteRepository.getArticles(category, nextPage).collect { result ->
            when (result) {
                is Resource.Success -> {
                    val articles = result.data.results
                    nextPageFromApi = result.data.nextPage
                    localRepository.deleteAllFavoriteArticle()
                    articles.forEach { localRepository.addFavoriteArticle(it) }
                }

                is Resource.Failed -> {
                    fetchFailed = true
                }

                is Resource.Loading -> Unit
            }
        }

        // Step 2: always collect local DB and wrap it in DomainResultApi
        if (!fetchFailed) {
            localRepository.getAllFavoriteArticle().collect { localResult ->
                when (localResult) {
                    is Resource.Success -> {
                        val apiFormatted = DomainResultApi(
                            results = localResult.data,
                            nextPage = nextPageFromApi
                        )
                        emit(Resource.Success(apiFormatted))
                    }

                    is Resource.Failed -> emit(Resource.Failed(localResult.message))
                    is Resource.Loading -> emit(Resource.Loading)
                }
            }
        }
    }
}
