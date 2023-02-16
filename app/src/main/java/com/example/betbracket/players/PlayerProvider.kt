package com.example.betbracket.players

import androidx.lifecycle.MutableLiveData

class PlayerProvider {
    companion object {
        fun getPlayerCount(): Int = playerList.size

        fun updatePlayer (pos: Int, name: String, balance: Int) {
            playerList[pos].name= name
            playerList[pos].balance= balance
        }

        fun getPlayerName(pos: Int): String = playerList[pos].name

        fun getPlayerBalance(pos: Int): Int = playerList[pos].balance

        fun insertPlayer(player: Player){
            playerList.add(player)
        }
        fun deletePlayer(pos: Int){
            playerList.removeAt(pos)
        }

        fun getPlayers(): List<Player> = playerList


        private val playerList = mutableListOf<Player>(
            Player("Juan", 100, -14),
            Player("Pepe", 74, 21)
        )
    }




}