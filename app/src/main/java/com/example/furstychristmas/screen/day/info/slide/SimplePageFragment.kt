package com.example.furstychristmas.screen.day.info.slide

import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.furstychristmas.databinding.InfoFragmentBinding
import com.example.furstychristmas.domain.info.model.InfoPageContent

class SimplePageFragment(private val content: InfoPageContent) : Fragment() {
    private lateinit var binding: InfoFragmentBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = InfoFragmentBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        content.imageid?.let { binding.imageSrc = it }
        binding.infoText = Html.fromHtml(content.text, Html.FROM_HTML_MODE_COMPACT)
        binding.subtitle = content.title
        return binding.root
    }

    companion object {
        fun getFragmentWithContent(content: InfoPageContent): SimplePageFragment = SimplePageFragment(content)
    }
}