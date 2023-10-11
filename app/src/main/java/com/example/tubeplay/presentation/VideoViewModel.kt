package com.example.tubeplay.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tubeplay.data.model.ResponseState
import com.example.tubeplay.data.repository.IRepository
import com.example.tubeplay.domain.VideoItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VideoViewModel @Inject constructor(
    private val repository: IRepository) : ViewModel() {

    // State for holding the list of video items
    private val _videoImageState = MutableLiveData<String>()
    val videoImageState = _videoImageState

    // State for holding the list of video items
    private val _videoListState = MutableLiveData<ResponseState<List<VideoItem>>>()
    val videoListState = _videoListState

    // State for holding the current query input text
    private val _query = MutableLiveData<String>()
    val query = _query

    fun searchVideos(newQuery: String) {
        viewModelScope.launch {
            _videoListState.value = ResponseState.Loading
            _videoListState.value = repository.searchVideos(newQuery)
        }
    }

    // Function to update the query
    fun setQuery(newQuery: String) {
        _query.value = newQuery
    }


}
