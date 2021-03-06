package com.example.muzicx.model

import com.example.muzicx.api.response.Albums
import com.example.muzicx.api.response.Tracks

data class ArtistDetails(
    var artist: Artist,
    var albums: Albums,
    var reviews: List<Review>? = null,
    var contacts: List<Contact>? = null,
)