package com.example.furstychrismas.screen.overview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.furstychrismas.databinding.FragmentCardsOverviewFragmentBinding
import com.example.furstychrismas.util.Util
import org.koin.android.ext.android.inject

class CardsOverviewFragment : Fragment() {

    private lateinit var binding: FragmentCardsOverviewFragmentBinding
    private val cardViewModel: CardViewModel by inject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCardsOverviewFragmentBinding.inflate(layoutInflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner

        binding.dayCards.layoutManager = GridLayoutManager(requireContext(), 4)

        cardViewModel.cards.observe(viewLifecycleOwner) {
            binding.dayCards.adapter = CardAdapter(findNavController(), it)
        }

        binding.slider.addOnChangeListener { slider, value, fromUser ->
            cardViewModel.updateDateRepo(
                value.toInt()
            )
        }

        binding.exercisesButton.setOnClickListener {
            findNavController().navigate(CardsOverviewFragmentDirections.overviewExerciseOverview())
        }

        binding.testTextView.text = Util.getDrillPresets(requireActivity().assets).toString()

        return binding.root
    }
}