package com.example.betbracket.players

class PlayerProvider {
    companion object {
        fun getPlayerCount(): Int = playerList.size

        fun updatePlayer(
            pos: Int,
            name: String,
            balance: Int,
            image: String?
        ) {

            playerList[pos].apply {
                this.name = name
                this.balance = balance
                if (image != null) {
                    this.image = image
                }
            }
        }

        fun getPlayerName(pos: Int): String = playerList[pos].name

        fun getPlayerBalance(pos: Int): Int = playerList[pos].balance

        fun insertPlayer(player: Player) {
            playerList.add(player)
        }

        fun deletePlayer(pos: Int) {
            playerList.removeAt(pos)
        }

        fun getPlayers(): List<Player> = playerList
        fun getPlayerImage(pos: Int): String = playerList[pos].image


        private val playerList = mutableListOf<Player>(
            Player(
                "Juan",
                100,
                "https://miro.medium.com/max/720/1*W35QUSvGpcLuxPo3SRTH4w.png",
                -14
            ),
            Player("Pepe", 74, "https://miro.medium.com/max/720/1*W35QUSvGpcLuxPo3SRTH4w.png", 21)
        )
    }


}