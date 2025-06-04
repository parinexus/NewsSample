package com.parinexus.data.mapper

import com.parinexus.data.model.DataArticle
import com.parinexus.domain.model.DomainArticle

fun DataArticle.toDomain(): DomainArticle = DomainArticle(
    id = id,
    articleId = articleId,
    title = title,
    link = link,
    description = description,
    content = content,
    pubDate = pubDate,
    pubDateTZ = pubDateTZ,
    imageUrl = imageUrl,
    sourceId = sourceId,
    sourceName = sourceName,
    sourceIcon = sourceIcon,
    language = language
)

fun DomainArticle.toData(): DataArticle = DataArticle(
    id = id,
    articleId = articleId,
    title = title,
    link = link,
    description = description,
    content = content,
    pubDate = pubDate,
    pubDateTZ = pubDateTZ,
    imageUrl = imageUrl,
    sourceId = sourceId,
    sourceName = sourceName,
    sourceIcon = sourceIcon,
    language = language
)