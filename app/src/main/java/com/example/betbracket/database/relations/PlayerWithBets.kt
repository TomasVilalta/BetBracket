package com.example.betbracket.database.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.example.betbracket.database.entities.Bet
import com.example.betbracket.database.entities.Player

class PlayerWithBets (
    @Embedded
    val player: Player,
    @Relation(
        parentColumn = "name",
        entityColumn = "betPlayerName"
    )
    val bets : List<Bet>
    )
