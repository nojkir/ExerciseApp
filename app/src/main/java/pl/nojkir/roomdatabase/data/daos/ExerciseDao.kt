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

    @Query("DELETE FROM exercises_table WHERE exercise_name = :trainingName")
    suspend fun deleteByName(trainingName: String)


    @Query("SELECT * FROM exercises_table")
    fun getAllExercise(): LiveData<List<Exercise>>


    @Query("SELECT * FROM exercises_table WHERE training_id = :trainingId")
    fun findExercisesByTrainingId(trainingId: Int): LiveData<List<Exercise>>


}

