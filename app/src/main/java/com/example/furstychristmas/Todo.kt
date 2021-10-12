package com.example.furstychristmas

// Todo: create module for Logger:
/*
* Add Interface
* Add Impl. of Timber
*/

// Todo: separate Repository Functionality into Use-Cases
/*
* AchievementRepository
* is Empty and unused right now
* Think about to remove or impl. it.
*
* CardRepository
* Rename Card to WorkoutAtDay
* Create WorkoutAtDayUseCase (t.b.d)
* Read WorkoutAtDayUseCase (Needed)
* --> Workouts should be selected by Year. (input: localdate (today); output: list of workouts; extra: on January the workouts of last year should be given)
* Update WorkoutAtDayUseCase (Needed)
* Delete WorkoutAtDayUsecase (t.b.d.)
*
* WorkoutRepository
* CreateWorkoutUseCase (Not needed, in app no workouts can be added)
* ReadWorkoutUseCase (Needed)
* Update (Not needed, see above)
* Delete (Not needed, see above)
* */

// Todo: save information of workouts as real json.
/*
* The current format is only a string with a custom format for the string. That is not usefull!
* */

// Todo: save texts for Excercise description and title in a json format.
/*
* to increase editability and readability the strings should be saved in a Json format.
* To parse the Json to the strings.xml a small pyhton/kotlin script can be used. (needs to be written)
* */