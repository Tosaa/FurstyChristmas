package com.example.furstychrismas.model

enum class Exercise(
    val exerciseName: String,
    val muscles: List<Muscle>,
    val description: String = "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet."
) {
    PUSHUP("push up", listOf(Muscle.ARM, Muscle.CHEST)),
    PUSHUP_ONE_SIDED("push up with one hand up", listOf(Muscle.ARM, Muscle.CHEST)),
    SHOULDER_TAPS("shoulder taps", listOf(Muscle.ARM, Muscle.CHEST, Muscle.BACK, Muscle.ABS)),
    PUSHUP_FALLING("falling push up", listOf(Muscle.ARM, Muscle.CHEST)),
    PUSHUP_TIGHT("pushup with tight hands", listOf(Muscle.ARM, Muscle.CHEST)),
    PUSHUP_WIDE("pushup with wide hands", listOf(Muscle.ARM, Muscle.CHEST)),
    PUSHUP_TO_PLANK("pushup to plank", listOf(Muscle.ARM, Muscle.BACK, Muscle.ABS, Muscle.CHEST)),
    MILITARY("military press", listOf(Muscle.ARM, Muscle.CHEST)),
    SQUAD("squads", listOf(Muscle.LEG)),
    PLANK("hold plank position", listOf(Muscle.BACK, Muscle.ABS)),
    PLANK_SIDE("hold side plank position", listOf(Muscle.BACK, Muscle.ABS)),
    LUNGES_FORWARD("lunges forward", listOf(Muscle.LEG)),
    LUNGES_BACKWARD("lunges backward", listOf(Muscle.LEG)),
    LUNGES_ONE_LEG("lunges on one leg", listOf(Muscle.LEG, Muscle.BACK)),
    LEG_RISES("leg rises", listOf(Muscle.LEG, Muscle.ABS)),
    ABS_ROTATION("abs rotation", listOf(Muscle.ABS, Muscle.BACK)),
}