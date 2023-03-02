package com.example.betbracket.players

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.betbracket.database.entities.Player
import com.example.betbracket.players.playerForm.AvatarProvider
import kotlinx.coroutines.launch

class PlayerViewModel(private val playersRepository: PlayersRepository) : ViewModel() {

    private val _playerCount = MutableLiveData<Int>()
    val playerCount: LiveData<Int> get() = _playerCount

    private val _currentPlayerImage = MutableLiveData<String>()
    val currentPlayerImage: LiveData<String> get() = _currentPlayerImage

    init {

            Log.i("AVATAR", "init")
            _currentPlayerImage.value = AvatarProvider.defaultAvatar
            updatePlayerCount()

    }

    fun getPlayers() = playersRepository.getPlayers()


    private fun updatePlayerCount() {
        _playerCount.value = playersRepository.getPlayers().value?.size
    }


    fun onCreatePlayer(player: Player) = viewModelScope.launch {
        playersRepository.insertPlayer(player)
        updatePlayerCount()
    }

    fun onDelete(player: Player) = viewModelScope.launch {
        playersRepository.deletePlayer(player)
        updatePlayerCount()
    }

    fun onEditPlayer(player: Player) = viewModelScope.launch  {
        playersRepository.editPlayer(player)
    }


    fun setCurrentPlayerImage(url: String) {
        if (url != "default") {
            _currentPlayerImage.value = url
        } else {
            _currentPlayerImage.value = AvatarProvider.defaultAvatar
        }
    }

    suspend fun getPlayerByName(playerName: String): Player = playersRepository.getPlayerByName(playerName)




}