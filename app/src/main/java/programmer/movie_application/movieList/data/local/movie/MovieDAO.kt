package programmer.movie_application.movieList.data.local.movie

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface MovieDAO {
    @Upsert
    suspend fun upsertMovieList(movieList: List<MovieDBO>)
    @Upsert
    suspend fun upsertMovieToFavouriteList(movie : MovieDBO)

    @Query("SELECT * FROM MovieDBO WHERE id = :id")
    suspend fun getMoviesById(id : Int): MovieDBO

    @Query("SELECT * FROM MovieDBO WHERE category = :category")
    suspend fun getMoviesByCategory(category: String): List<MovieDBO>
}