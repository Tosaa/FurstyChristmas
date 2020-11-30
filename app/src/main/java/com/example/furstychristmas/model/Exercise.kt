package com.example.furstychristmas.model

import com.example.furstychristmas.R

enum class Exercise(
    val exerciseName: String,
    val muscles: List<Muscle>,
    val startPositionId: Int = R.string.empty_string,
    val repetitionId: Int = R.string.empty_string
) {
    //CHEST
    PUSHUP(
        "Liegestütz",
        listOf(Muscle.ARM, Muscle.CHEST),
        R.string.push_up_start_position,
        R.string.push_up_repetition
    ),
    PUSHUP_ONE_SIDED(
        "Liegestütz mit erhöhter Hand",
        listOf(Muscle.ARM, Muscle.CHEST),
        R.string.push_up_one_sided_start_position,
        R.string.push_up_one_sided_repetition
    ),
    SHOULDER_TAPS(
        "Shoulder taps",
        listOf(Muscle.ARM, Muscle.CHEST, Muscle.BACK, Muscle.ABS),
        R.string.shoulder_taps_start_position,
        R.string.shoulder_taps_repetition
    ),
    PUSHUP_FALLING(
        "Fallender Liegestütz",
        listOf(Muscle.ARM, Muscle.CHEST),
        R.string.pushup_falling_start_position,
        R.string.pushup_falling_repetition
    ),
    PUSHUP_TIGHT(
        "Liegestütz mit engen Händen",
        listOf(Muscle.ARM, Muscle.CHEST),
        R.string.pushup_tight_start_position,
        R.string.pushup_tight_repetition
    ),
    PUSHUP_WIDE(
        "Liegestütz mit weiten Händen",
        listOf(Muscle.ARM, Muscle.CHEST),
        R.string.pushup_wide_start_position,
        R.string.pushup_wide_repetition
    ),
    PUSHUP_TO_PLANK(
        "Liegestütz zu Plank",
        listOf(Muscle.ARM, Muscle.BACK, Muscle.ABS, Muscle.CHEST),
        R.string.pushup_to_plank_start_position,
        R.string.pushup_to_plank_repetition
    ),
    MILITARY(
        "Military press",
        listOf(Muscle.ARM, Muscle.CHEST),
        R.string.military_start_position,
        R.string.military_repetition
    ),

    //LEGS
    SQUAD(
        "Kniebeuge",
        listOf(Muscle.LEG),
        R.string.squad_start_position,
        R.string.squad_repetition
    ),
    LUNGES_FORWARD(
        "Ausfallschritt vorwärts",
        listOf(Muscle.LEG),
        R.string.lunges_forward_start_position,
        R.string.lunges_forward_repetition
    ),
    LUNGES_BACKWARD(
        "Ausfallschritt rückwärts",
        listOf(Muscle.LEG),
        R.string.lunges_backward_start_position,
        R.string.lunges_backward_repetition
    ),
    SQUAD_ONE_LEG(
        "Einbeinige Kniebeuge",
        listOf(Muscle.LEG, Muscle.BACK),
        R.string.squad_one_leg_start_position,
        R.string.squad_one_leg_repetition
    ),
    DEADLIFT_ONE_LEG(
        "Einbeinige Standwaage",
        listOf(Muscle.LEG, Muscle.BACK),
        R.string.deadlift_one_leg_start_position,
        R.string.deadlift_one_leg_repetition
    ),

    //CORE
    PLANK(
        "Plank position halten",
        listOf(Muscle.BACK, Muscle.ABS),
        R.string.plank_start_position,
        R.string.plank_repetition
    ),
    PLANK_SIDE(
        "Seitliche Plank position halten",
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
        listOf(Muscle.BACK, Muscle.LEG),
        R.string.leg_stretch_start_position,
        R.string.leg_stretch_repetition
    ),
    LEG_STRETCH_CLOSED(
        "Beindehnung geschlossen",
        listOf(Muscle.BACK, Muscle.LEG),
        R.string.leg_stretch_closed_start_position,
        R.string.leg_stretch_closed_repetition
    ),
    FEMUR_STRETCH(
        "Oberschenkeldehnung",
        listOf(Muscle.LEG),
        R.string.femur_stretch_start_position,
        R.string.femur_stretch_repetition
    ),
    BACK_STRETCH(
        "Rückendehnung",
        listOf(Muscle.BACK, Muscle.ARM),
        R.string.back_stretch_start_position,
        R.string.back_stretch_repetition
    ),
    COBRA_STRETCH(
        "Cobra stretch",
        listOf(Muscle.BACK, Muscle.ARM),
        R.string.cobra_stretch_start_position,
        R.string.cobra_stretch_repetition
    ),
    ARM_ROTATION(
        "Arm Kreisen",
        listOf(Muscle.ARM),
        R.string.arm_rotation_start_position,
        R.string.arm_rotation_repetition
    ),
    BREAK("Pause", listOf(Muscle.BREAK)),
    BURPEES("Burpees", listOf(Muscle.ALL), R.string.cheer, R.string.cheer),
    HANDS_OPEN_CLOSE("Hände auf und zu", listOf(Muscle.ALL), R.string.cheer, R.string.cheer),
    CRUNCH("Crunch", listOf(Muscle.ALL), R.string.cheer, R.string.cheer),
    MOUNTAIN_CLIMBER("Mountain climber", listOf(Muscle.ALL), R.string.cheer, R.string.cheer),
    PLANK_JUMPING_JACK("Plank jumping jack", listOf(Muscle.ALL), R.string.cheer, R.string.cheer),
    TOE_TOUCH_CROSSED("Toe touch über Kreuz", listOf(Muscle.ALL), R.string.cheer, R.string.cheer),
    HIP_EXTENSION("Hip Extension einbeinig", listOf(Muscle.ALL), R.string.cheer, R.string.cheer),
    STRETCH("Dehnen", listOf(Muscle.ALL), R.string.cheer, R.string.cheer),
    STRADDLE_1("Gerkrätscht sitzen", listOf(Muscle.ALL), R.string.straddle_1_start_position, R.string.straddle_1_repetition),
    HOLLOW_1("Hollowposition bis 4", listOf(Muscle.ALL), R.string.hollow_1_start_position, R.string.hollow_1_repetition),
    HOLLOW_2("Hollowposition bis zur zweiten 4", listOf(Muscle.ALL), R.string.hollow_2_start_position, R.string.hollow_2_repetition),
    HURDLE_SEAT("Hürdensitz", listOf(Muscle.ALL), R.string.hurdle_seat_start_position, R.string.hurdle_seat_repetition),
    STRADLE_2("Gerkrätscht sitzen", listOf(Muscle.ALL), R.string.straddle_2_start_position, R.string.straddle_2_repetition),
    STRETCH_AFTER_STRADDLE("Dehnen nach Spagat", listOf(Muscle.ALL), R.string.empty_string, R.string.stretch_after_straddle)
}