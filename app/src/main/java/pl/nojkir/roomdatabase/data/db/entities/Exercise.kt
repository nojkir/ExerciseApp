package pl.nojkir.roomdatabase.data.db.entities

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.io.Serializable

@Entity(tableName = "training_table")

data class Exercise(

    @ColumnInfo(name = "training_name")
    var trainingName : String,
    @ColumnInfo(name = "exercise_name")
    var exerciseName : String,
    @ColumnInfo(name = "series_amount")
    var amountOfSeries : Int,
    @ColumnInfo(name = "weight")
    var weight : Double,
    @ColumnInfo(name = "reps")
    var reps : Int

) {

    @PrimaryKey(autoGenerate = true)
    var id : Int = 0
}