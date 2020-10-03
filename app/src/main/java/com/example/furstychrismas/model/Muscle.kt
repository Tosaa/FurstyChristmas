package com.example.furstychrismas.model

import com.example.furstychrismas.R

enum class Muscle(val muscle:String,val icon:Int) {
    LEG("legs", R.drawable.body),
    ARM("arms", R.drawable.ball),
    CHEST("chest", R.drawable.infinite),
    ABS("abs", R.drawable.ball),
    NECK("neck", R.drawable.ball),
    BACK("back", R.drawable.ball),
    STABILITY("stability", R.drawable.ball),
    ALL("all", R.drawable.ball),
    FLEXIBILITY("flexibility", R.drawable.ball);


}