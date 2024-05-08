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
    // insert university in the database
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUniversity(university: LocalUniversity): Long

    // get all the universities from the local database
    @Query("SELECT * FROM LocalUniversity")
    suspend fun getAllUniversities(): List<LocalUniversity>

    // insert the domain of the university
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDomain(domain: LocalDomains)

    // get the all the domains of the particular university
    @Query("SELECT * FROM LocalDomains where universityId = :universityId")
    suspend fun getAllDomains(universityId: Int): List<LocalDomains>

    // insert the web page of the university
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWebPage(webPage: LocalWebPages)

    // get the all the web pages of the particular university
    @Query("SELECT * FROM LocalWebPages where universityId = :universityId")
    suspend fun getAllWebPages(universityId: Int): List<LocalWebPages>
}