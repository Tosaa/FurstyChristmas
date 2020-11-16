package com.example.furstychrismas.model

import com.example.furstychrismas.R

enum class Exercise(
    val exerciseName: String,
    val muscles: List<Muscle>,
    val descriptionId: Int = R.string.lorem_ipsum
) {
    //CHEST
    PUSHUP("Liegestütz", listOf(Muscle.ARM, Muscle.CHEST), R.string.push_up),
    PUSHUP_ONE_SIDED("Liegestütz mit erhöhter Hand", listOf(Muscle.ARM, Muscle.CHEST),R.string.push_up_one_sided),
    SHOULDER_TAPS("Shoulder taps", listOf(Muscle.ARM, Muscle.CHEST, Muscle.BACK, Muscle.ABS),R.string.shoulder_taps),
    PUSHUP_FALLING("Fallender Liegestütz", listOf(Muscle.ARM, Muscle.CHEST),R.string.pushup_falling),
    PUSHUP_TIGHT("Liegestütz mit engen Händen", listOf(Muscle.ARM, Muscle.CHEST),R.string.pushup_tight),
    PUSHUP_WIDE("Liegestütz mit weiten Händen", listOf(Muscle.ARM, Muscle.CHEST),R.string.pushup_wide),
    PUSHUP_TO_PLANK("Liegestütz zu Plank", listOf(Muscle.ARM, Muscle.BACK, Muscle.ABS, Muscle.CHEST),R.string.pushup_to_plank),
    MILITARY("Military press", listOf(Muscle.ARM, Muscle.CHEST),R.string.military),

    //LEGS
    SQUAD("Kniebeuge", listOf(Muscle.LEG),R.string.squad),
    LUNGES_FORWARD("Ausfallschritt vorwärts", listOf(Muscle.LEG),R.string.lunges_forward),
    LUNGES_BACKWARD("Ausfallschritt rückwärts", listOf(Muscle.LEG),R.string.lunges_backward),
    SQUAD_ONE_LEG("Einbeinige Kniebeuge", listOf(Muscle.LEG, Muscle.BACK),R.string.squad_one_leg),
    DEADLIFT_ONE_LEG("Einbeinige Standwaage", listOf(Muscle.LEG, Muscle.BACK),R.string.deadlift_one_leg),

    //CORE
    PLANK("Plank position halten", listOf(Muscle.BACK, Muscle.ABS),R.string.plank),
    PLANK_SIDE("Seitliche plank position halten", listOf(Muscle.BACK, Muscle.ABS),R.string.plank_side),
    LEG_RISES("Beinheben", listOf(Muscle.LEG, Muscle.ABS),R.string.leg_rises),
    ABS_ROTATION("Russian twist", listOf(Muscle.ABS, Muscle.BACK),R.string.abs_rotation),
    FLUTTER_KICKS("Flutter kicks",listOf(Muscle.ABS),R.string.flutter_kicks),
    BICYCLE("Bicycle",listOf(Muscle.ABS),R.string.bicycle),
    SIT_UP("Sit ups",listOf(Muscle.ABS),R.string.sit_up),
    KNEE_TO_ELBOW("Knee to elbow",listOf(Muscle.ABS),R.string.knee_to_elbow),
    LEG_RISED_CIRCLE("Beinheben mit kreisender Bewegung",listOf(Muscle.ABS),R.string.leg_rised_circle)
}