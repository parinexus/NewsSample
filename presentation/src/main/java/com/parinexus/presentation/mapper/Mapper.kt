package com.parinexus.presentation.mapper

import com.parinexus.domain.model.DomainArticle
import com.parinexus.domain.model.DomainResultApi
import com.parinexus.presentation.model.Article
import com.parinexus.presentation.model.ArticleList

fun DomainResultApi.toPresentation(): ArticleList {
    return ArticleList(
        status = this.status,
        totalResults = this.totalResults,
        nextPage = this.nextPage,
        results = this.results.map { it.toArticlePresentation() }
    )
}

fun DomainArticle.toArticlePresentation(): Article {
    return Article(
        title = this.title?:"",
        description = this.description,
        urlToImage = this.imageUrl,
        url = this.link?:"",
        content = this.content
    )
}

fun List<DomainArticle>.toPresentation(): List<Article> {
    return this.map { it.toArticlePresentation() }
}