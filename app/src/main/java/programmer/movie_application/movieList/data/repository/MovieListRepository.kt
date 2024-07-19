package programmer.movie_application.movieList.data.repository

import programmer.movie_application.movieList.data.local.movie.MovieDatabase
import programmer.movie_application.movieList.data.mappers.toMovie
import programmer.movie_application.movieList.data.mappers.toMovieDBO
import programmer.movie_application.movieList.data.remote.MovieAPI
import programmer.movie_application.movieList.data.remote.respond.MovieDTO
import programmer.movie_application.movieList.domain.model.Movie
import programmer.movie_application.movieList.domain.repository.MovieRepositoryInterface
import programmer.movie_application.movieList.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class MovieListRepository @Inject constructor(
    private val movieApi : MovieAPI,
    private val movieDatabase: MovieDatabase
): MovieRepositoryInterface {
    override suspend fun getMovieList(
        forceFetchFromRemote: Boolean,
        category: String,
        page: Int
    ): Flow<Resource<List<Movie>>> {
        return flow {
            emit(Resource.Loading(true))
            val data = movieDatabase.movieDAO.getMoviesByCategory(category)
            val result  = data.isNotEmpty() && !forceFetchFromRemote
            if(result){
                emit(Resource.Success(data.map { it.toMovie(category) }))
                emit(Resource.Loading(false))
                return@flow
            }
            emit(Resource.Loading(true))
            val requestToApi= try {
                movieApi.getMoviesList(category, page)
            }
            catch (e : IOException){
                e.printStackTrace()
                emit(Resource.Error(message = "Error loading movies"))
                return@flow
            }
            catch (e : HttpException){
                e.printStackTrace()
                emit(Resource.Error(message = "Error loading movies"))
                return@flow
            }
            catch (e : Exception){
                e.printStackTrace()
                emit(Resource.Error(message = "Error loading movies"))
                return@flow
            }

            val movieListFromAPI = requestToApi.results.map {
                it.toMovieDBO(category)
            }
            movieDatabase.movieDAO.upsertMovieList(movieListFromAPI)
            emit(Resource.Success(movieListFromAPI.map{it.toMovie(category)}))
        }
    }

    override suspend fun getMovie(id: Int): Flow<Resource<Movie>> {
        return flow {
            emit(Resource.Loading(true))
            val movieDBO = movieDatabase.movieDAO.getMoviesById(id)
            if(movieDBO != null){
                emit(Resource.Success(movieDBO.toMovie(movieDBO.category)))
                emit(Resource.Loading(false))
                return@flow
            }
            else{
                emit(Resource.Error(message = "Error no such movie"))
                emit(Resource.Loading(false))
                return@flow
            }
        }
    }
}
