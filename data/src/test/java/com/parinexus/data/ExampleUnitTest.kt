package com.parinexus.data

import com.parinexus.data.mapper.toData
import com.parinexus.data.mapper.toDomain
import com.parinexus.data.model.DataArticle
import org.junit.Assert.assertEquals
import org.junit.Test

class ExampleUnitTest {
    @Test
    fun dataArticle_mapsToDomainAndBack_preservesId() {
        val original = DataArticle(id = 1, articleId = "a1")

        val mappedBack = original.toDomain().toData()

        assertEquals("ID should be preserved after mapping", original.id, mappedBack.id)
    }
}
