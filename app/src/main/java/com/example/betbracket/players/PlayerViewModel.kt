package com.example.betbracket.players

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.betbracket.players.playerForm.AvatarProvider
import kotlinx.coroutines.launch

class PlayerViewModel : ViewModel() {

    private val _playerList = MutableLiveData<List<Player>>()
    val playerList: LiveData<List<Player>> get() = _playerList

    private val _playerCount = MutableLiveData<Int>()
    val playerCount: LiveData<Int> get() = _playerCount

    private val _playerImage = MutableLiveData<String>()
    val playerImage: LiveData<String> get() = _playerImage

    init {

        viewModelScope.launch {
            _playerList.value = getPlayers()
            Log.i("AVATAR", "init")
            _playerImage.value = AvatarProvider.defaultAvatar
            updatePlayerCount()

        }
    }

    fun getPlayers() = PlayerProvider.getPlayers()


    private fun updatePlayerCount() {
        _playerCount.value = PlayerProvider.getPlayerCount()
    }


    fun onCreatePlayer(name: String, balance: Double) {
        val newPlayer = _playerImage.value?.let { Player(name, balance, it) }
        if (newPlayer != null) {
            PlayerProvider.insertPlayer(newPlayer)
        }
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

    fun onEditPlayer(pos: Int, name: String, balance: Double) {
        PlayerProvider.updatePlayer(pos, name, balance, _playerImage.value)
        _playerList.value = getPlayers()
    }

    fun getPlayerName(pos: Int): String = PlayerProvider.getPlayerName(pos)
    fun getPlayerBalance(pos: Int): Double = PlayerProvider.getPlayerBalance(pos)
    fun setCurrentPlayerImage(url: String) {
        if (url != "default") {
            _playerImage.value = url
        } else {
            _playerImage.value = AvatarProvider.defaultAvatar
        }
    }

    fun getPlayerImage(pos: Int): String {
        _playerImage.value = PlayerProvider.getPlayerImage(pos)
        return PlayerProvider.getPlayerImage(pos)
    }

}