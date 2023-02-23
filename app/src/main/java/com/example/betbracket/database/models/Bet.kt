package com.example.betbracket.database.models

data class Bet (
    val player: Player,
    val amount: Double,
    val team: String
)
