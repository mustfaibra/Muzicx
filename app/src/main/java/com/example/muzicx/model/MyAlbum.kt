package com.example.muzicx.model


import androidx.compose.ui.graphics.Color
import com.example.muzicx.api.response.Genres
import com.example.muzicx.api.response.Tracks
import com.google.gson.annotations.SerializedName
import kotlin.random.Random

data class MyAlbum(
    @SerializedName("artist")
    val artist: Artist,
    @SerializedName("available")
    val available: Boolean = true,
    @SerializedName("cover")
    val cover: Color = Color(
        Random.nextFloat(),
        Random.nextFloat(),
        Random.nextFloat(),
        1f
    ),
    @SerializedName("fans")
    val fans: Int = Random.nextInt(100,100000),
    @SerializedName("genre_id")
    val genreId: Int = 0,
    @SerializedName("genres")
    val genres: Genres? = null,
    @SerializedName("id")
    val id: Int,
    @SerializedName("nb_tracks")
    val nbTracks: Int,
    @SerializedName("rating")
    val rating: Int = Random.nextInt(0,10),
    @SerializedName("release_date")
    val releaseDate: String = "20${Random.nextInt(10,21)}",
    @SerializedName("title")
    val title: String,
    @SerializedName("tracks")
    val tracks: Tracks,
    @SerializedName("type")
    val type: String = "album",
)