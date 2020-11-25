package com.example.furstychristmas.screen.overview

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.furstychristmas.R
import com.example.furstychristmas.databinding.FragmentCardsOverviewFragmentBinding
import com.example.furstychristmas.eula.EulaActivity
import org.koin.android.ext.android.inject

class CardsOverviewFragment : Fragment() {

    private lateinit var binding: FragmentCardsOverviewFragmentBinding
    private val cardViewModel: CardViewModel by inject()
    private lateinit var adapter: CardAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCardsOverviewFragmentBinding.inflate(layoutInflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner

        setupCards()
        setupButton()

        return binding.root
    }

    private fun setupCards() {
        adapter = CardAdapter(findNavController(), emptyList())
        binding.dayCards.adapter = adapter
        binding.dayCards.layoutManager = GridLayoutManager(requireContext(), 4)

        cardViewModel.cards.observe(viewLifecycleOwner) {
            adapter.setItems(it)
        }
    }

    private fun setupButton() {
        binding.exercisesButton.setOnClickListener {
            findNavController().navigate(CardsOverviewFragmentDirections.overviewExerciseOverview())
        }
        binding.infoButton.setOnClickListener {
            val intent = Intent(requireContext(), EulaActivity::class.java)
            val bundle = Bundle()
            val eula = R.raw.eula2
            bundle.putInt("eula", eula)
            intent.putExtras(bundle)
            startActivity(intent)
        }
    }
}