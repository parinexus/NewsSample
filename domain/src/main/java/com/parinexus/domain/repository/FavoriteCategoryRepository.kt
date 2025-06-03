package com.parinexus.domain.repository

import kotlinx.coroutines.flow.Flow

interface FavoriteCategoryRepository {
    val favoriteCategories: Flow<List<String>>
    suspend fun updateFavoriteCategory(category: String, isSelected: Boolean)
}
