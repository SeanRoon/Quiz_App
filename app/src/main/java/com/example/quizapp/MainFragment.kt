package com.example.quizapp

import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.quizapp.databinding.FragmentMainBinding

const val KEY_CURRENT_INDEX = "current_index_key"
const val KEY_USER_CHEATED = "user_cheated_key"

class MainFragment : Fragment() {

    private var _binding : FragmentMainBinding? = null
    private val binding get() = _binding!!
    private val viewModel: QuizViewModel by activityViewModels()
    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        super.onSaveInstanceState(savedInstanceState)
        savedInstanceState.putInt(KEY_CURRENT_INDEX, currentIndex)
        savedInstanceState.putBoolean(KEY_USER_CHEATED, userCheated)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        val rootView = binding.root
        Log.i("MainActivity", "onCreate Called")
        binding.question.text = getString(myList[currentIndex].resourceId)
        binding.falseButton.setOnClickListener(){
            checkAnswer(false)
        }
        binding.trueButton.setOnClickListener(){
            checkAnswer(true)
        }
        binding.advanceButton.setOnClickListener(){
            advanceScreen()
        }
        binding.question.setOnClickListener(){
            advanceScreen()
        }
        binding.cheatButton.setOnClickListener(){
            val answer: Boolean = myList[currentIndex].answer
            val action = MainFragmentDirections.actionMainFragmentToCheatFragment(answer)
            rootView.findNavController().navigate(action)
        }
        setFragmentResultListener("REQUESTING_DID_CHEAT_KEY"){ requestKey: String, bundle: Bundle ->
            userCheated = bundle.getBoolean("DID_CHEAT_KEY")
        }
        setHasOptionsMenu(true)
        return rootView
    }
    fun checkAnswer(guess: Boolean){
        if(viewModel.myList[currentIndex].answer == guess){
            if(userCheated){
                Toast.makeText(activity, R.string.cheating_is_bad, Toast.LENGTH_SHORT).show()
            }
            else{
                Toast.makeText(activity, R.string.correct, Toast.LENGTH_SHORT).show()
                if(viewModel.numOfCorrect > 2){
                    val action = MainFragmentDirections.actionMainFragmentToGameWonFragment(numOfIncorrect)
                    findNavController().navigate(action)
                }
            }
        }
        else{
            Toast.makeText(activity, R.string.incorrect, Toast.LENGTH_SHORT).show()
        }
    }
    fun advanceScreen(){
        if(currentIndex < myList.size - 1) {
            currentIndex++
        }
        else{
            currentIndex = 0
        }
        userCheated = false
        updateText()
    }
    fun previousScreen(){
        if(currentIndex > 0){
            currentIndex--
        }
        else{
            currentIndex = 4
        }
        updateText()
    }
    fun updateText(){
        binding.question.text = getString(myList[currentIndex].resourceId)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.options_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(item, requireView().findNavController()) || super.onOptionsItemSelected(item)
    }
}
