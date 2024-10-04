package redtoss.example.furstychristmas.calendar.content.workout

enum class Muscle(val muscle: String, val iconId: String) {
    LEG("legs", "ic_legs"),
    ARM("arms", "ic_arm"),
    CHEST("chest", "ic_chest"),
    ABS("abs", "ic_abs"),
    NECK("neck", "ball"),
    BACK("back", "ic_back"),
    STABILITY("stability", "ball"),
    ALL("all", "ball"),
    FLEXIBILITY("flexibility", "ic_flex"),
    BREAK("break", "ic_timer");

    companion object {
        fun byName(name: String): Muscle = values().associateBy { it.muscle }.getOrDefault(name, BREAK)
    }
}