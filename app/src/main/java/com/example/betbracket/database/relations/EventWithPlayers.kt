package com.example.betbracket.database.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.example.betbracket.database.entities.Event
import com.example.betbracket.database.entities.Player


class EventWithPlayers (
    @Embedded val event: Event,
    @Relation(
        parentColumn = "player1Name",
        entityColumn = "name"
    )
    val player1: Player,
    @Relation(
        parentColumn = "player2Name",
        entityColumn = "name"
    )
    val player2: Player

)