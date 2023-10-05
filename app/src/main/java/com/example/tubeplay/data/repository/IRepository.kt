package com.example.tubeplay.data.repository

import com.example.tubeplay.data.model.ResponseState
import com.example.tubeplay.domain.VideoItem

interface IRepository {
    suspend fun searchVideos(query: String): ResponseState<List<VideoItem>>
}
