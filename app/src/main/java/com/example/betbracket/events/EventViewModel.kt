package com.example.betbracket.events

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.betbracket.database.models.Event
import com.example.betbracket.events.providers.EventProvider
import com.example.betbracket.database.models.Player
import com.example.betbracket.players.PlayerProvider

class EventViewModel : ViewModel() {
    private var _eventList = MutableLiveData<List<Event>>(EventProvider.getEvents())
    val eventList: LiveData<List<Event>> get() = _eventList


    fun onCreateEvent(title: String, player1: String, player2: String) {
        val player1Object: Player? = PlayerProvider.getPlayers().find { it.name == player1 }
        val player2Object = PlayerProvider.getPlayers().find { it.name == player2 }
        if (player1Object == null || player2Object == null) {
            Log.e("CREATE EVENT", "Cannot find player name")
        } else {
            val newEvent = Event(title, player1Object, player2Object)
            EventProvider.insertEvent(newEvent)
            _eventList.value = getEvents()
        }

    }

    fun getEvents() = EventProvider.getEvents()

    fun getPlayerNameList(): List<String> = PlayerProvider.getPlayers().map { it.name }

    fun onDeleteEvent(pos: Int) {
        EventProvider.deleteEvent(pos)
        _eventList.value = getEvents()
    }


}