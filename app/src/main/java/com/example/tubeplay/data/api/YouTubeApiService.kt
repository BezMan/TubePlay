package com.example.tubeplay.data.api

import com.example.tubeplay.data.model.SearchResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface YouTubeApiService {
    @GET("youtube/v3/search")
    fun searchVideos(
        @Query("part") part: String,
        @Query("key") apiKey: String,
        @Query("q") query: String
    ): Call<SearchResponse>
}
