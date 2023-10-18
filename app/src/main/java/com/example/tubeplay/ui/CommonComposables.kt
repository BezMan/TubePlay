package com.example.tubeplay.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage

@Composable
@OptIn(ExperimentalGlideComposeApi::class)
fun GlideImageComposable(imageUrl: String, modifier: Modifier) {
    GlideImage(
        model = imageUrl,
        contentDescription = null,
        modifier = modifier
    )
}