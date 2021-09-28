package com.example.muzicx

import com.example.muzicx.api.ApiServices
import com.example.muzicx.api.response.*
import com.example.muzicx.model.Artist
import com.example.muzicx.sealed.ApiResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class DataRepo(
    var services: ApiServices
) {

    suspend fun getAllGenres() : ApiResponse<Genres>{
        val response = try {
            services.getGenres()
        } catch (e: Exception){
            return ApiResponse.Error(message = "No internet connection !")
        }
        return ApiResponse.Success(data = response)
    }

    suspend fun getGenresArtists(id: Int) : ApiResponse<Artists>{
        val response = try {
            services.getGenreArtists(id = id)
        } catch (e: Exception){
            return ApiResponse.Error(message = "No internet connection !")
        }
        return ApiResponse.Success(data = response)
    }

    suspend fun getArtist(id: Int) : ApiResponse<Artist>{
        val response = try {
            services.getArtist(id = id)
        } catch (e: Exception){
            return ApiResponse.Error(message = "No internet connection !")
        }
        return ApiResponse.Success(data = response)
    }

    suspend fun getArtistAlbums(id: Int) : ApiResponse<Albums>{
        val response = try {
            services.getArtistAlbums(id = id)
        } catch (e: Exception){
            return ApiResponse.Error(message = "No internet connection !")
        }
        return ApiResponse.Success(data = response)
    }

    suspend fun getArtistPlaylists(id: Int) : ApiResponse<PlayLists>{
        val response = try {
            services.getArtistPlayLists(id = id)
        } catch (e: Exception){
            return ApiResponse.Error(message = "No internet connection !")
        }
        return ApiResponse.Success(data = response)
    }

    suspend fun getArtistTracks(id: Int) : ApiResponse<Tracks>{
        val response = try {
            services.getArtistTracks(id = id)
        } catch (e: Exception){
            return ApiResponse.Error(message = "No internet connection !")
        }
        return ApiResponse.Success(data = response)
    }


}