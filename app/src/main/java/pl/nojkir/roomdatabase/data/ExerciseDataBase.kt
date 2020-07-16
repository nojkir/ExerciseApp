package pl.nojkir.roomdatabase.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import pl.nojkir.roomdatabase.data.db.entities.Exercise

@Database(
    entities = [Exercise::class],
    version = 1
)
abstract class ExerciseDataBase : RoomDatabase() {

    abstract fun getExerciseDao(): ExerciseDao

    companion object{
        @Volatile
        private var instance: ExerciseDataBase? = null
        private val LOCK = Any()

        operator fun invoke (context: Context ) = instance
            ?: synchronized(LOCK){
            instance
                ?: createDatabase(
                    context
                )
                .also { instance = it }
        }

        private fun createDatabase (context: Context) =
            Room.databaseBuilder(context.applicationContext,
            ExerciseDataBase::class.java, "exercise_database")
                .build()
    }


}