package programmer.movie_application.core.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import programmer.movie_application.core.presentation.components.MovieCard
import programmer.movie_application.movieList.presentation.MovieListState
import programmer.movie_application.movieList.presentation.MovieListUiEvents
import programmer.movie_application.movieList.util.Category

@Composable
fun UpcomingMovieScreen(
    navController : NavHostController,
    movieStateList : MovieListState,
    onEvent : (MovieListUiEvents) -> Unit,
) {
    if(movieStateList.upcomingMovieList.isEmpty()){
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center,
        ){
            CircularProgressIndicator()
        }
    }
    else{
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(vertical = 8.dp, horizontal = 4.dp),
        ){
            items(movieStateList.upcomingMovieList.size){index ->
                MovieCard(
                    movie = movieStateList.upcomingMovieList[index],
                    navController = navController)
                Spacer(modifier = Modifier.height(16.dp))
                if(index >= movieStateList.upcomingMovieList.size - 1 && !movieStateList.isLoading){
                    onEvent(MovieListUiEvents.Paginate(Category.UPCOMING))
                }
            }
        }
    }
}