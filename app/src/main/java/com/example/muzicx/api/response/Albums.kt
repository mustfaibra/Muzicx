package com.example.muzicx.api.response

import com.example.muzicx.model.MyAlbum
import com.google.gson.annotations.SerializedName

data class Albums(
    @SerializedName("data")
    val data: List<MyAlbum>
)
