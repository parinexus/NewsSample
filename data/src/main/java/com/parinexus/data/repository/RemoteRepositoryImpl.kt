package com.parinexus.data.repository

import com.parinexus.data.mapper.toDomain
import com.parinexus.data.model.DataResultApi
import com.parinexus.data.remote.ApiService
import com.parinexus.domain.model.DomainResultApi
import com.parinexus.domain.repository.RemoteRepository
import com.parinexus.domain.state.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okio.IOException
import retrofit2.HttpException
import retrofit2.Response
import javax.inject.Inject

class RemoteRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : RemoteRepository {

    override fun getArticles(
        category: String,
        pageNumber: String?
    ): Flow<Resource<DomainResultApi>> = flow {
        emit(Resource.Loading)

        try {
            val response: Response<DataResultApi> = if (pageNumber == null) {
                apiService.getArticles(category = category)
            } else {
                apiService.getArticles(category = category, pageNumber = pageNumber)
            }

            if (response.isSuccessful) {
                val data = response.body()
                if (data != null) {
                    emit(Resource.Success(data.toDomain()))
                } else {
                    emit(Resource.Failed("Unexpected error: No data received."))
                }
            } else {
                emit(Resource.Failed("Server error: ${response.message()}"))
            }

        } catch (e: Exception) {
            val errorMessage = when (e) {
                is IOException -> "Network error: Please check your internet connection."
                is HttpException -> "HTTP ${e.code()}: ${e.message() ?: "Unexpected server error."}"
                else -> e.message ?: "Unknown error occurred."
            }
            emit(Resource.Failed(errorMessage))
        }
    }.flowOn(Dispatchers.IO)
}