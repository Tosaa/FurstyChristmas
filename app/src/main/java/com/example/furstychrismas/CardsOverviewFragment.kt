package com.example.furstychrismas

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.furstychrismas.databinding.FragmentCardsOverviewBinding

class CardsOverviewFragment : Fragment() {

    private lateinit var binding: FragmentCardsOverviewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =
            FragmentCardsOverviewBinding.inflate(layoutInflater)
        binding.dayCards.layoutManager = GridLayoutManager(requireContext(), 4)
        binding.dayCards.adapter = CardAdapter(layoutInflater,findNavController())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }
}