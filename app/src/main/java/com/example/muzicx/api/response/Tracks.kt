package com.example.muzicx.api.response


import com.example.muzicx.model.MyTrack
import com.google.gson.annotations.SerializedName

data class Tracks(
    @SerializedName("data")
    val data: MutableList<MyTrack>
)