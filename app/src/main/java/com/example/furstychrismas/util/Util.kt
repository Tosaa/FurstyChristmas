package com.example.furstychrismas.util

import android.content.res.AssetManager
import android.util.Log
import com.example.furstychrismas.model.*
import com.example.furstychrismas.persistence.CardDatabase
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.Exception
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

    suspend fun createDaysInDB(cardDatabase: CardDatabase) {
        cardDatabase.cardDao().insertCards(IntRange(1, 24).map {
            Card(intToDayInDecember(it), false)
        })

    }

    fun getDrillPresets(assetManager: AssetManager): Map<String, List<Drill>> {
        var drillPresets = emptyMap<String, List<Drill>>()

        try {
            val json = BufferedReader(
                InputStreamReader(
                    assetManager.open("excersises.json"),
                    "UTF-8"
                )
            ).readText()
            drillPresets = drillAdapter.failOnUnknown().fromJson(json) ?: emptyMap()
        } catch (exception: Exception) {
            Log.w("Util", "can't read excersises from json: $exception")
        }
        return drillPresets
    }

/*
    private fun test1(drillAdapter: JsonAdapter<Map<String, List<Drill>>>) {
        val exampleMap = mapOf(
            Pair(
                "test", listOf(
                    Drill(Repetition(5), Exercise.PLANK, Seconds(5)),
                    Drill(Repetition(2), Exercise.PUSHUP_TO_PLANK, Seconds(0))
                )
            )
        )
        Log.i("UtilTest", "real obj:$exampleMap")

        val exampleJson = drillAdapter.toJson(
            exampleMap
        )
        Log.i("UtilTest", "tojson obj:$exampleJson")

        val exampleObj = drillAdapter.fromJson(exampleJson)
        Log.i("UtilTest", "fromjson obj:$exampleObj")
    }

    private fun test2(drillAdapter: JsonAdapter<Drill>) {
        val exampledrill = Drill(Repetition(5), Exercise.PLANK, Seconds(5))
        Log.i("UtilTest", "real obj:$exampledrill")

        val exampleJson = drillAdapter.toJson(
            exampledrill
        )
        Log.i("UtilTest", "tojson obj:$exampleJson")

        val exampleObj = drillAdapter.fromJson(exampleJson)
        Log.i("UtilTest", "fromjson obj:$exampleObj")
    }
    */
/*
* example how to save Json
*
    val exampleMap = mapOf(
        Pair(
            "test", listOf(
                Drill(Repetition(5), Exercise.PLANK, Seconds(5)),
                Drill(Repetition(2), Exercise.PUSHUP_TO_PLANK, Seconds(0))
            )
        )
    )
    val example = drillAdapter.toJson(
        exampleMap
    )

* */
}