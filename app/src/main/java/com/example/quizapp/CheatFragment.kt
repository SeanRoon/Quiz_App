package com.example.quizapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.findNavController
import com.example.quizapp.databinding.FragmentCheatBinding

class CheatFragment : Fragment() {

    private var _binding: FragmentCheatBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCheatBinding.inflate(inflater, container, false)
        val rootView = binding.root
        val args = CheatFragmentArgs.fromBundle(requireArguments())
        val correctAnswer = args.answer
        binding.showAnswerButton.setOnClickListener(){
            binding.answerText.text = correctAnswer.toString()
            setFragmentResult("REQUESTING_DID_CHEAT_KEY", bundleOf("DID_CHEAT_KEY" to true))
        }
        return rootView
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}