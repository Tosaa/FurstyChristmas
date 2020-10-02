package com.example.furstychrismas.model

enum class Excercise(
    val excerciseName: String,
    val muscles: List<Muscle>,
    val description: String = ""
) {
    PUSHUP("push up", listOf(Muscle.ARM, Muscle.CHEST))
}