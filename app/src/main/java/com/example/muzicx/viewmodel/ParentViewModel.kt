package com.example.muzicx.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.muzicx.model.MyTrack

class ParentViewModel : ViewModel(){
    // the song that to be played in player screen
    private var _track = MutableLiveData<MyTrack>()
    val track: LiveData<MyTrack>
    get() {
        return _track
    }
    fun setTrackToPlay(track: MyTrack){
        _track.value = track
    }

}