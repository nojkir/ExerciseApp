package pl.nojkir.roomdatabase.data.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import pl.nojkir.roomdatabase.data.converters.Converters


@Entity (tableName = "trainings")
data class Training (

    @ColumnInfo (name = "training_name")
    var trainingName : String,

    @ColumnInfo (name = "exercises_list")
    var exercisesList : List<Exercise>


) {
    @PrimaryKey(autoGenerate = true)
    var id : Int = 0
}