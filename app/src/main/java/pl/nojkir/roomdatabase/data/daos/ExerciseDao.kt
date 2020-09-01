package pl.nojkir.roomdatabase.data.daos

import androidx.lifecycle.LiveData
import androidx.room.*
import pl.nojkir.roomdatabase.data.db.entities.Exercise

@Dao

interface ExerciseDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(vararg exercise: Exercise)

    @Delete
    suspend fun delete(vararg exercise: Exercise)

    @Query("DELETE FROM training_table WHERE training_name = :trainingName")
    suspend fun deleteByName(trainingName: String)


    @Query("SELECT * FROM training_table")
    fun getAllExercise(): LiveData<List<Exercise>>

    @Query("SELECT training_name from training_table")
    fun getAllNames(): LiveData<List<String>>

    @Query("SELECT * FROM training_table WHERE training_name = :trainingName")
    fun findExercisesByTrainingName(trainingName: String): LiveData<List<Exercise>>


}

