package com.example.muzicx.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider

abstract class RoomDb : RoomDatabase() {

    abstract fun getDao(): RoomDao

    class PopulateDataClass @Inject constructor(
        private val roomDb: Provider<RoomDb>,
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            val dao = roomDb.get().getDao()
            scope.launch {

            }
        }

    }
}