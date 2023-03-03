package com.example.betbracket.events

import com.example.betbracket.database.BetDatabase
import com.example.betbracket.database.entities.Bet
import com.example.betbracket.database.entities.Event
import com.example.betbracket.database.entities.Player
import com.example.betbracket.database.relations.EventWithBets
import com.example.betbracket.database.relations.EventWithPlayers

class EventsRepository(val db: BetDatabase) {

    suspend fun insertEvent(event: Event) = db.betDao.insertEvent(event)

    suspend fun deleteEvent(event: Event) = db.betDao.deleteEvent(event)

    suspend fun updateEvent(event: Event) = db.betDao.updateEvent(event)

    suspend fun insertBet(newBet: Bet) = db.betDao.insertBet(newBet)

    fun getPlayers() = db.betDao.getAllPlayers()
    fun getEventsWithPlayers() = db.betDao.getEventsWithPlayers()
    suspend fun getEventWithPlayersByTitle(eventTitle: String): EventWithPlayers = db.betDao.getEventWithPlayersByTitle(eventTitle)

    suspend fun getEventWithBetsByTitle(eventTitle: String): EventWithBets = db.betDao.getEventWithBetsByTitle(eventTitle)
    suspend fun updatePlayer(player: Player) = db.betDao.updatePlayer(player)


}
