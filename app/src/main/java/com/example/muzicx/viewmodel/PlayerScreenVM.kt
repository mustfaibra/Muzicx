package com.example.muzicx.viewmodel

import android.os.Handler
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.muzicx.DataRepo
import com.example.muzicx.model.MyTrack
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlayerScreenVM @Inject constructor() : ViewModel(){
    private val tracks = mutableStateOf<List<MyTrack>>(listOf())
    val playing = mutableStateOf(true)
    val currentPlayingIndex = mutableStateOf(0)

    fun getTrackPlaying(index: Int) = tracks.value[index]

    fun playNext(){
        var current = currentPlayingIndex.value
        if (current == tracks.value.size - 1) {
            current = 0
        } else {
            current += 1
        }
        updateCurrentPlayingIndex(current)
    }

    fun playPrevious(){
        var current = currentPlayingIndex.value
        if (current == 0) {
            current = tracks.value.size - 1
        } else {
            current -= 1
        }
        updateCurrentPlayingIndex(current)
    }

    fun setTrackQueue(tracks: List<MyTrack>){
        this.tracks.value = tracks
    }

    private fun updateCurrentPlayingIndex(index: Int){
        currentPlayingIndex.value = index
    }
}