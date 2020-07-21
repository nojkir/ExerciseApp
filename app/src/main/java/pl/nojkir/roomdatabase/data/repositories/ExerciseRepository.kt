package pl.nojkir.roomdatabase.data.repositories

import pl.nojkir.roomdatabase.data.ExerciseDao
import pl.nojkir.roomdatabase.data.ExerciseDataBase
import pl.nojkir.roomdatabase.data.db.entities.Exercise
import javax.inject.Inject

class ExerciseRepository @Inject constructor(
    private val db: ExerciseDao
) {
    suspend fun upsert(exercise: Exercise) = db.upsert(exercise)
    suspend fun delete(exercise: Exercise) = db.delete(exercise)


     fun getAllExercise() = db.getAllExercise()
}


