package com.example.betbracket.events

import com.example.betbracket.database.BetDatabase
import com.example.betbracket.database.entities.Event
import com.example.betbracket.database.entities.Player

class EventsRepository(val db: BetDatabase) {

    suspend fun insertEvent(event: Event) = db.betDao.insertEvent(event)

    suspend fun deleteEvent(event: Event) = db.betDao.deleteEvent(event)

    suspend fun updateEvent(event: Event) = db.betDao.updateEvent(event)

    suspend fun getPlayerByName(playerName: String) = db.betDao.getPlayerByName(playerName)

    fun getEvents() = db.betDao.getAllEvents()
    fun getPlayers() = db.betDao.getAllPlayers()
    fun getEventsWithPlayers() = db.betDao.getEventsWithPlayers()


}
