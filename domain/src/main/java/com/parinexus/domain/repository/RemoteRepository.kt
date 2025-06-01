package com.parinexus.domain.repository

import com.parinexus.domain.model.DomainResultApi
import com.parinexus.domain.state.Resource
import kotlinx.coroutines.flow.Flow

interface RemoteRepository {
    fun getArticles(category:String, pageNumber: String?) : Flow<Resource<DomainResultApi>>
}