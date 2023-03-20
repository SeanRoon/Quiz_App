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
    lateinit var mediaPlayer: MediaPlayer
    private val viewModel: QuizViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        val rootView = binding.root
        Log.i("MainActivity", "onCreate Called")
        binding.question.text = getString(viewModel.currentQuestionText)
        binding.falseButton.setOnClickListener(){
            checkAnswer(false)
        }
        binding.trueButton.setOnClickListener(){
            checkAnswer(true)
        }
        binding.advanceButton.setOnClickListener(){
            viewModel.advanceScreen()
            updateText()
        }
        binding.question.setOnClickListener(){
            viewModel.advanceScreen()
            updateText()
        }
        binding.cheatButton.setOnClickListener(){
            val action = MainFragmentDirections.actionMainFragmentToCheatFragment(viewModel.currentQuestionAnswer)
            rootView.findNavController().navigate(action)
        }
        setHasOptionsMenu(true)
        return rootView
    }
    fun checkAnswer(guess: Boolean){
        if(viewModel.currentQuestionAnswer == guess){
            if(viewModel.currentQuestionCheatStatus){
                Toast.makeText(activity, R.string.cheating_is_bad, Toast.LENGTH_SHORT).show()
                mediaPlayer = MediaPlayer.create(context, R.raw.correctsound)
                mediaPlayer.start()
            }
            else{
                Toast.makeText(activity, R.string.correct, Toast.LENGTH_SHORT).show()
                mediaPlayer = MediaPlayer.create(context, R.raw.incorrectsound)
                mediaPlayer.start()
                if(viewModel.isGameWon){
                    val action = MainFragmentDirections.actionMainFragmentToGameWonFragment(viewModel.numOfIncorrect)
                    findNavController().navigate(action)
                }
            }
        }
        else{
            Toast.makeText(activity, R.string.incorrect, Toast.LENGTH_SHORT).show()
        }
        viewModel.checkAnswer(guess)
    }

//    fun previousScreen(){
//        if(currentIndex > 0){
//            currentIndex--
//        }
//        else{
//            currentIndex = 4
//        }
//        updateText()
//    }
    fun updateText(){
        binding.question.text = getString(viewModel.currentQuestionText)
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
