package com.example.betbracket.events.providers

import com.example.betbracket.events.models.Bet
import com.example.betbracket.events.models.Event
import com.example.betbracket.players.Player


class EventProvider {
    companion object {

        private val eventlist = mutableListOf<Event>(
            Event(
                "EEee si",
                Player("Juan", 100.0,"https://miro.medium.com/max/720/1*W35QUSvGpcLuxPo3SRTH4w.png" ),
                Player("Pepe", 100.0, "https://miro.medium.com/max/720/1*W35QUSvGpcLuxPo3SRTH4w.png"),
            )
        )

        fun getEvents(): List<Event> = eventlist
        fun insertEvent(newEvent: Event) {
            eventlist.add(0,newEvent)
        }

        fun deleteEvent(pos: Int) {
            eventlist.removeAt(pos)
        }


    }
}