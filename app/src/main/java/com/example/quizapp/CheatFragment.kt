package com.example.quizapp

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
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
        setHasOptionsMenu(true)
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
}