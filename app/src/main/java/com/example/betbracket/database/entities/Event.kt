package com.example.betbracket.database.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "events"
    ,
        foreignKeys = [
            ForeignKey(
                entity = Player::class,
                parentColumns = arrayOf("name"),
                childColumns = arrayOf("player1Name"),
                onDelete = ForeignKey.CASCADE
            ),
            ForeignKey(
                entity = Player::class,
                parentColumns = arrayOf("name"),
                childColumns = arrayOf("player2Name"),
                onDelete = ForeignKey.CASCADE
            )
        ])
data class Event(
    @PrimaryKey(autoGenerate = false)
    val title: String,
    val player1Name: String,
    val player2Name: String,
    var player1Return: Double = 1.0,
    var player2Return: Double = 1.0,
    var status: String = "Not started"
)
