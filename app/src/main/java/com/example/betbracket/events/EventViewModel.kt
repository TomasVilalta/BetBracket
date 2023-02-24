package com.example.betbracket.events

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.betbracket.database.entities.Event
import com.example.betbracket.events.providers.EventProvider
import com.example.betbracket.database.entities.Player
import com.example.betbracket.players.PlayerProvider
import kotlinx.coroutines.launch

class EventViewModel(val eventsRepository: EventsRepository) : ViewModel() {


    fun onCreateEvent(title: String, player1: String, player2: String) = viewModelScope.launch {
            val newEvent = Event(title, player1, player2)
            eventsRepository.insertEvent(newEvent)
    }

    fun getEvents() = eventsRepository.getEvents()

    fun getPlayerNameList(): List<String> = PlayerProvider.getPlayers().map { it.name }

    fun onDeleteEvent(event: Event)= viewModelScope.launch {
        eventsRepository.deleteEvent(event)
    }

    fun getPlayerByName(playerName: String): Player{
        return eventsRepository.getPlayerByName(playerName)
    }


}