package com.parinexus.data.repository

import android.content.Context
import androidx.datastore.preferences.core.edit
import com.parinexus.data.FAVORITE_CATEGORIES_KEY
import com.parinexus.data.dataStore
import com.parinexus.domain.repository.FavoriteCategoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.serialization.json.Json
import kotlinx.serialization.encodeToString
import javax.inject.Inject

class FavoriteCategoryRepositoryImpl @Inject constructor(
    private val context: Context
) : FavoriteCategoryRepository {

    override val favoriteCategories: Flow<List<String>> =
        context.dataStore.data.map { prefs ->
            prefs[FAVORITE_CATEGORIES_KEY]?.let { Json.decodeFromString(it) } ?: emptyList()
        }

    override suspend fun updateFavoriteCategory(category: String, isSelected: Boolean) {
        context.dataStore.edit { preferences ->
            val current = favoriteCategories.first()
            val updated = if (isSelected) current - category else current + category
            preferences[FAVORITE_CATEGORIES_KEY] = Json.encodeToString(updated)
        }
    }
}
