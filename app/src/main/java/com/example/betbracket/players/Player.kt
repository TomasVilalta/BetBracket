package com.example.betbracket.players

data class Player(
    var name: String,
    var balance: Int,
    var image: String,
    var lastWagerResult: Int = 0
    ){

}