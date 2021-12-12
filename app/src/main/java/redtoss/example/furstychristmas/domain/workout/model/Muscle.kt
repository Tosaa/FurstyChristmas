package redtoss.example.furstychristmas.domain.workout.model

import redtoss.example.furstychristmas.R

enum class Muscle(val muscle: String, val icon: Int) {
    LEG("legs", R.drawable.ic_legs),
    ARM("arms", R.drawable.ic_arm),
    CHEST("chest", R.drawable.ic_chest),
    ABS("abs", R.drawable.ic_abs),
    NECK("neck", R.drawable.ball),
    BACK("back", R.drawable.ic_back),
    STABILITY("stability", R.drawable.ball),
    ALL("all", R.drawable.ball),
    FLEXIBILITY("flexibility", R.drawable.ic_flex),
    BREAK("break", R.drawable.ic_timer);

    companion object {
        fun byName(name: String): Muscle = values().associateBy { it.muscle }.getOrDefault(name, BREAK)
    }
}