package com.example.betbracket.events.models

import com.example.betbracket.players.Player

data class Bet (
    val player: Player,
    val amount: Double,
    val team: String
)
