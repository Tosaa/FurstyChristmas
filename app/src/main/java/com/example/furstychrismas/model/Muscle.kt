package com.example.furstychrismas.model

import android.graphics.drawable.Drawable
import com.example.furstychrismas.R

enum class Muscle(val muscle:String,val icon:Int) {
    LEG("legs", R.drawable.ball),
    ARM("arms", R.drawable.ball),
    CHEST("chest", R.drawable.ball),
    ABS("abs", R.drawable.ball),
    NECK("neck", R.drawable.ball),
    BACK("back", R.drawable.ball),
    STABILITY("stability", R.drawable.ball),
    ALL("all", R.drawable.ball),
    FLEXIBILITY("flexibility", R.drawable.ball)

}