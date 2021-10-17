package com.example.furstychristmas.screen.developer

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.furstychristmas.databinding.FragmentDeveloperBinding
import com.example.furstychristmas.util.DateUtil
import org.koin.android.ext.android.inject
import timber.log.Timber
import java.time.LocalDate
import java.time.LocalTime
import java.time.Month
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

class DeveloperFragment : Fragment() {

    private lateinit var binding: FragmentDeveloperBinding

    private val preferences: SharedPreferences by inject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDeveloperBinding.inflate(layoutInflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner

        binding.inputCalendar.date = DateUtil.today().atTime(LocalTime.now()).toEpochSecond(ZoneOffset.UTC) * 1000L
        binding.inputCalendar.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val developerDay = LocalDate.of(year, Month.of(month + 1), dayOfMonth)
            Timber.d("on dev day changed listener: $developerDay")
            preferences.edit().putString("developer_date", developerDay.format(DateTimeFormatter.ISO_LOCAL_DATE)).apply()
            DateUtil.setDevDay(developerDay)
        }

        return binding.root
    }


}