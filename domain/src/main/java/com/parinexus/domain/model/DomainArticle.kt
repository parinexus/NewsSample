package com.parinexus.domain.model

data class DomainArticle(
    val id: Int? = null,
    val articleId: String,
    val title: String? = null,
    val link: String? = null,
    val description: String? = null,
    val content: String? = null,
    val pubDate: String? = null,
    val pubDateTZ: String? = null,
    val imageUrl: String? = null,
    val sourceId: String? = null,
    val sourceName: String? = null,
    val sourceIcon: String? = null,
    val language: String? = null
)