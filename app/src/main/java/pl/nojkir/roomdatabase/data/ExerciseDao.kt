package pl.nojkir.roomdatabase.data

import androidx.lifecycle.LiveData
import androidx.room.*
import pl.nojkir.roomdatabase.data.converters.Converters
import pl.nojkir.roomdatabase.data.db.entities.Exercise

@Dao
@TypeConverters(Converters::class)
interface ExerciseDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(vararg exercise: Exercise)

    @Delete
    suspend fun delete( vararg exercise: Exercise)


    @Query("SELECT * FROM training_table")
     fun getAllExercise(): LiveData<List<Exercise>>
}

