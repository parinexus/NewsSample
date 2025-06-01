package com.parinexus.domain.model

data class DomainResultApi(
    val status: String? = null,
    val totalResults: Int? = null,
    val results: List<DomainArticle> = emptyList(),
    val nextPage: String? = null
)