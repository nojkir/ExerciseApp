package pl.nojkir.roomdatabase.data

import androidx.room.Database
import androidx.room.RoomDatabase
import pl.nojkir.roomdatabase.data.daos.ExerciseDao
import pl.nojkir.roomdatabase.data.daos.TrainingDao
import pl.nojkir.roomdatabase.data.db.entities.Exercise
import pl.nojkir.roomdatabase.data.db.entities.Training


@Database(
    entities = [Exercise::class, Training::class],
    version = 1

)
abstract class ExerciseDataBase : RoomDatabase() {

    abstract fun getExerciseDao(): ExerciseDao
    abstract fun getTrainingDao() : TrainingDao



}