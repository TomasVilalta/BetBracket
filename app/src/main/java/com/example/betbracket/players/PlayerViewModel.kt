package com.example.betbracket.players

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.betbracket.players.adapter.PlayerAdapter

class PlayerViewModel : ViewModel() {

    private val  _playerList = MutableLiveData<MutableList<Player>>()
            val playerList: LiveData<MutableList<Player>> get() = _playerList

    private val _playerCount = MutableLiveData<Int>()
            val playerCount : LiveData<Int> get() = _playerCount

    init {
        _playerList.value = PlayerProvider.playerList.toMutableList()
        _playerCount.value = _playerList.value!!.size

    }

    fun onDelete(playerPos: Int){
        _playerList.value?.removeAt(playerPos)
        updatePlayerCount()
    }



    private fun updatePlayerCount() {
        _playerCount.value = _playerList.value!!.size
    }

    fun getPlayerName (pos: Int): String{
        return _playerList.value?.get(pos)?.name ?: ""
    }

    fun onCreatePlayer() : Int{
        val newPlayer = Player("Paco", 100)
        _playerList.value?.add(newPlayer)
//        adapter.notifyItemInserted(playerMutableList.size - 1)
//        binding.playerList.layoutManager?.scrollToPosition(playerMutableList.size - 1)
        updatePlayerCount()
        return _playerCount.value?.minus(1) ?: 0

    }


}