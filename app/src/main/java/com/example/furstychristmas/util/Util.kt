package com.example.furstychristmas.util

import android.content.res.AssetManager
import android.util.Log
import com.example.furstychristmas.domain.workout.model.Drill
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import java.io.BufferedReader
import java.io.InputStreamReader
import java.time.LocalDate

object Util {

    private val moshi = Moshi.Builder()
        .add(DrillAdapter())
        .add(KotlinJsonAdapterFactory())
        .build()

    private val type = Types.newParameterizedType(
        Map::class.java,
        String::class.java,
        Types.newParameterizedType(List::class.java, Drill::class.java)
    )

    private val drillAdapter = moshi.adapter<Map<String, List<Drill>>>(type)

    fun intToDayInDecember(day: Int): LocalDate {
        return LocalDate.of(2020, 12, day)
    }

    fun getDrillPresets(assetManager: AssetManager): Map<String, List<Drill>> {
        var drillPresets = emptyMap<String, List<Drill>>()

        try {
            val json = BufferedReader(
                InputStreamReader(
                    assetManager.open("excersises_2020.json"),
                    "UTF-8"
                )
            ).readText()
            drillPresets = drillAdapter.failOnUnknown().fromJson(json) ?: emptyMap()
        } catch (exception: Exception) {
            Log.w("Util", "can't read excersises from json: $exception")
        }
        return drillPresets
    }
}