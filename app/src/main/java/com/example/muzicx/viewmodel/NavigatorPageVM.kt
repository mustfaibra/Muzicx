package com.example.muzicx.viewmodel

import androidx.compose.runtime.*
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.muzicx.DataRepo
import com.example.muzicx.model.MyGenre
import com.example.muzicx.sealed.ApiResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NavigatorPageVM @Inject constructor(
    var repo: DataRepo
) : ViewModel(){

    var loading = mutableStateOf(true)
    var error = mutableStateOf("")
    private var _genres = MutableLiveData<List<MyGenre>>()
    val genres = _genres

    fun getGenres(){
        viewModelScope.launch {
            val response = repo.getAllGenres()
            when(response){
                is ApiResponse.Success->{
                    _genres.value = response.data!!.data
                    loading.value = false
                }
                is ApiResponse.Error->{
                    error.value = response.message!!
                    loading.value = false
                }
            }
        }
    }
}