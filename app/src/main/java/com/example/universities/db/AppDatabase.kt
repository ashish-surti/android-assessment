package com.example.universities.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.universities.db.dao.LocalUniversitiesDao
import com.example.universities.db.entity.LocalDomains
import com.example.universities.db.entity.LocalUniversity
import com.example.universities.db.entity.LocalWebPages

// All the tables and queries for the database
@Database(entities = [LocalUniversity::class, LocalWebPages::class, LocalDomains::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun universityDao(): LocalUniversitiesDao
}