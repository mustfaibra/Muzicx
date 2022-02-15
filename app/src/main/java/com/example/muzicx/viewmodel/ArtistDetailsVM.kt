package com.example.muzicx.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.muzicx.DataRepo
import com.example.muzicx.api.response.Albums
import com.example.muzicx.api.response.Tracks
import com.example.muzicx.model.*
import com.example.muzicx.sealed.ApiResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.log
import kotlin.random.Random

@HiltViewModel
class ArtistDetailsVM @Inject constructor(
    private var repo: DataRepo
) : ViewModel() {
    val TAG = "ArtistViewModel"
    private val _cart = MutableLiveData<MutableList<MyTrack>>(mutableListOf())
    val cart: LiveData<MutableList<MyTrack>> = _cart

    private var _details = MutableLiveData<ArtistDetails>()
    val details: LiveData<ArtistDetails>
    get(){
        return _details
    }
    var error = mutableStateOf("")
    var loading = mutableStateOf(true)

    fun getArtistDetails(id: Int) {
        Log.d(TAG, "getArtistDetails: getting artist details ... ")
        viewModelScope.launch {
            val response = repo.getArtist(id)
            if(response is ApiResponse.Success ){
                val artist = response.data!!
                // generate fake albums to populate the UI
                val albums = mutableListOf<MyAlbum>()
                for(i in 1..15){
                    val tracks = Tracks(mutableListOf())
                    for (x in 1..5){
                        tracks.data.add(
                            MyTrack(
                                artist = artist,
                                duration = Random.nextInt(80,300),
                                id = x,
                                title = listOf(
                                    "We gonna die",
                                    "Beautiful Lies",
                                    "Mornings",
                                    "Sad nights",
                                    "Lovely",
                                    "Momento",
                                    "Love",
                                    "Dark Fire",
                                    "White Horse",
                                    "All alone",
                                    "La noche"
                                ).shuffled().first()
                            )
                        )
                    }
                    albums.add(
                        MyAlbum(
                            title = listOf(
                                    "Dream ${Random.nextInt(0, 10)}",
                                    "Future ${Random.nextInt(0, 10)}",
                                    "Luxury ${Random.nextInt(1, 5)}",
                                    "Corazon ${Random.nextInt(1, 5)}",
                                    "Destiny ${Random.nextInt(1, 5)}",
                                    "Habits ${Random.nextInt(1, 5)}"
                                ).shuffled().last(),
                            artist = artist,
                            id = i,
                            nbTracks = tracks.data.size,
                            tracks = tracks
                        )
                    )
                }
                val reviews = mutableListOf<Review>()
                for(x in 1..15){
                    reviews.add(Review(id = x))
                }
                _details.value = ArtistDetails(artist = artist,albums = Albums(albums),reviews = reviews)
                loading.value = false
            } else {
                loading.value = false
                error.value = response.message!!
            }
        }
    }

    fun updateCart(track: MyTrack, alreadyAdded: Boolean) {
        if (alreadyAdded) {
            // its already there , we should remove it
            Log.d(TAG, "updateCart: removing")
            _cart.value?.remove(track)
        } else {
            // not in cart currently, we should add it :)
            Log.d(TAG,"UpdateCart: adding")
            _cart.value?.add(track)
        }
    }
}