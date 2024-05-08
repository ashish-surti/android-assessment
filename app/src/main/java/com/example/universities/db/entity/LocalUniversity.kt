package com.example.universities.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class LocalUniversity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "country") val country: String? = null,
    @ColumnInfo(name = "name") val name: String? = null,
    @ColumnInfo(name = "stateProvince") val stateProvince: String? = null,
    @ColumnInfo(name = "alphaTwoCode") val alphaTwoCode: String? = null
    )