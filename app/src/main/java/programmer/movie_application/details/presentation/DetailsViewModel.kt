package programmer.movie_application.details.presentation

import androidx.compose.runtime.collectAsState
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import programmer.movie_application.movieList.data.repository.MovieListRepository
import programmer.movie_application.movieList.util.Resource
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val movieListRepository: MovieListRepository,
    private val savedStateHandle : SavedStateHandle
) : ViewModel(){

    private val movieId = savedStateHandle.get<Int>("movieId")
    private var _detailsState = MutableStateFlow(StateDetails())
    val details = _detailsState.asStateFlow()

    init {
        getMovie(movieId ?: 1)
    }

    private fun getMovie(movieId: Int) {
        viewModelScope.launch {
            _detailsState.update { it.copy(isLoading = true) }
            movieListRepository.getMovie(movieId).collectLatest { resultState ->
                when(resultState){
                   is Resource.Error -> {
                       _detailsState.update { it.copy(isLoading = false) }
                   }
                    is Resource.Loading -> {
                        _detailsState.update { it.copy(isLoading = resultState.isLoading) }
                    }
                    is Resource.Success -> {
                        resultState.data?.let {movie ->
                            _detailsState.update { it.copy(movie = movie) }
                        }
                    }
                }
            }
        }
    }
}