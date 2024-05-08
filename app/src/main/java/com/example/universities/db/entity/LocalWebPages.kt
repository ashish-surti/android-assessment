package com.example.universities.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

// table to store web page of university
@Entity
data class LocalWebPages(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "universityId") val universityId: Int,
    @ColumnInfo(name = "webPage") val webPage: String? = null
)