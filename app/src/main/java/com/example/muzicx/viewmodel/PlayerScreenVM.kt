package com.example.muzicx.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.muzicx.DataRepo
import com.example.muzicx.model.MyTrack
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PlayerScreenVM @Inject constructor(
    repo: DataRepo,
    private var parentViewModel: ParentViewModel
) : ViewModel(){

    val track: LiveData<MyTrack>
    get(){
        return parentViewModel.track
    }
}