package com.example.muzicx.api.response


import com.example.muzicx.model.MyGenre
import com.google.gson.annotations.SerializedName

data class Genres(
    @SerializedName("data")
    val data: List<MyGenre>
)