package com.example.tubeplay.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.tubeplay.presentation.VideoViewModel


@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun VideoPlayerScreen(imageUrl: String) {

    val viewModel: VideoViewModel = hiltViewModel()

    val videoImageState by viewModel.videoImageState.observeAsState(imageUrl)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        GlideImage(
            model = videoImageState,
            contentDescription = null,
            modifier = Modifier.fillMaxSize()
        )
    }

}