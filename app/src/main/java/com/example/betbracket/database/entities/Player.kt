package com.example.betbracket.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "players")
data class Player(
    @PrimaryKey(autoGenerate = false)
    var name: String,
    var balance: Double,
    var image: String,
    var lastWagerResult: Double = 0.0
    )

