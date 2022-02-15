package com.example.muzicx.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.muzicx.model.MyTrack
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * A View model with hiltViewModel annotation that is used to access this view model everywhere needed
 */
@HiltViewModel
class ParentViewModel @Inject constructor() : ViewModel(){
    // the song that to be played in player screen
    var songToPlayIndex = 0
    private var _tracks = MutableLiveData<List<MyTrack>>()
    val tracks: LiveData<List<MyTrack>>

    get() {
        return _tracks
    }
    fun setQueue(tracks: List<MyTrack>){
        _tracks.value = tracks
    }

}