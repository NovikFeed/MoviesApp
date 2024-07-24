package programmer.movie_application.movieList.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import programmer.movie_application.movieList.data.repository.MovieListRepository
import programmer.movie_application.movieList.util.Category
import programmer.movie_application.movieList.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(
    private val movieListRepository: MovieListRepository
) : ViewModel(){
    private var _movieListState = MutableStateFlow(MovieListState())
    val movieListState = _movieListState.asStateFlow()
    init {
        getPopularMovieList(false)
        getUpcomingMovieList(false)
        getNowPlayingMovies(false)
        getTopRatedMovies(false)
        getFavouriteMovieList()
    }

    fun onEvent(events: MovieListUiEvents, titleScreen : String = "Popular Movies"){
        when(events){
            is MovieListUiEvents.Navigate ->{
                _movieListState.update {
                    it.copy(currentScreenTitle = titleScreen)
                }
            }
            is MovieListUiEvents.Paginate ->{
                if (events.category == Category.POPULAR){
                    getPopularMovieList(true)
                }
                else if(events.category == Category.UPCOMING){
                    getUpcomingMovieList(true)
                }
                else if(events.category == Category.NOW_PLAYING){
                    getNowPlayingMovies(true)
                }
                else if(events.category == Category.TOP_RATED){
                    getTopRatedMovies(true)
                }
                else if(events.category == Category.FAVOURITE){
                    getFavouriteMovieList()
                }
            }

        }
    }

    private fun getUpcomingMovieList(forceFetchFromRemote : Boolean) {
        viewModelScope.launch {
            _movieListState.update {
                it.copy(isLoading = true)
            }
            movieListRepository.getMovieList(
                forceFetchFromRemote,
                Category.UPCOMING,
                movieListState.value.upcomingMovieListPage
            ).collectLatest { result ->
                when(result){
                    is Resource.Error -> _movieListState.update { it.copy(isLoading = false) }
                    is Resource.Loading -> _movieListState.update { it.copy(isLoading = result.isLoading) }
                    is Resource.Success -> {
                        result.data?.let {list ->
                            _movieListState.update { it.copy(
                                isLoading = false,
                                upcomingMovieList = movieListState.value.upcomingMovieList + list.shuffled(),
                                upcomingMovieListPage = movieListState.value.upcomingMovieListPage + 1
                            ) }
                        }
                    }
                }
            }
        }
    }

    private fun getPopularMovieList(forceFetchFromRemote : Boolean) {
        viewModelScope.launch {
            _movieListState.update {
                it.copy(isLoading = true)
            }
            movieListRepository.getMovieList(
                forceFetchFromRemote,
                Category.POPULAR,
                movieListState.value.popularMovieListPage
            ).collectLatest { result ->
                when(result){
                    is Resource.Error -> _movieListState.update { it.copy(isLoading = false) }
                    is Resource.Loading -> _movieListState.update { it.copy(isLoading = result.isLoading) }
                    is Resource.Success -> {
                        result.data?.let {list ->
                            _movieListState.update { it.copy(
                                isLoading = false,
                                popularMovieList = movieListState.value.popularMovieList + list.shuffled(),
                                popularMovieListPage = movieListState.value.popularMovieListPage + 1
                            ) }
                        }
                    }
                }
            }
        }
    }

    private fun getNowPlayingMovies(forceFetchFromRemote: Boolean){
        viewModelScope.launch {
            _movieListState.update { it.copy(isLoading = true) }
            movieListRepository.getMovieList(
                forceFetchFromRemote,
                Category.NOW_PLAYING,
                movieListState.value.nowPlayingMovieListPage).collectLatest {result ->
                when (result) {
                    is Resource.Error -> _movieListState.update { it.copy(isLoading = false) }
                    is Resource.Loading -> _movieListState.update { it.copy(isLoading = result.isLoading) }
                    is Resource.Success -> {
                        result.data?.let { list ->
                            _movieListState.update {
                                it.copy(
                                    isLoading = false,
                                    nowPlayingMovieList = movieListState.value.nowPlayingMovieList + list.shuffled(),
                                    nowPlayingMovieListPage = movieListState.value.nowPlayingMovieListPage + 1
                                )
                            }
                        }
                    }
                }
            }

        }
    }

    private fun getTopRatedMovies(forceFetchFromRemote : Boolean) {
        viewModelScope.launch {
            _movieListState.update {
                it.copy(isLoading = true)
            }
            movieListRepository.getMovieList(
                forceFetchFromRemote,
                Category.TOP_RATED,
                movieListState.value.topRateMovieListPage
            ).collectLatest { result ->
                when(result){
                    is Resource.Error -> _movieListState.update { it.copy(isLoading = false) }
                    is Resource.Loading -> _movieListState.update { it.copy(isLoading = result.isLoading) }
                    is Resource.Success -> {
                        result.data?.let {list ->
                            _movieListState.update { it.copy(
                                isLoading = false,
                                topRateMovieList = movieListState.value.topRateMovieList + list.shuffled(),
                                topRateMovieListPage = movieListState.value.topRateMovieListPage + 1
                            ) }
                        }
                    }
                }
            }
        }
    }

    private fun getFavouriteMovieList(){
        viewModelScope.launch {
            _movieListState.update{it.copy(isLoading = true)}
            movieListRepository.getMovieListFromDb(Category.FAVOURITE).collectLatest {list ->
                    _movieListState.update {
                        it.copy(
                            favouriteMovieList = list,
                            isLoading = false
                        )
                    }
            }
        }
    }

}