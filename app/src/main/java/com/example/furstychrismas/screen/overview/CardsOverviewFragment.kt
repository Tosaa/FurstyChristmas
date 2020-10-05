package com.example.furstychrismas.screen.overview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.furstychrismas.databinding.FragmentCardsOverviewBinding

class CardsOverviewFragment : Fragment() {

    private lateinit var binding: FragmentCardsOverviewBinding
    private val cardViewModel: CardViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentCardsOverviewBinding.inflate(layoutInflater)
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
        return binding.root
    }
}