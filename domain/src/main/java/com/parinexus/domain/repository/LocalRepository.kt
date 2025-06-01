package com.parinexus.domain.repository

import com.parinexus.domain.model.DomainArticle
import com.parinexus.domain.state.Resource
import kotlinx.coroutines.flow.Flow

interface LocalRepository {

    suspend fun addFavoriteArticle(article: DomainArticle) : Resource<String>

    fun getAllFavoriteArticle() : Flow<Resource<List<DomainArticle>>>

    suspend fun deleteFavoriteArticle(article: DomainArticle): Resource<String>

    suspend fun deleteAllFavoriteArticle(): Resource<String>

    suspend fun findArticleById(id :String) : Flow<Boolean>

}