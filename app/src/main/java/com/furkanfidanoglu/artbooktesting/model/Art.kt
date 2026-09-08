package com.furkanfidanoglu.artbooktesting.model

import androidx.room3.ColumnInfo
import androidx.room3.Entity
import androidx.room3.PrimaryKey
import kotlinx.serialization.Serializable

@Entity(tableName = "art_table")
@Serializable
data class Art(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "artistName")
    val artistName: String,

    @ColumnInfo(name = "year")
    val year: Int,

    @ColumnInfo(name = "image")
    val image: String
)