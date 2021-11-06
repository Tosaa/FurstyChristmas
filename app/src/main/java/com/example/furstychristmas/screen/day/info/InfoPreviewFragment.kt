package com.example.furstychristmas.screen.day.info

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.observe
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.furstychristmas.databinding.InfoViewpagerFragmentBinding
import com.example.furstychristmas.domain.info.model.InfoPageContent
import com.example.furstychristmas.screen.day.info.slide.SimplePageFragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class InfoPreviewFragment : Fragment() {

    val args: InfoPreviewFragmentArgs by navArgs()
    private val viewModel: InfoViewModel by viewModel<InfoViewModel> { parametersOf(LocalDate.parse(args.date, DateTimeFormatter.ISO_LOCAL_DATE)) }
    private lateinit var binding: InfoViewpagerFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = InfoViewpagerFragmentBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        viewModel.title.observe(viewLifecycleOwner) {
            binding.title.text = it
        }
        viewModel.pages.observe(viewLifecycleOwner) { pages ->
            activity?.let { activity ->
                binding.infoPager.adapter = InfoPageAdapter(activity, pages)
            }
        }
        return binding.root
    }

    inner class InfoPageAdapter(activity: FragmentActivity, private val pages: List<InfoPageContent>) : FragmentStateAdapter(activity) {
        override fun getItemCount(): Int = pages.size

        override fun createFragment(position: Int): Fragment = SimplePageFragment.getFragmentWithContent(pages[position])

    }
}