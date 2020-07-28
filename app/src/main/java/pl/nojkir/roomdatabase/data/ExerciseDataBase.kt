package pl.nojkir.roomdatabase.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import pl.nojkir.roomdatabase.data.db.entities.Exercise

@Database(
    entities = [Exercise::class],
    version = 1,
    exportSchema = false
)
abstract class ExerciseDataBase : RoomDatabase() {

    abstract fun getExerciseDao(): ExerciseDao


}