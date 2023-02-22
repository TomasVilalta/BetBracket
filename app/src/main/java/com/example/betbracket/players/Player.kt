package com.example.betbracket.players

data class Player(
    var name: String,
    var balance: Double,
    var image: String,
    var lastWagerResult: Double = 0.0
    ){

}