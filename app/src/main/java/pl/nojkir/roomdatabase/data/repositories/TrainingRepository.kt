package pl.nojkir.roomdatabase.data.repositories

import pl.nojkir.roomdatabase.data.daos.TrainingDao
import pl.nojkir.roomdatabase.data.db.entities.Training
import javax.inject.Inject

class TrainingRepository @Inject constructor(
    private val dao: TrainingDao
) {

    suspend fun upsert(training: Training) = dao.upsert(training)
    suspend fun delete (training: Training) = dao.delete(training)

    fun getAllTraining() = dao.getAllTrainings()
}