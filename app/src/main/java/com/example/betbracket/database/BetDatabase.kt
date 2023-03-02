package com.example.betbracket.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.betbracket.database.dao.BetDao
import com.example.betbracket.database.entities.Bet
import com.example.betbracket.database.entities.Event
import com.example.betbracket.database.entities.Player

@Database(
    entities = [
        Bet::class,
        Event::class,
        Player::class
    ],
    version = 3
)
abstract class BetDatabase : RoomDatabase(){

    abstract val betDao : BetDao

    companion object{
        @Volatile
        private var INSTANCE: BetDatabase? = null

        fun getInstance(context: Context): BetDatabase{
            synchronized(this){
                return INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    BetDatabase::class.java,
                    "betbracket_db"
                )
                    .fallbackToDestructiveMigration()
                    .build().also{
                    INSTANCE = it
                }
            }
        }

    }

}