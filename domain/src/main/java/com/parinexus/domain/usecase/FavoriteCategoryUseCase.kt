package com.parinexus.domain.usecase

import com.parinexus.domain.repository.FavoriteCategoryRepository
import kotlinx.coroutines.flow.Flow

class FavoriteCategoryUseCase(
    private val repository: FavoriteCategoryRepository
) {
    val favorites: Flow<List<String>> = repository.favoriteCategories

    suspend fun updateFavorite(category: String, isSelected: Boolean) {
        repository.updateFavoriteCategory(category, isSelected)
    }
}