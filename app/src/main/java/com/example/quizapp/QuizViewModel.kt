package com.example.quizapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class QuizViewModel: ViewModel() {
    val myList: List<Question> = listOf(Question(R.string.question1, false, false), Question(R.string.question2, true, false), Question(R.string.question3, false, false), Question(R.string.question4, false, false), Question(R.string.question5, false, false))

    //    figure out which on should be mutable
    private var _currentIndex = MutableLiveData(0)
    val currentIndex: MutableLiveData<Int>
        get() = _currentIndex
    private var numOfIncorrect = 0
        get() = numOfIncorrect
    private var numOfCorrect = 0
        get() = numOfCorrect
    private var _gameWon = MutableLiveData(0)
    val gameWon: MutableLiveData<Int>
        get() = gameWon

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
        if(numOfCorrect >= 3)
            _gameWon = true
    }

}