package com.example.muzicx.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.muzicx.DataRepo
import com.example.muzicx.api.response.Artists
import com.example.muzicx.model.Artist
import com.example.muzicx.model.MyGenre
import com.example.muzicx.sealed.ApiResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GenreDetailsVM @Inject constructor(
    var repo: DataRepo
) : ViewModel(){
    val loading = mutableStateOf(true)

    var _artists = MutableLiveData<List<Artist>>()
    val artists = _artists
    val error = mutableStateOf("")

    fun getGenreArtists(id: Int){
        viewModelScope.launch {
            val response = repo.getGenresArtists(id)
            when(response){
                is ApiResponse.Success -> {
                    _artists.value = response.data!!.data
                    loading.value = false
                    error.value = ""
                }
                is ApiResponse.Error -> {
                    error.value = response.message!!
                    loading.value = false
                }
            }
        }
    }
}