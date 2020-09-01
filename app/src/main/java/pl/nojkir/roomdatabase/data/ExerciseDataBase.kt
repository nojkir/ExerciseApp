package pl.nojkir.roomdatabase.data

import androidx.room.Database
import androidx.room.RoomDatabase
import pl.nojkir.roomdatabase.data.daos.ExerciseDao
import pl.nojkir.roomdatabase.data.db.entities.Exercise


@Database(
    entities = [Exercise::class],
    version = 1

)
abstract class ExerciseDataBase : RoomDatabase() {

    abstract fun getExerciseDao(): ExerciseDao



}