package pl.nojkir.roomdatabase.data.repositories

import pl.nojkir.roomdatabase.data.ExerciseDataBase
import pl.nojkir.roomdatabase.data.db.entities.Exercise

class ExerciseRepository(
    private val db: ExerciseDataBase
) {
    suspend fun upsert(exercise: Exercise) = db.getExerciseDao().upsert(exercise)
    suspend fun delete(exercise: Exercise) = db.getExerciseDao().delete(exercise)


     fun getAllExercise() = db.getExerciseDao().getAllExercise()
}


