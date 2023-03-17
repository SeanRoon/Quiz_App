package com.example.quizapp

import android.app.Fragment
import android.os.Bundle
import android.view.*
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.quizapp.databinding.FragmentCheatBinding
import com.example.quizapp.databinding.FragmentGameWonBinding
import android.media.MediaPlayer
import androidx.fragment.app.activityViewModels


class GameWonFragment : androidx.fragment.app.Fragment() {

    private var _binding: FragmentGameWonBinding? = null
    private val binding get() = _binding!!
    lateinit var mediaPlayer: MediaPlayer
    private val viewModel: QuizViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGameWonBinding.inflate(inflater, container, false)
        val rootView = binding.root
        val args = GameWonFragmentArgs.fromBundle(requireArguments())
        lateinit var mediaPlayer: MediaPlayer
        val numOfIncorrect = args.numOfIncorrect
        binding.numOfWrongTextView.text = "You had ${numOfIncorrect} wrong answers"
        setHasOptionsMenu(true)
        mediaPlayer = MediaPlayer.create(context, R.raw.gamewon)
        mediaPlayer.setLooping(true)
        mediaPlayer.start()
        binding.playPauseButton.setOnClickListener() {
            if(mediaPlayer.isPlaying){
                mediaPlayer.pause()
                binding.playPauseButton.setImageResource(R.drawable.ic_baseline_play_arrow_24)
            }
            else{
                mediaPlayer.start()
                binding.playPauseButton.setImageResource(R.drawable.ic_baseline_pause_circle_outline_24)
            }
        }
        binding.fastForwardButton.setOnClickListener(){
            mediaPlayer.seekTo(mediaPlayer.currentPosition + 1000)
        }
        binding.rewindButton.setOnClickListener(){
            mediaPlayer.seekTo(mediaPlayer.currentPosition - 1000)
        }
        return rootView
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

    override fun onStop() {
        super.onStop()
        mediaPlayer.release()
    }
}
