package com.example.betbracket.players

data class Player(
    val name: String,
    val balance: Int,
    val lastWagerResult: Int = 0
    ){

}