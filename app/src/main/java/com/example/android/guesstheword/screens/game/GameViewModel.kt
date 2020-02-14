package com.example.android.guesstheword.screens.game

import android.os.CountDownTimer
import android.text.format.DateUtils
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel



/**
 * GameView Model for handling data displays decisions
 * Survives config changes
 */
class GameViewModel: ViewModel() {

    //Current time of game
    private val _currentTime = MutableLiveData<Long>()
    val currentTime: LiveData<Long>
        get() = _currentTime
    val currentTimeString = Transformations.map(currentTime) { time ->
        DateUtils.formatElapsedTime(time)
    }
    //Timer
    private val timer: CountDownTimer

    // The current word
    private var _word = MutableLiveData<String>()
    val word: LiveData<String>
        get() = _word

    //Word hint
    val wordHint = Transformations.map(word){ word ->
        val randomPosition = (1..word.length).random()
        "Current word has " + word.length + " letters" +
            "\nThe letter position " + randomPosition + " is " +
            word.get(randomPosition - 1).toUpperCase()
    }

    // The current score
    private var _score = MutableLiveData<Int>()
    val score: LiveData<Int>
        get() = _score

    //Check for game ending
    private val _eventGameFinish = MutableLiveData<Boolean>()
    val eventGameFinish: LiveData<Boolean>
        get() = _eventGameFinish

    // The list of words - the front of the list is the next word to guess
    lateinit var wordList: MutableList<String>

    companion object {
            //Time when the game is over
        private const val DONE = 0L
            //Countdown time interval
        private const val ONE_SECOND = 1000L
            //Total time for the game
        private const val COUNTDOWN_TIME = 60000L
    }

    //
    init {
        Log.i("GameViewModel", "GameViewModel Created")
        //Init of the word list and first round
        _word.value = ""
        _score.value = 0
        resetList()
        nextWord()
        //Timer initialization
        timer = object: CountDownTimer(COUNTDOWN_TIME, ONE_SECOND) {
                //One tick
            override fun onTick(millisUntilFinished: Long) {
                _currentTime.value = millisUntilFinished / ONE_SECOND
            }
                //When timer finishes
            override fun onFinish() {
                _currentTime.value = DONE
                    onGameFinish()
            }
        }
        timer.start()
    }

    override fun onCleared() {
        super.onCleared()
        Log.i("GameViewModel", "GameViewModel destroyed!")
        //Destroy timer to don't leave it running in the background
        timer.cancel()
    }

    /**
     * Resets the list of words and randomizes the order
     */
    private fun resetList() {
        wordList = mutableListOf(
                "queen",
                "hospital",
                "basketball",
                "cat",
                "change",
                "snail",
                "soup",
                "calendar",
                "sad",
                "desk",
                "guitar",
                "home",
                "railway",
                "zebra",
                "jelly",
                "car",
                "crow",
                "trade",
                "bag",
                "roll",
                "bubble"
        )
        wordList.shuffle()
    }

    /**
     * Moves to the next word in the list
     */
    private fun nextWord() {
        if (!wordList.isEmpty()) {
            //Select and remove a word from the list
            _word.value = wordList.removeAt(0)
        }else{
            resetList()
        }
    }

    /** Methods for buttons presses **/

    fun onSkip() {
        _score.value = (_score.value)?.minus(1)
        nextWord()
    }

    fun onCorrect() {
        _score.value = (_score.value)?.plus(1)
        nextWord()
    }

    /** Method to signal a game completed event **/
    fun onGameFinish(){
        _eventGameFinish.value = true
    }

    /** Method for avoiding Toast message lifecycle bug **/
    fun onGameFinishComplete(){
        _eventGameFinish.value = false
    }

}