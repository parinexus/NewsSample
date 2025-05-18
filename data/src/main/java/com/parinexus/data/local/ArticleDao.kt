package com.parinexus.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.parinexus.data.model.DataArticle
import kotlinx.coroutines.flow.Flow

@Dao
interface ArticleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavoriteArticle(article: DataArticle)

    @Query("SELECT * FROM articles ORDER BY pubDate ASC")
    fun getAllFavoriteArticle() : Flow<List<DataArticle>>

    @Delete
    suspend fun deleteFavoriteArticle(article: DataArticle)

    @Query("DELETE FROM articles")
    suspend fun deleteAllFavoriteArticle()

    @Query("SELECT EXISTS (SELECT 1 FROM articles WHERE articleId=:id)")
    fun findArticleById(id: String) : Flow<Boolean>

}