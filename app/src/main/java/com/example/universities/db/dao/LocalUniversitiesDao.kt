package com.example.universities.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.universities.db.entity.LocalDomains
import com.example.universities.db.entity.LocalUniversity
import com.example.universities.db.entity.LocalWebPages

@Dao
interface LocalUniversitiesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUniversity(university: LocalUniversity): Long

    @Query("SELECT * FROM LocalUniversity")
    suspend fun getAllUniversities(): List<LocalUniversity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDomain(domain: LocalDomains)

    @Query("SELECT * FROM LocalDomains where universityId = :universityId")
    suspend fun getAllDomains(universityId: Int): List<LocalDomains>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWebPage(webPage: LocalWebPages)

    @Query("SELECT * FROM LocalWebPages where universityId = :universityId")
    suspend fun getAllWebPages(universityId: Int): List<LocalWebPages>
}