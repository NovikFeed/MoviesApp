package programmer.movie_application.movieList.util
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class TypeConverter {

    @TypeConverter
    fun fromListToJson(value: List<Int>?): String? {
        return Gson().toJson(value)
    }
    @TypeConverter
    fun fromJsonToList(value: String?): List<Int>? {
        val listType = object : TypeToken<List<Int>>() {}.type
        return Gson().fromJson(value, listType)
    }
}