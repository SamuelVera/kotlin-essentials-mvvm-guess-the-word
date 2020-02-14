package com.example.android.guesstheword.screens.score

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * Score ViewModel with the final score as a parameter from another fragment
 */
class ScoreViewModel(finalScore: Int): ViewModel() {

        //The final score
    private var _score = MutableLiveData<Int>()
    val score: LiveData<Int>
        get() = _score
        //Boolean to signal a play again
    private var _playAgain = MutableLiveData<Boolean>()
    val playAgain: LiveData<Boolean>
        get() = _playAgain

    init {

        _score.value = finalScore
        _playAgain.value = false

        Log.i("ScoreViewModel", "Final score is $finalScore")
    }

    /** Methods to handle the onPlayAgain click event and the lifecycle bug **/
    fun onPlayAgain(){
        _playAgain.value = true
    }
    fun onPlayAgainComplete(){
        _playAgain.value = false
    }


}