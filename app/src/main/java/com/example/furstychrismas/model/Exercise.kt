package com.example.furstychrismas.model

import com.example.furstychrismas.R

enum class Exercise(
    val exerciseName: String,
    val muscles: List<Muscle>,
    val descriptionId: Int = R.string.lorem_ipsum
) {
    //CHEST
    PUSHUP("push up", listOf(Muscle.ARM, Muscle.CHEST), R.string.push_up),
    PUSHUP_ONE_SIDED("push up with one hand up", listOf(Muscle.ARM, Muscle.CHEST),R.string.push_up_one_sided),
    SHOULDER_TAPS("shoulder taps", listOf(Muscle.ARM, Muscle.CHEST, Muscle.BACK, Muscle.ABS),R.string.shoulder_taps),
    PUSHUP_FALLING("falling push up", listOf(Muscle.ARM, Muscle.CHEST),R.string.pushup_falling),
    PUSHUP_TIGHT("pushup with tight hands", listOf(Muscle.ARM, Muscle.CHEST),R.string.pushup_tight),
    PUSHUP_WIDE("pushup with wide hands", listOf(Muscle.ARM, Muscle.CHEST),R.string.pushup_wide),
    PUSHUP_TO_PLANK("pushup to plank", listOf(Muscle.ARM, Muscle.BACK, Muscle.ABS, Muscle.CHEST),R.string.pushup_to_plank),
    MILITARY("military press", listOf(Muscle.ARM, Muscle.CHEST),R.string.military),

    //LEGS
    SQUAD("squads", listOf(Muscle.LEG),R.string.squad),
    LUNGES_FORWARD("lunges forward", listOf(Muscle.LEG),R.string.lunges_forward),
    LUNGES_BACKWARD("lunges backward", listOf(Muscle.LEG),R.string.lunges_backward),
    LUNGES_ONE_LEG("lunges on one leg", listOf(Muscle.LEG, Muscle.BACK),R.string.lunges_one_leg),

    //CORE
    PLANK("hold plank position", listOf(Muscle.BACK, Muscle.ABS),R.string.plank),
    PLANK_SIDE("hold side plank position", listOf(Muscle.BACK, Muscle.ABS),R.string.plank_side),
    LEG_RISES("leg rises", listOf(Muscle.LEG, Muscle.ABS),R.string.leg_rises),
    ABS_ROTATION("abs rotation", listOf(Muscle.ABS, Muscle.BACK),R.string.abs_rotation),
    FLUTTER_KICKS("flutter kicks",listOf(Muscle.ABS),R.string.flutter_kicks),
    BICYCLE("bicycle",listOf(Muscle.ABS),R.string.bicycle),
    SIT_UP("sit ups",listOf(Muscle.ABS),R.string.sit_up),
    KNEE_TO_ELBOW("knee to elbow",listOf(Muscle.ABS),R.string.knee_to_elbow),
    LEG_RISED_CIRCLE("leg rised circle",listOf(Muscle.ABS),R.string.leg_rised_circle)
}