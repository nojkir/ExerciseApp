package pl.nojkir.roomdatabase.data.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "trainings_table")
data class Training (

    @ColumnInfo(name = "training_name")
    var trainingName: String)

{

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}