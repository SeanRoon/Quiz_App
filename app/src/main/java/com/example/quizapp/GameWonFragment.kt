package com.example.quizapp

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.quizapp.databinding.FragmentCheatBinding
import com.example.quizapp.databinding.FragmentGameWonBinding

class GameWonFragment : Fragment() {

    private var _binding: FragmentGameWonBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGameWonBinding.inflate(inflater, container, false)
        val rootView = binding.root
        val args = GameWonFragmentArgs.fromBundle(requireArguments())
        val numOfIncorrect = args.numOfIncorrect
        binding.numOfWrongTextView.text = "You had ${numOfIncorrect} wrong answers"
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
