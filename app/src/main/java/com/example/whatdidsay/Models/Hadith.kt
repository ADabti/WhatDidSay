package com.example.whatdidsay.Models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "hadith_table")
data class Hadith(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val text: String,
    val keywords: String
)
