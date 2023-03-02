package com.example.betbracket.events

import com.example.betbracket.database.BetDatabase
import com.example.betbracket.database.entities.Event
import com.example.betbracket.database.relations.EventWithPlayers

class EventsRepository(val db: BetDatabase) {

    suspend fun insertEvent(event: Event) = db.betDao.insertEvent(event)

    suspend fun deleteEvent(event: Event) = db.betDao.deleteEvent(event)

    fun deleteEvents() = db.betDao.deleteEvents()

    fun getEvents() = db.betDao.getAllEvents()
    fun getPlayers() = db.betDao.getAllPlayers()
    fun getEventsWithPlayers() = db.betDao.getEventsWithPlayers()
    suspend fun getEventWithPlayersByTitle(eventTitle: String): EventWithPlayers = db.betDao.getEventWithPlayersByTitle(eventTitle)


}
