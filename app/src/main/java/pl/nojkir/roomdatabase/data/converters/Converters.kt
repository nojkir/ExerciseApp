package pl.nojkir.roomdatabase.data.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import pl.nojkir.roomdatabase.data.db.entities.Exercise

 class Converters {
    @TypeConverter
     fun fromString(value: String?): List<Exercise> {
        val listType =
            object : TypeToken<List<String?>?>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
     fun fromList(list: List<Exercise?>?): String {
        val gson = Gson()
        return gson.toJson(list)
    }
}