package com.example.betbracket.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.versionedparcelable.VersionedParcelize

@Entity(tableName = "events")
data class Event(
    @PrimaryKey(autoGenerate = false)
    val title: String,
    val player1Name: String,
    val player2Name: String,
    val player1Return: Double = 1.0,
    val player2Return: Double = 1.0,
    var status: String = "Not started"
)
