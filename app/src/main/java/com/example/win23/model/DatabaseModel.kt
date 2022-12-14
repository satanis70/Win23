package com.example.win23.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tableRoom")
data class DatabaseModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int?,
    @ColumnInfo(name = "position")
    val betPosition: Int,
    @ColumnInfo(name = "name")
    val betName: String,
    @ColumnInfo(name = "odd")
    val betOdd: String,
    @ColumnInfo(name = "amount")
    val betAmount: String,
    @ColumnInfo(name= "status")
    val betStatus: String,
    @ColumnInfo(name = "capital")
    val bankCapital: String
)
