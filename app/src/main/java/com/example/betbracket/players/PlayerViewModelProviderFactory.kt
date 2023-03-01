package com.example.betbracket.players

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.betbracket.events.EventViewModel
import com.example.betbracket.events.EventsRepository

class PlayerViewModelProviderFactory (val playersRepository: PlayersRepository) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return PlayerViewModel(playersRepository) as T
    }
}