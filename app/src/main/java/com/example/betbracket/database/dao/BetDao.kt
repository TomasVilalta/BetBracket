package com.example.betbracket.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.betbracket.database.entities.Bet
import com.example.betbracket.database.entities.Event
import com.example.betbracket.database.entities.Player
import com.example.betbracket.database.relations.EventWithPlayers
import com.example.betbracket.database.relations.PlayerWithBets

@Dao
interface BetDao {

    //  PLAYERS

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlayer(player: Player)

    @Update
    suspend fun updatePlayer(player: Player)

    @Delete
    suspend fun deletePlayer(player: Player)

    @Query("SELECT * FROM players")
    fun getAllPlayers(): LiveData<List<Player>>

    @Query("SELECT * from players WHERE name = :playerName" )
    suspend fun getPlayerByName(playerName: String): Player


    //  Events
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEvent(event: Event)

    @Update
    suspend fun updateEvent(event: Event)

    @Delete
    suspend fun deleteEvent(event: Event)

    @Query("SELECT * FROM events")
    fun getAllEvents(): LiveData<List<Event>>

    @Query("SELECT * FROM events")
    fun getEventsWithPlayers(): LiveData<List<EventWithPlayers>>

//    Bets
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBet(bet: Bet)







}