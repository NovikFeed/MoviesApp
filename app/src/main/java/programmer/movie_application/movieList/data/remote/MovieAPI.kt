package programmer.movie_application.movieList.data.remote

import programmer.movie_application.movieList.data.remote.respond.MoviesListDTO
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieAPI {
    @GET("movie/{category}")
    suspend fun getMoviesList(
        @Path("category") category: String,
        @Query("page") page : Int,
        @Query("api_key") apiKey : String = BASE_API_KEY
    ) : MoviesListDTO

    companion object{
        const val BASE_URL = "https://api.themoviedb.org/3/"
        const val BASE_API_KEY = "bd6ecc8610f5f1b6ef57f7d20a65e2bc"
        const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500"
    }
}