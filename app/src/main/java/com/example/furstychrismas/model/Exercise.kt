package com.example.furstychrismas.model

import com.example.furstychrismas.R

enum class Exercise(
    val exerciseName: String,
    val muscles: List<Muscle>,
    val startPositionId: Int = R.string.lorem_ipsum,
    val repetitionId : Int = R.string.lorem_ipsum
) {
    //CHEST
    PUSHUP("Liegestütz", listOf(Muscle.ARM, Muscle.CHEST), R.string.push_up_start_position,R.string.push_up_repetition),
    PUSHUP_ONE_SIDED("Liegestütz mit erhöhter Hand", listOf(Muscle.ARM, Muscle.CHEST),R.string.push_up_one_sided_start_position,R.string.push_up_one_sided_repetition),
    SHOULDER_TAPS("Shoulder taps", listOf(Muscle.ARM, Muscle.CHEST, Muscle.BACK, Muscle.ABS),R.string.shoulder_taps_start_position,R.string.shoulder_taps_repetition),
    PUSHUP_FALLING("Fallender Liegestütz", listOf(Muscle.ARM, Muscle.CHEST),R.string.pushup_falling_start_position,R.string.pushup_falling_repetition),
    PUSHUP_TIGHT("Liegestütz mit engen Händen", listOf(Muscle.ARM, Muscle.CHEST),R.string.pushup_tight_start_position,R.string.pushup_tight_repetition),
    PUSHUP_WIDE("Liegestütz mit weiten Händen", listOf(Muscle.ARM, Muscle.CHEST),R.string.pushup_wide_start_position,R.string.pushup_wide_repetition),
    PUSHUP_TO_PLANK("Liegestütz zu Plank", listOf(Muscle.ARM, Muscle.BACK, Muscle.ABS, Muscle.CHEST),R.string.pushup_to_plank_start_position,R.string.pushup_to_plank_repetition),
    MILITARY("Military press", listOf(Muscle.ARM, Muscle.CHEST),R.string.military_start_position,R.string.military_repetition),

    //LEGS
    SQUAD("Kniebeuge", listOf(Muscle.LEG),R.string.squad_start_position,R.string.squad_repetition),
    LUNGES_FORWARD("Ausfallschritt vorwärts", listOf(Muscle.LEG),R.string.lunges_forward_start_position,R.string.lunges_forward_repetition),
    LUNGES_BACKWARD("Ausfallschritt rückwärts", listOf(Muscle.LEG),R.string.lunges_backward_start_position,R.string.lunges_backward_repetition),
    SQUAD_ONE_LEG("Einbeinige Kniebeuge", listOf(Muscle.LEG, Muscle.BACK),R.string.squad_one_leg_start_position,R.string.squad_one_leg_repetition),
    DEADLIFT_ONE_LEG("Einbeinige Standwaage", listOf(Muscle.LEG, Muscle.BACK),R.string.deadlift_one_leg_start_position,R.string.deadlift_one_leg_repetition),

    //CORE
    PLANK("Plank position halten", listOf(Muscle.BACK, Muscle.ABS),R.string.plank_start_position,R.string.plank_repetition),
    PLANK_SIDE(
        "Seitliche plank position halten",
        listOf(Muscle.BACK, Muscle.ABS),
        R.string.plank_side_start_position,
        R.string.plank_side_repetition
    ),
    LEG_RISES(
        "Beinheben",
        listOf(Muscle.LEG, Muscle.ABS),
        R.string.leg_rises_start_position,
        R.string.leg_rises_repetition
    ),
    ABS_ROTATION(
        "Russian twist",
        listOf(Muscle.ABS, Muscle.BACK),
        R.string.abs_rotation_start_position,
        R.string.abs_rotation_repetition
    ),
    FLUTTER_KICKS(
        "Flutter kicks",
        listOf(Muscle.ABS),
        R.string.flutter_kicks_start_position,
        R.string.flutter_kicks_repetition
    ),
    BICYCLE(
        "Bicycle",
        listOf(Muscle.ABS),
        R.string.bicycle_start_position,
        R.string.bicycle_repetition
    ),
    SIT_UP(
        "Sit ups",
        listOf(Muscle.ABS),
        R.string.sit_up_start_position,
        R.string.sit_up_repetition
    ),
    KNEE_TO_ELBOW(
        "Knee to elbow",
        listOf(Muscle.ABS),
        R.string.knee_to_elbow_start_position,
        R.string.knee_to_elbow_repetition
    ),
    LEG_RISED_CIRCLE(
        "Beinheben mit kreisender Bewegung",
        listOf(Muscle.ABS),
        R.string.leg_rised_circle_start_position,
        R.string.leg_rised_circle_repetition
    ),

    //STRETCH
    LEG_STRETCH(
        "Beindehnung offen",
        listOf(Muscle.BACK, Muscle.LEG, Muscle.FLEXIBILITY),
        R.string.leg_stretch_start_position,
        R.string.leg_stretch_repetition
    ),
    LEG_STRETCH_CLOSED(
        "Beindehnung geschlossen",
        listOf(Muscle.BACK, Muscle.LEG, Muscle.FLEXIBILITY),
        R.string.leg_stretch_closed_start_position,
        R.string.leg_stretch_closed_repetition
    ),
    FEMUR_STRETCH(
        "Oberschenkeldehnung",
        listOf(Muscle.LEG, Muscle.FLEXIBILITY),
        R.string.femur_stretch_start_position,
        R.string.femur_stretch_repetition
    ),
    BACK_STRETCH(
        "Rückendehnung",
        listOf(Muscle.BACK, Muscle.ARM, Muscle.FLEXIBILITY),
        R.string.back_stretch_start_position,
        R.string.back_stretch_repetition
    ),
    COBRA_STRETCH(
        "Cobra stretch",
        listOf(Muscle.BACK, Muscle.ARM, Muscle.FLEXIBILITY),
        R.string.cobra_stretch_start_position,
        R.string.cobra_stretch_repetition
    ),
    ARM_ROTATION(
        "Arm Kreisen",
        listOf(Muscle.ARM, Muscle.FLEXIBILITY),
        R.string.arm_rotation_start_position,
        R.string.arm_rotation_repetition
    )
}