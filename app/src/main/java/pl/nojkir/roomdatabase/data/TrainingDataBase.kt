package pl.nojkir.roomdatabase.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import pl.nojkir.roomdatabase.data.converters.Converters
import pl.nojkir.roomdatabase.data.db.entities.Training

@TypeConverters(Converters::class)
@Database(
    entities = [Training::class],
    version = 1
)

abstract class TrainingDataBase : RoomDatabase(){

    abstract fun getTrainingDao(): TrainingDao
}