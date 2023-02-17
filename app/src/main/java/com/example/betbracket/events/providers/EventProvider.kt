package com.example.betbracket.events.providers

import com.example.betbracket.events.models.Bet
import com.example.betbracket.events.models.Event
import com.example.betbracket.players.Player


class EventProvider {
    companion object {

        private val eventlist = mutableListOf<Event>(
            Event(
                "EEee si",
                Player("Juan", 100,"https://miro.medium.com/max/720/1*W35QUSvGpcLuxPo3SRTH4w.png" ),
                Player("Pepe", 100, "https://miro.medium.com/max/720/1*W35QUSvGpcLuxPo3SRTH4w.png"),
                "Not started",
                mutableListOf<Bet>()
            )
        )

        fun getEvents(): List<Event> = eventlist


    }
}