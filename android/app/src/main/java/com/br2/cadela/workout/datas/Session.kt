package com.br2.cadela.workout.datas

import java.time.LocalDate

data class Session(
    val name: String,
    val exercises: List<Exercise>,
    val levelStartedAt: LocalDate = LocalDate.now()
) {
    val estimatedTimeInSec: Long
        get() = exercises.foldIndexed(0, { index, acc, it ->
            val totalRestBetweenSeries = (it.series.count - 1) * it.series.restAfter.duration
            val totalRestBetweenExercise = if(index < exercises.size - 1)  (it.restAfter?.duration ?: 0) else 0
            val estimatedExerciseTime = Series.ESTIMATED_TIME_FOR_ONE_SERIE_IN_SEC * it.series.count
            acc + totalRestBetweenSeries + totalRestBetweenExercise + estimatedExerciseTime
        })

    val notStarted: Boolean
        get() = exercises.flatMap { it.series.repetitions }.all { it.done == 0 }

    val isComplete: Boolean
        get() = exercises.map { it.series.repetitions.any { rep -> rep.done > 0 } }.all { it }

    fun clone(newStartDate: LocalDate? = null): Session =
        Session(name = name, exercises = exercises, levelStartedAt = newStartDate ?: levelStartedAt)

    companion object {
        val FIRST_LEVEL_TEST = Session(
            name = "first_level_test",
            exercises = listOf(
                Exercise(name = "A", series = Series(2), restAfter = Rest(5)),
                Exercise(name = "B", series = Series(1), restAfter = Rest(5)),
                Exercise(name = "C", series = Series(1), restAfter = Rest(180)),
                Exercise(name = "A1", series = Series(1), restAfter = null)
            )
        )
        val FIRST_PROGRAM = Session(
            name = "first_program",
            exercises = listOf(
                Exercise(name = "A1", series = Series(2), restAfter = Rest(120)),
                Exercise(name = "D", series = Series(2), restAfter = Rest(120)),
                Exercise(name = "C1", series = Series(2), restAfter = Rest(120), speed = ESpeed.FAST),
                Exercise(name = "E", series = Series(2), restAfter = Rest(120)),
                Exercise(name = "F", series = Series(2), restAfter = Rest(120)),
                Exercise(name = "G", series = Series(2), restAfter = Rest(120)),
                Exercise(name = "K2", series = Series(2), restAfter = null)
            )
        )

        val FIRST_PROGRAM_WITH_C4 = Session(
            name = "first_program",
            exercises = listOf(
                Exercise(name = "A1", series = Series(1), restAfter = Rest(120)),
                Exercise(name = "D", series = Series(1), restAfter = Rest(120)),
                Exercise(name = "C4", series = Series(1), restAfter = Rest(120)),
                Exercise(name = "E", series = Series(1), restAfter = Rest(120)),
                Exercise(name = "F", series = Series(1), restAfter = Rest(120)),
                Exercise(name = "G", series = Series(1), restAfter = Rest(120)),
                Exercise(name = "K2", series = Series(1), restAfter = null)
            )
        )

        val ONLY_B_TEST = Session(
            name = "only_b_test",
            exercises = listOf(
                Exercise(name = "B", series = Series(1), restAfter = null),
            )
        )

        val FIRST_PROGRAM_WITH_B1 = Session(
            name = "first_program",
            exercises = listOf(
                Exercise(name = "B1", series = Series(3), restAfter = Rest(duration = 120)),
                Exercise(name = "A6", series = Series(3), restAfter = Rest(duration = 120)),
                Exercise(name = "A2", series = Series(3), restAfter = Rest(duration = 120)),
                Exercise(name = "D", series = Series(3), restAfter = Rest(duration = 120)),
                Exercise(name = "C1", series = Series(3), restAfter = Rest(duration = 120)),
                Exercise(name = "E", series = Series(3), restAfter = Rest(duration = 120)),
                Exercise(name = "F", series = Series(3), restAfter = Rest(duration = 120)),
                Exercise(name = "G", series = Series(3), restAfter = Rest(duration = 120)),
                Exercise(name = "K2", series = Series(3), restAfter = Rest(duration = 120))
            )
        )

        val SECOND_PROGRAM = Session(
            name = "second_program",
            exercises = listOf(
                Exercise(name = "B", series = Series(3), restAfter = Rest(duration = 120)),
                Exercise(name = "A1", series = Series(3), restAfter = Rest(duration = 120)),
                Exercise(name = "D", series = Series(1), restAfter = Rest(duration = 120)),
                Exercise(name = "C1", series = Series(3), restAfter = Rest(duration = 120), speed = ESpeed.FAST),
                Exercise(name = "E", series = Series(3), restAfter = Rest(duration = 120)),
                Exercise(name = "F", series = Series(3), restAfter = Rest(duration = 120)),
                Exercise(name = "G", series = Series(3), restAfter = Rest(duration = 120)),
                Exercise(name = "K2", series = Series(3), restAfter = null)
            )
        )

        val SECOND_PROGRAM_WITH_A2 = Session(
            name = "second_program",
            exercises = listOf(
                Exercise(name = "B", series = Series(3), restAfter = Rest(duration = 120)),
                Exercise(name = "A2", series = Series(3), restAfter = Rest(duration = 120)),
                Exercise(name = "D", series = Series(1), restAfter = Rest(duration = 120)),
                Exercise(name = "C1", series = Series(3), restAfter = Rest(duration = 120), speed = ESpeed.FAST),
                Exercise(name = "E", series = Series(3), restAfter = Rest(duration = 120)),
                Exercise(name = "F", series = Series(3), restAfter = Rest(duration = 120)),
                Exercise(name = "G", series = Series(3), restAfter = Rest(duration = 120)),
                Exercise(name = "K2", series = Series(3), restAfter = null)
            )
        )

        val SECOND_LEVEL = Session(
            name = "level_2",
            exercises = listOf(
                Exercise(name = "B1", series = Series(6), restAfter = Rest(duration = 25), speed = ESpeed.FAST),
                Exercise(name = "A3", series = Series(6), restAfter = Rest(duration = 25), speed = ESpeed.FAST),
                Exercise(name = "A2", series = Series(6), restAfter = Rest(duration = 180), speed = ESpeed.FAST),
                Exercise(name = "C1", series = Series(6), restAfter = Rest(duration = 180), speed = ESpeed.FAST),
                Exercise(name = "E", series = Series(6), restAfter = Rest(duration = 180)),
                Exercise(name = "F", series = Series(4), restAfter = Rest(duration = 180)),
                Exercise(name = "G", series = Series(6), restAfter = Rest(duration = 90)),
                Exercise(name = "H", series = Series(6), restAfter = Rest(duration = 60)),
                Exercise(name = "K2", series = Series(3), restAfter = null)
            )
        )
    }
}