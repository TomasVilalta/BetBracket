package com.example.betbracket.players

import com.example.betbracket.database.BetDatabase
import com.example.betbracket.database.entities.Player

class PlayersRepository(val db: BetDatabase) {

    suspend fun insertPlayer(player: Player) = db.betDao.insertPlayer(player)

    suspend fun deletePlayer(player: Player) = db.betDao.deletePlayer(player)

    suspend fun editPlayer(player: Player) = db.betDao.updatePlayer(player)

    fun getPlayers() = db.betDao.getAllPlayers()

    suspend fun getPlayerByName(playerName: String): Player = db.betDao.getPlayerByName(playerName)


}