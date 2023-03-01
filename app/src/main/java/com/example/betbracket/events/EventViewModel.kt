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

class EventViewModel(private val eventsRepository: EventsRepository) : ViewModel() {

//    private var _eventsList : MutableList<Event>? = (eventsRepository.getEvents().value as MutableList<Event>?)
//
//    private var _playerList : MutableList<Player>? = (eventsRepository.getPlayers().value as MutableList<Player>?)

    private var _eventsAndPlayersList = MutableLiveData<MutableList<Triple<Event,Player,Player>>>()
    val playerList: LiveData<MutableList<Triple<Event,Player,Player>>> get() = _eventsAndPlayersList

    init {
//        _eventsAndPlayersList.value = _eventsList.map {it.player1Name == }
    }

    fun onCreateEvent(title: String, player1: String, player2: String) = viewModelScope.launch {
        val newEvent = Event(title, player1, player2)
        eventsRepository.insertEvent(newEvent)
    }


    fun getEventsWithPlayers() = eventsRepository.getEventsWithPlayers()
    fun getPlayers() = eventsRepository.getPlayers()

    fun onDeleteEvent(event: Event) = viewModelScope.launch {
        eventsRepository.deleteEvent(event)
    }

//    fun getPlayerByName(playerName: String) = viewModelScope.launch {
//        Log.i("diomio", "getPlayerByName called")
//        _player.value = eventsRepository.getPlayerByName(playerName)
//        Log.i("diomio", _player.value.toString())
//    }




}