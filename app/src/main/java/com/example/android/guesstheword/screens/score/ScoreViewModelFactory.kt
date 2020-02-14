package com.example.android.guesstheword.screens.score

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/**
 * Responsible for creating the instance of a ScoreViewModel with the factory design pattern
 */
class ScoreViewModelFactory(private val finalScore: Int): ViewModelProvider.Factory {

        //Construct the ScoreViewModel Object
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(ScoreViewModel::class.java)){
                //Instance of ScoreViewModel with the final score passed with the factory design pattern
            return ScoreViewModel(finalScore) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}