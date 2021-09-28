package com.example.muzicx.api.response


import com.example.muzicx.model.PlayList
import com.google.gson.annotations.SerializedName

data class PlayLists(
    @SerializedName("data")
    val data: List<PlayList>
)