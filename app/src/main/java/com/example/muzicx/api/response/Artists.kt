package com.example.muzicx.api.response

import com.example.muzicx.model.Artist
import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName

data class Artists(
    @SerializedName("data")
    @Expose
    val data: List<Artist>
)
