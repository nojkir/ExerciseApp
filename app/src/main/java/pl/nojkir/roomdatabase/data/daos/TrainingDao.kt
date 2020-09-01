package pl.nojkir.roomdatabase.data.daos

import androidx.lifecycle.LiveData
import androidx.room.*
import pl.nojkir.roomdatabase.data.db.entities.Training


@Dao
interface TrainingDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(vararg training: Training)

    @Delete
    suspend fun delete(vararg training: Training)

    @Query ("SELECT * FROM trainings_table")
    fun getAllTrainings(): LiveData<List<Training>>


}