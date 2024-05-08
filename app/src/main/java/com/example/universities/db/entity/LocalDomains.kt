package com.example.universities.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

// table to store the domains of the university
@Entity
data class LocalDomains(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "universityId") val universityId: Int,
    @ColumnInfo(name = "domain") val domain: String? = null
)