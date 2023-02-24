package com.example.betbracket.database.entities

import androidx.room.Entity

@Entity(
    tableName = "bets",
    primaryKeys = ["betPlayerName", "betEventTitle"]
)
data class Bet(
    val betPlayerName: String,
    val betEventTitle: String,
    val amount: Double,
    val prediction: String
)

