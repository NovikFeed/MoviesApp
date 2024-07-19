package programmer.movie_application.movieList.presentation

sealed interface MovieListUiEvents {
    data class Paginate(val category: String): MovieListUiEvents
    object Navigate : MovieListUiEvents
}