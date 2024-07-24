package programmer.movie_application.movieList.presentation

import programmer.movie_application.movieList.domain.model.Movie

data class MovieListState (
    val isLoading : Boolean = false,
    val popularMovieListPage: Int = 1,
    val upcomingMovieListPage: Int = 1,
    val nowPlayingMovieListPage : Int = 1,
    val topRateMovieListPage : Int = 1,
    val popularMovieList : List<Movie> = emptyList(),
    val upcomingMovieList : List<Movie> = emptyList(),
    val nowPlayingMovieList : List<Movie> = emptyList(),
    val topRateMovieList : List<Movie> = emptyList(),
    val favouriteMovieList : List<Movie> = emptyList(),
    val currentScreenTitle : String = "Popular Movies",


    ){
}