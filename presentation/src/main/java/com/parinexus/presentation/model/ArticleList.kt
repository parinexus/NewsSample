package com.parinexus.presentation.model


data class ArticleList(
    val status: String? = null,
    val totalResults: Int? = null,
    val results: List<Article> = emptyList(),
    val nextPage: String? = null
)