package programmer.movie_application.movieList.domain.repository

import programmer.movie_application.movieList.domain.model.Movie
import programmer.movie_application.movieList.util.Resource
import kotlinx.coroutines.flow.Flow

interface MovieRepositoryInterface {
    suspend fun getMovieList(
        forceFetchFromRemote : Boolean,
        category : String,
        page : Int
    ) : Flow<Resource<List<Movie>>>

    suspend fun getMovie(id : Int) : Flow<Resource<Movie>>

    suspend fun upsertMovie(movie : Movie)

    suspend fun getMovieListFromDb(category: String) : Flow<List<Movie>>

    suspend fun removeMovieFromFavourite(movie: Movie)
}