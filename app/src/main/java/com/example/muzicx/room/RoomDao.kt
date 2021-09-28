package com.example.muzicx.room

import androidx.room.Insert
import com.example.muzicx.model.MyAlbum
import com.example.muzicx.model.MyTrack

interface RoomDao {

    @Insert
    suspend fun insertTrack(track: MyTrack)

    @Insert
    suspend fun insertAlbum(album: MyAlbum)


}