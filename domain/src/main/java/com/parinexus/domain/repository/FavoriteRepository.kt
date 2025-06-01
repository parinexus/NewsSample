package com.parinexus.domain.repository

import kotlinx.coroutines.flow.Flow

/**
 * A repository that persists “favorite article IDs” per category.
 */
interface FavoriteRepository {
    /**
     * Save the given [articleId] as a favorite under [category].
     * If it’s already present, no change is made.
     */
    suspend fun saveFavorite(category: String, articleId: String)

    /**
     * Remove [articleId] from favorites under [category]. If not present, no‐op.
     */
    suspend fun removeFavorite(category: String, articleId: String)

    /**
     * Returns a Flow of the current set of favorite IDs for [category].
     * Emits updates any time that category’s favorites change.
     */
    fun getFavorites(category: String): Flow<Set<String>>
}
