package com.example.muzicx.model


import com.google.gson.annotations.SerializedName

data class PlayList(
    @SerializedName("checksum")
    val checksum: String,
    @SerializedName("creation_date")
    val creationDate: String,
    @SerializedName("id")
    val id: Long,
    @SerializedName("link")
    val link: String,
    @SerializedName("md5_image")
    val md5Image: String,
    @SerializedName("picture")
    val picture: String,
    @SerializedName("picture_big")
    val pictureBig: String,
    @SerializedName("picture_medium")
    val pictureMedium: String,
    @SerializedName("picture_small")
    val pictureSmall: String,
    @SerializedName("picture_type")
    val pictureType: String,
    @SerializedName("picture_xl")
    val pictureXl: String,
    @SerializedName("public")
    val `public`: Boolean,
    @SerializedName("title")
    val title: String,
    @SerializedName("tracklist")
    val tracklist: String,
    @SerializedName("type")
    val type: String,
)