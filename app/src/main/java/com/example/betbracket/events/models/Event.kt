package com.example.betbracket.events.models

import com.example.betbracket.players.Player

data class Event(
    val title: String,
    val teamA: Player,
    val teamB: Player,
    var status: String = "Not started",
    val bets: MutableList<Bet> = mutableListOf<Bet>()
)
