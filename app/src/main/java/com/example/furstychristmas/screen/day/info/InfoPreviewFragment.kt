package com.example.furstychristmas.screen.day.info

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.furstychristmas.databinding.InfoFragmentBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class InfoPreviewFragment : Fragment() {

    val args: InfoPreviewFragmentArgs by navArgs()
    private val viewModel: InfoViewModel by viewModel<InfoViewModel> { parametersOf(LocalDate.parse(args.date, DateTimeFormatter.ISO_LOCAL_DATE)) }
    private lateinit var binding: InfoFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = InfoFragmentBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewmodel = viewModel
        return binding.root
    }
}