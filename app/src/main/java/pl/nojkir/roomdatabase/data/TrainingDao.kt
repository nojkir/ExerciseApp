package pl.nojkir.roomdatabase.data

import androidx.lifecycle.LiveData
import androidx.room.*
import pl.nojkir.roomdatabase.data.db.entities.Exercise
import pl.nojkir.roomdatabase.data.db.entities.Training


@Dao
interface TrainingDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun upsert(vararg training: Training)

    @Delete
    suspend fun delete( vararg training: Training)

    @Query("SELECT * FROM trainings")
    fun getAllExercise(): LiveData<List<Training>>

}