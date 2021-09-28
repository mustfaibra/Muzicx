package com.example.muzicx.api

import com.example.muzicx.api.response.*
import com.example.muzicx.model.*
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiServices {

    @GET("genre/")
    suspend fun getGenres(): Genres

    @GET("genre/{id}/artists")
    suspend fun getGenreArtists(
        @Path("id") id: Int
    ) : Artists

    @GET("artist/{id}")
    suspend fun getArtist(
        @Path("id") id: Int
    ): Artist

    @GET("artist/{id}/albums")
    suspend fun getArtistAlbums(
        @Path("id") id: Int
    ): Albums

    @GET("artist/{id}/playlists")
    suspend fun getArtistPlayLists(
        @Path("id") id: Int
    ) : PlayLists

    @GET("artist/{id}/tracks")
    suspend fun getArtistTracks(
        @Path("id") id: Int
    ) : Tracks

}