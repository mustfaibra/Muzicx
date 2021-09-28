package com.example.muzicx.model

import androidx.compose.ui.graphics.Color
import com.google.gson.annotations.SerializedName
import kotlin.random.Random

data class MyTrack(
    @SerializedName("artist")
    val artist: Artist,
    @SerializedName("duration")
    val duration: Int,
    @SerializedName("id")
    val id: Int,
    @SerializedName("link")
    val link: String = "",
    @SerializedName("md5_image")
    val cover: Color = Color(
        Random.nextFloat(),
        Random.nextFloat(),
        Random.nextFloat(),
        1f
    ),
    @SerializedName("preview")
    val preview: String = "",
    @SerializedName("rank")
    val rank: Int = Random.nextInt(1,1000),
    @SerializedName("title")
    val title: String,
    @SerializedName("type")
    val type: String = "track"
)