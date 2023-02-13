package com.example.betbracket.players

import android.net.PlatformVpnProfile
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.betbracket.players.adapter.PlayerAdapter
import kotlinx.coroutines.launch

class PlayerViewModel : ViewModel() {

    private val _playerList = MutableLiveData<List<Player>>()
    val playerList: LiveData<List<Player>> get() = _playerList

    private val _playerCount = MutableLiveData<Int>()
    val playerCount: LiveData<Int> get() = _playerCount

    init {
        Log.i("VIEWMODEL", "CREATED")
        viewModelScope.launch {
            _playerList.value = getPlayers()
            updatePlayerCount()
        }
    }

    override fun onCleared() {
        Log.i("VIEWMODEL", "DESTROYED")
        super.onCleared()
    }

    fun getPlayers() = PlayerProvider.getPlayers()


    private fun updatePlayerCount() {
        _playerCount.value = PlayerProvider.getPlayerCount()
    }

    fun getPlayerName(pos: Int): String {
        return PlayerProvider.getPlayerName(pos)
    }



    fun onCreatePlayer(name: String, balance: Int) {
        val newPlayer = Player(name, balance)
        PlayerProvider.insertPlayer(newPlayer)
        Log.i("VIEWMODEL", "Player Created")
        _playerList.value = getPlayers()
        updatePlayerCount()
    }

    fun onDelete(pos: Int) {
        Log.i("onDelete", "playerPos before deletePlayer: $pos")
        PlayerProvider.deletePlayer(pos)
        _playerList.value = getPlayers()
        updatePlayerCount()
    }

    fun onEditPlayer (pos: Int, name: String, balance: Int){
        PlayerProvider.updatePlayer(pos,name,balance)
        _playerList.value = getPlayers()
    }

}