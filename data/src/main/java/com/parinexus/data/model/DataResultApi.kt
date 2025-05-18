package com.parinexus.data.model

import com.google.gson.annotations.SerializedName

data class DataResultApi(
    @SerializedName("status") var status: String? = null,
    @SerializedName("totalResults") var totalResults: Int? = null,
    @SerializedName("results") var results: ArrayList<DataArticle> = arrayListOf(),
    @SerializedName("nextPage") var nextPage: String? = null
)