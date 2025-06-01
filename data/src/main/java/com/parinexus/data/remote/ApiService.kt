package com.parinexus.data.remote

import com.parinexus.data.BuildConfig
import com.parinexus.data.constants.Constants
import com.parinexus.data.model.DataResultApi
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("api/1/latest")
    suspend fun getArticles(
        @Query("country") country : String = BuildConfig.COUNTRY,
        @Query("language") language: String = BuildConfig.COUNTRY,
        @Query("category") category: String,
        @Query("page") pageNumber: String? = null,
        @Query("apikey") apiKey:String = BuildConfig.API_KEY
    ) : Response<DataResultApi>

}
