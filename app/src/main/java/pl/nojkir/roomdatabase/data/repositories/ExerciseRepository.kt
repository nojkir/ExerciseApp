package pl.nojkir.roomdatabase.data.repositories

import pl.nojkir.roomdatabase.data.daos.ExerciseDao
import pl.nojkir.roomdatabase.data.db.entities.Exercise
import javax.inject.Inject

class ExerciseRepository @Inject constructor(
    private val db: ExerciseDao
) {
    suspend fun upsert(exercise: Exercise) = db.upsert(exercise)
    suspend fun delete(exercise: Exercise) = db.delete(exercise)

    suspend fun deleteByName(trainingName: String) = db.deleteByName(trainingName)

    fun getAllExercise() = db.getAllExercise()

    fun getAllNames() = db.getAllNames()

    fun findExercisesByTrainingName(trainingName: String) =
        db.findExercisesByTrainingName(trainingName)
}


