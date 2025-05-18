package com.parinexus.data.repository

import com.parinexus.data.local.ArticleDao
import com.parinexus.data.mapper.toData
import com.parinexus.data.mapper.toDomain
import com.parinexus.domain.model.DomainArticle
import com.parinexus.domain.repository.LocalRepository
import com.parinexus.domain.state.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class LocalRepositoryImpl @Inject constructor(
    private val articleDao: ArticleDao
) : LocalRepository {

    override suspend fun addFavoriteArticle(article: DomainArticle): Resource<String> {
        return try {
            articleDao.addFavoriteArticle(article.toData())
            Resource.Success("Article added to favorites successfully.")
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Failed("Something went wrong. Please try again.")
        }
    }

    override fun getAllFavoriteArticle(): Flow<Resource<List<DomainArticle>>> {
        return articleDao.getAllFavoriteArticle()
            .map<List<com.parinexus.data.model.DataArticle>, Resource<List<DomainArticle>>> { articles ->
                Resource.Success(articles.map { it.toDomain() })
            }
            .onStart { emit(Resource.Loading) }
            .catch { throwable ->
                throwable.printStackTrace()
                emit(Resource.Failed("Failed to load favorites. Please try again."))
            }
            .flowOn(Dispatchers.IO)
    }

    override suspend fun deleteFavoriteArticle(article: DomainArticle): Resource<String> {
        return try {
            articleDao.deleteFavoriteArticle(article.toData())
            Resource.Success("Article removed from favorites.")
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Failed("Unable to remove article. Please try again.")
        }
    }

    override suspend fun deleteAllFavoriteArticle(): Resource<String> {
        return try {
            articleDao.deleteAllFavoriteArticle()
            Resource.Success("All favorite articles deleted.")
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Failed("Could not delete favorites. Please try again.")
        }
    }

    override suspend fun findArticleById(id: String): Flow<Boolean> =
        articleDao.findArticleById(id)
            .flowOn(Dispatchers.IO)
}