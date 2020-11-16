package com.example.furstychrismas.screen.overview

import android.icu.util.LocaleData
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.furstychrismas.databinding.FragmentCardsOverviewFragmentBinding
import com.example.furstychrismas.repository.DateRepository
import com.example.furstychrismas.util.Util
import org.koin.android.ext.android.inject
import java.time.LocalDate
import java.time.LocalTime

class CardsOverviewFragment : Fragment() {

    private lateinit var binding: FragmentCardsOverviewFragmentBinding
    private val cardViewModel: CardViewModel by inject()
    private lateinit var adapter: CardAdapter
    private val dateRepository : DateRepository by inject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCardsOverviewFragmentBinding.inflate(layoutInflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner

        adapter = CardAdapter(findNavController(), emptyList())
        binding.dayCards.adapter = adapter
        binding.dayCards.layoutManager = GridLayoutManager(requireContext(), 4)

        cardViewModel.cards.observe(viewLifecycleOwner) {
            adapter.setItems(it)
        }

        binding.slider.addOnChangeListener { _, value, fromUser ->
            if (fromUser) {
                cardViewModel.updateDateRepo(
                    value.toInt()
                )
            }
        }
        binding.slider.value = dateRepository.today.value?.dayOfMonth?.toFloat() ?: LocalDate.now().dayOfMonth.toFloat()
        binding.exercisesButton.setOnClickListener {
            findNavController().navigate(CardsOverviewFragmentDirections.overviewExerciseOverview())
        }

        binding.testTextView.text = Util.getDrillPresets(requireActivity().assets).toString()

        return binding.root
    }
}