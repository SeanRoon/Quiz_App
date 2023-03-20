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
    private var _currentIndex = MutableLiveData(0)
    val currentIndex: LiveData<Int>
        get() = _currentIndex
    private var _numOfIncorrect = 0
    val numOfIncorrect: Int
        get() = _numOfIncorrect
    private var _numOfCorrect = 0
    val numOfCorrect: Int
        get() = _numOfCorrect
    private var _gameWon = MutableLiveData(false)
    val gameWon: LiveData<Boolean>
        get() = _gameWon

    val currentQuestionAnswer: Boolean
        get() = myList[currentIndex.value ?: 0].answer
    val currentQuestionText: Int
        get() = myList[currentIndex.value ?: 0].resourceId
    val currentQuestionCheatStatus: Boolean
        get() = myList[currentIndex.value ?: 0].cheated
    val isGameWon: Boolean
        get() = _gameWon.value!!

    fun setCheatedStatusForCurrentQuestion(cheated: Boolean){
        myList.get(currentIndex.value ?: 0).cheated = cheated
    }
    fun checkIfGameWon(): Boolean{
        return _gameWon.value == numOfCorrect >= 3
    }
    fun checkAnswer(guess: Boolean): Boolean {
        if(myList[_currentIndex.value ?: 0].answer == guess){
            if(!(currentQuestionCheatStatus)){
                _numOfCorrect++
            }
        }
        else{
            _numOfIncorrect++
        }
//        checkIfGameWon()
        return (myList[_currentIndex.value ?: 0].answer == guess)
    }
    fun advanceScreen(){
        if(currentIndex.value!! < myList.size - 1) {
            _currentIndex.value = _currentIndex.value?.plus(1)
        }
        else{
            _currentIndex.value = 0
        }
    }
}