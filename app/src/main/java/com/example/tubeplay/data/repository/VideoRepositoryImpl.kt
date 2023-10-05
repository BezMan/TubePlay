package com.example.tubeplay.data.repository

import com.example.tubeplay.data.model.ResponseState
import com.example.tubeplay.data.api.YouTubeApiService
import com.example.tubeplay.domain.VideoItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class VideoRepositoryImpl(private val apiService: YouTubeApiService) : IRepository {

        private val YOUTUBE_API_KEY = "AIzaSyBWQ_XB0eZuSdpZQ95Y9T_GIBQ2OhBohW8"

    override suspend fun searchVideos(query: String): ResponseState<List<VideoItem>> {

        return withContext(Dispatchers.IO) {
            try {
                val call = apiService.searchVideos("snippet", YOUTUBE_API_KEY, query)
                val response = call.execute()

                if (response.isSuccessful) {
                    val searchResponse = response.body()
                    searchResponse?.let {
                        val videoItems = it.items.map { searchResult ->
                            val snippet = searchResult.snippet
                            VideoItem(
                                videoId = searchResult.id.videoId,
                                title = snippet.title,
                                description = snippet.description,
                                thumbnailUrl = snippet.thumbnails.default.url
                            )
                        }
                        ResponseState.Success(videoItems)
                    } ?: ResponseState.Error(Exception("Empty response"))
                } else {
                    ResponseState.Error(Exception("API request failed"))
                }
            } catch (e: Exception) {
                ResponseState.Error(e)
            }
        }

    }
}
