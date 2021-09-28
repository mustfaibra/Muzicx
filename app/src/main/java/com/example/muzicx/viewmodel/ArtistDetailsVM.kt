package com.example.muzicx.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.muzicx.DataRepo
import com.example.muzicx.model.Artist
import com.example.muzicx.model.ArtistDetails
import com.example.muzicx.sealed.ApiResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArtistDetailsVM @Inject constructor(
    var repo: DataRepo
) : ViewModel() {
    var _details = MutableLiveData<ArtistDetails>()
    val details = _details
    var error = mutableStateOf("")
    var loading = mutableStateOf(true)

    fun getArtistDetails(id: Int) {
        loading.value = true
        viewModelScope.launch {
            val artist = repo.getArtist(id)
            val albums = repo.getArtistAlbums(id)
            if(artist is ApiResponse.Success && albums is ApiResponse.Success){
                loading.value = false
                _details.value = ArtistDetails(artist = artist.data!!,albums = albums.data!!)
            } else {
                loading.value = false
                error.value = albums.message!!
            }
        }
    }
}