package com.parinexus.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.parinexus.data.model.DataArticle

@Database(
    entities = [DataArticle::class],
    version = 1,
    exportSchema = false
)
abstract class ArticleDataBase : RoomDatabase() {
    abstract fun getArticleDao(): ArticleDao
}