package com.example.muzicx.model


import com.google.gson.annotations.SerializedName

data class MyGenre(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("picture")
    val picture: String,
    @SerializedName("type")
    val type: String
)