package com.parinexus.data.mapper

import com.parinexus.data.model.DataResultApi
import com.parinexus.domain.model.DomainResultApi

fun DataResultApi.toDomain(): DomainResultApi = DomainResultApi(
    status = status,
    totalResults = totalResults,
    results = results.map { it.toDomain() },
    nextPage = nextPage
)