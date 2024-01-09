package com.example.tubeplay.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.tubeplay.data.model.ResponseState
import com.example.tubeplay.domain.VideoItem
import com.example.tubeplay.presentation.VideoViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun VideoListScreen(navController: NavHostController) {

    val coroutineScope = rememberCoroutineScope()

    val viewModel: VideoViewModel = hiltViewModel()
    // Observe the video list state
    val videoListState by viewModel.videoListState.observeAsState(ResponseState.Success(emptyList()))

    // Define a lambda function to handle the search
    val onSearch: (String) -> Unit = { newQuery ->
        // Set the query in the ViewModel
        viewModel.setQuery(newQuery)
        // Fetch video list using the repository based on the user's query
        coroutineScope.launch {
            viewModel.searchVideos(newQuery)
        }

    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        // Include the VideoSearchBar Composable
        VideoSearchBar(onSearch = onSearch)

        when (videoListState) {

            is ResponseState.Success -> {
                val videoItems = (videoListState as ResponseState.Success).data
                VideoList(
                    videos = videoItems,
                    onItemClick = { videoItem ->
                        coroutineScope.launch {
                            try {
                                val encodedUrl = withContext(Dispatchers.IO) {
                                    URLEncoder.encode(
                                        videoItem.highResImageUrl,
                                        StandardCharsets.UTF_8.toString()
                                    )
                                }
                                navController.navigate("videoPlayer/$encodedUrl")
                            } catch (e: Exception) {
                                // Handle exceptions if the encoding or navigation fails
                                e.printStackTrace()
                            }
                        }
                    }
                )
            }

            is ResponseState.Error -> {
                val errorText =
                    (videoListState as ResponseState.Error).exception.message ?: "An error occurred"
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = errorText, style = MaterialTheme.typography.titleLarge)
                }
            }

            is ResponseState.Loading -> {
                // Display the loading indicator only after the "Search" button is clicked
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
        }
    }
}

@Composable
fun VideoList(
    videos: List<VideoItem>,
    onItemClick: (VideoItem) -> Unit
) {
    LazyColumn {
        items(videos) { video ->
            VideoItemRow(video = video, onItemClick = onItemClick)
            Divider()
        }
    }
}

@Composable
fun VideoItemRow(video: VideoItem, onItemClick: (VideoItem) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onItemClick(video) }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        GlideImageComposable(
            imageUrl = video.thumbnailUrl,
            modifier = Modifier.size(120.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = video.title)
    }
}

@Composable
fun VideoSearchBar(
    onSearch: (String) -> Unit
) {
    val viewModel: VideoViewModel = hiltViewModel()

    // Observe the query input text
    val query by viewModel.query.observeAsState("")

    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        BasicTextField(
            value = query,
            onValueChange = { newQuery ->
                // Set the query in the ViewModel
                viewModel.setQuery(newQuery)
            },
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.LightGray)
                .padding(8.dp),
            textStyle = TextStyle.Default.copy(color = Color.Black),
            singleLine = true,
            keyboardActions = KeyboardActions(
                onDone = {
                    // Trigger the search when the user presses "Done" on the keyboard
                    onSearch(query)
                }
            )
        )

        // Add a "Search" button to trigger the search
        Button(
            onClick = {
                // Trigger the search when the user clicks the "Search" button
                onSearch(query)
            },
            modifier = Modifier
                .align(Alignment.End)
                .padding(top = 8.dp)
        ) {
            Text(text = "Search")
        }
    }
}
