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
import org.koin.android.ext.android.inject

class CardsOverviewFragment : Fragment() {

    private lateinit var binding: FragmentCardsOverviewFragmentBinding
    private val cardViewModel: CardViewModel by inject()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentCardsOverviewFragmentBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding.lifecycleOwner = viewLifecycleOwner
        binding.dayCards.layoutManager = GridLayoutManager(requireContext(), 4)
        cardViewModel.cards.observe(viewLifecycleOwner) {
            binding.dayCards.adapter = CardAdapter(findNavController(), it)
        }
        binding.slider.addOnChangeListener { slider, value, fromUser ->
            cardViewModel.date.postValue(
                value.toInt()
            )
        }
        binding.exercisesButton.setOnClickListener {
            findNavController().navigate(CardsOverviewFragmentDirections.overviewExerciseOverview())
        }
        return binding.root
    }
}