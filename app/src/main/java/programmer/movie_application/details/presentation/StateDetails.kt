package programmer.movie_application.details.presentation

import programmer.movie_application.movieList.domain.model.Movie

data class StateDetails(
    val isLoading : Boolean = false,
    val movie : Movie? = null
) {
}