package com.example.betbracket.database.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.example.betbracket.database.entities.Bet
import com.example.betbracket.database.entities.Event


data class EventWithBets (
    @Embedded val event: Event,
    @Relation(
        parentColumn = "eventTitle",
        entityColumn = "betEventTitle"
    )
    val bets : List<Bet>
    )