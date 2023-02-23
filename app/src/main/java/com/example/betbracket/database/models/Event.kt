package com.example.betbracket.database.models

data class Event(
    val title: String,
    val teamA: Player,
    val teamB: Player,
    val teamAReturn: Double = 1.0,
    val teamBReturn: Double = 1.0,
    var status: String = "Not started",
    val bets: MutableList<Bet> = mutableListOf<Bet>()
)
