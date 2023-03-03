package com.example.betbracket.events

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.betbracket.database.entities.Bet
import com.example.betbracket.database.entities.Event
import com.example.betbracket.database.entities.Player
import com.example.betbracket.database.relations.EventWithBets
import com.example.betbracket.database.relations.EventWithPlayers
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue

class EventViewModel(private val eventsRepository: EventsRepository) : ViewModel() {

    private var _currentEventAndBets = MutableLiveData<EventWithBets>()
    val currentEventAndBets: LiveData<EventWithBets> get() = _currentEventAndBets

    private var _currentEventAndPlayers = MutableLiveData<EventWithPlayers>()
    val currentEventAndPlayers: LiveData<EventWithPlayers> get() = _currentEventAndPlayers

    private var _returnDifference = MutableLiveData<Pair<Boolean, Int>>(Pair(false,0))
    val returnDifference: LiveData<Pair<Boolean, Int>> get() = _returnDifference

    private var _players = emptyList<Player>()

    fun onCreateEvent(title: String, player1: String, player2: String) = viewModelScope.launch {
        val newEvent = Event(title, player1, player2)
        eventsRepository.insertEvent(newEvent)
    }


    fun getEventsWithPlayers() = eventsRepository.getEventsWithPlayers()
    fun getPlayers() = eventsRepository.getPlayers()

    fun onDeleteEvent(event: Event) = viewModelScope.launch {
        eventsRepository.deleteEvent(event)
    }

    fun getEventWithPlayersByTitle(eventTitle: String) = viewModelScope.launch {
        _currentEventAndPlayers.value = eventsRepository.getEventWithPlayersByTitle(eventTitle)
    }

    fun getEventWithBetsByTitle(eventTitle: String) = viewModelScope.launch {
        _currentEventAndBets.value = eventsRepository.getEventWithBetsByTitle(eventTitle)
    }

    fun onPlaceBet(bettingPlayerName: String, amount: Double, prediction: String) =
        viewModelScope.launch {
            _currentEventAndBets.value!!.apply {
                Log.i("PLACE A BET", "event is -> $event")
                Log.i("PLACE A BET", "Bets is -> $bets")

                Log.i("PLACE A BET", "Prediction is -> $prediction")

                val newBet = Bet(bettingPlayerName, event.title, amount, prediction)
                Log.i("PLACE A BET", "placed bet is -> $newBet")
                eventsRepository.insertBet(newBet)
                Log.i("PLACE A BET", "$ Bet added -> ${_currentEventAndBets.value!!.bets}")
                _currentEventAndBets.value = eventsRepository.getEventWithBetsByTitle(event.title)
                calculateReturns()

            }
        }

    private fun calculateReturns() = viewModelScope.launch {
        Log.i("PLACE A BET", "Calculate returns CALLED")

        _currentEventAndBets.value!!.apply {

            // Calculate returns
            val totalPool = bets.sumOf { it.amount }
            Log.i("PLACE A BET", "Total Pool -> $totalPool")

            val poolPlayer1 = bets.filter { it.prediction == event.player1Name }.sumOf { it.amount }
            Log.i("PLACE A BET", "Player1 pool -> $poolPlayer1")
            val poolPlayer2 = bets.filter { it.prediction == event.player2Name }.sumOf { it.amount }
            Log.i("PLACE A BET", "Player2 pool $poolPlayer2")

            val player1Return = if (poolPlayer1 != 0.0) totalPool / poolPlayer1 else 1.0
            val player2Return = if (poolPlayer2 != 0.0) totalPool / poolPlayer2 else 1.0
            Log.i("PLACE A BET", "Returns are $player1Return and $player2Return")

            event.player1Return = player1Return
            event.player2Return = player2Return

            setReturnDifference(player1Return, player2Return)

            eventsRepository.updateEvent(event)
            _currentEventAndPlayers.value = eventsRepository.getEventWithPlayersByTitle(event.title)
        }
    }

    fun setReturnDifference(player1Return: Double, player2Return: Double) {
        val returnDifference = (player1Return - player2Return).absoluteValue
        val grade = when {
            returnDifference <= 0.1 -> 0
            returnDifference <= 0.7 -> 1
            returnDifference <= 1.3 -> 2
            else -> 3
        }
        _returnDifference.value =
            if (player1Return > player2Return) Pair(false, grade)
            else Pair(true, grade)

    }

    fun onDeclareWinner(winner: String){
        Log.i("PLEASE", "players are $_players")
        viewModelScope.launch {
        currentEventAndBets.value?.apply {
            event.status = "Winner is $winner"
            val winnerReturn = if (winner == event.player1Name) event.player1Return else event.player2Return
            Log.i("PLEASE", "Bets are -> $bets")
            bets.forEach { bet ->
                Log.i("PLEASE", "Computing for -> $bet")
                val player = _players.find { it.name == bet.betPlayerName }
                Log.i("PLEASE", "Player is -> $player")
                if (bet.prediction == winner) {
                    Log.i("PLEASE", "Player Won")
                    val winnings = bet.amount*winnerReturn
                    player?.balance = player?.balance?.plus(winnings)!!
                    player?.lastWagerResult = winnings
                }else{
                    Log.i("PLEASE", "Player Lost")
                    player?.balance = player?.balance?.minus(bet.amount)!!
                    player?.lastWagerResult = bet.amount.unaryMinus()
                }
                eventsRepository.updatePlayer(player)
            }
            eventsRepository.updateEvent(event)
        }

    }
    }

    fun setPlayerList(playerList: List<Player>?) {
        _players = playerList!!
    }
}

