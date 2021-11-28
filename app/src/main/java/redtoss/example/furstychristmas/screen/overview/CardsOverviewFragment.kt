package redtoss.example.furstychristmas.screen.overview

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import org.koin.android.ext.android.inject
import redtoss.example.furstychristmas.BuildConfig
import redtoss.example.furstychristmas.R
import redtoss.example.furstychristmas.databinding.FragmentCardsOverviewFragmentBinding
import redtoss.example.furstychristmas.domain.info.usecase.LoadInfoUseCase
import redtoss.example.furstychristmas.domain.workout.usecase.LoadWorkoutUseCase
import redtoss.example.furstychristmas.eula.EulaActivity
import redtoss.example.furstychristmas.util.NavigationHelper

class CardsOverviewFragment : Fragment() {

    private lateinit var binding: FragmentCardsOverviewFragmentBinding
    private val cardViewModel: CardViewModel by inject()
    private val loadInfoUseCase: LoadInfoUseCase by inject()
    private val loadWorkoutUseCase: LoadWorkoutUseCase by inject()
    private lateinit var adapter: DayOverviewAdapter

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
        adapter = DayOverviewAdapter(NavigationHelper(findNavController(), loadInfoUseCase, loadWorkoutUseCase, cardViewModel.viewModelScope), emptyList())
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
            val eula = R.raw.eula2021
            bundle.putInt("eula", eula)
            intent.putExtras(bundle)
            startActivity(intent)
        }
        if (BuildConfig.DEBUG) {
            binding.developerButton.visibility = View.VISIBLE
            binding.developerSpace.visibility = View.VISIBLE
            binding.developerButton.setOnClickListener {
                findNavController().navigate(CardsOverviewFragmentDirections.overviewDeveloper())
            }
        }
    }
}