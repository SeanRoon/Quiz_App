package com.example.quizapp

import android.media.MediaPlayer
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController

class QuizViewModel: ViewModel() {
    val myList: List<Question> = listOf(Question(R.string.question1, false, false), Question(R.string.question2, true, false), Question(R.string.question3, false, false), Question(R.string.question4, false, false), Question(R.string.question5, false, false))

    //    figure out which on should be mutable
    var _currentIndex = MutableLiveData(0)
    val currentIndex: LiveData<Int>
        get() = _currentIndex
    var numOfIncorrect = 0
        get() = numOfIncorrect
    var numOfCorrect = 0
        get() = numOfCorrect
    var _gameWon = MutableLiveData(false)
    val gameWon: LiveData<Boolean>
        get() = gameWon
    lateinit var mediaPlayer: MediaPlayer


    val currentQuestionAnswer: Boolean
        get() = myList[currentIndex.value ?: 0].answer
    val currentQuestionText: Int
        get() = myList[currentIndex.value ?: 0].resourceId
    val currentQuestionCheatStatus: Boolean
        get() = myList[currentIndex.value ?: 0].cheated

    fun setCheatedStatusForCurrentQuestion(cheated: Boolean){
        myList.get(currentIndex.value ?: 0).cheated = cheated
    }
    fun checkIfGameWon(){
        _gameWon.value = numOfCorrect >= 3
    }
    fun checkAnswer(guess: Boolean){
        if(myList[currentIndex.value ?: 0].answer == guess){
            if(!(currentQuestionCheatStatus)){
                numOfCorrect++
                mediaPlayer = MediaPlayer.create(context, R.raw.correctsound)
                mediaPlayer.start()
            }
        }
        else{
            Toast.makeText(activity, R.string.incorrect, Toast.LENGTH_SHORT).show()
            numOfIncorrect++
            mediaPlayer = MediaPlayer.create(context, R.raw.incorrectsound)
            mediaPlayer.start()
        }
    }
}