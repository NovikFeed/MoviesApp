package programmer.movie_application.movieList.data.local.movie

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import programmer.movie_application.movieList.util.TypeConverter

@TypeConverters(TypeConverter::class)
@Database(entities = [MovieDBO::class],
    version = 1)
abstract class MovieDatabase : RoomDatabase() {
    abstract val movieDAO : MovieDAO
}