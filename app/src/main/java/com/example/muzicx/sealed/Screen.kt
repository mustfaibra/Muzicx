package com.example.muzicx.sealed

import com.example.muzicx.R

sealed class Screen(val route: String,val title: String,val icon: Int = 0){
    object Splash: Screen("splash","Splash")
    object Home: Screen("home","Home", R.drawable.ic_home)
    object Navigator: Screen("navigator","Navigator", R.drawable.ic_explore)
    object Chat: Screen("chat","Chat", R.drawable.ic_chat)
    object Profile: Screen("profile","Profile", R.drawable.ic_user)
    object ArtistDetails: Screen("artist_details","Artist's Details")
    object GenreDetails: Screen("genre_details","Genre's Details")
    object Player: Screen("player","Player")
}
