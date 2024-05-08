package com.example.universities.db

import androidx.room.Room
import com.example.universities.MainApplication


// main database class to store the data in the room database
object DatabaseBuilder {

    private var INSTANCE: AppDatabase? = null

    fun getInstance(): AppDatabase {
        if (INSTANCE == null) {
            synchronized(AppDatabase::class) {
                INSTANCE = buildRoomDB()
            }
        }
        return INSTANCE!!
    }

    private fun buildRoomDB() =
        Room.databaseBuilder(MainApplication.mInstance, AppDatabase::class.java, "university").build()
}