package programmer.movie_application.core.presentation

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import programmer.movie_application.core.MainActivity
import programmer.movie_application.movieList.presentation.MovieListState
import programmer.movie_application.movieList.presentation.MovieListUiEvents
import programmer.movie_application.movieList.presentation.MovieListViewModel
import programmer.movie_application.movieNavigation.BottomItem
import programmer.movie_application.movieNavigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavHostController){
    val movieListViewModel = hiltViewModel<MovieListViewModel>()
    val movieState = movieListViewModel.movieListState.collectAsState().value
    val bottomNavController = rememberNavController()

    Scaffold(
        topBar = {
                 TopAppBar(
                     title = {
                     Text(text = movieState.currentScreenTitle)
                             },
                     actions = {
                         Icon(
                             modifier = Modifier
                                 .size(50.dp)
                                 .padding(end = 16.dp)
                                 .clickable(
                                     interactionSource = remember { MutableInteractionSource() },
                                     indication = null
                                 ) {
                                   bottomNavController.navigate(Screen.FavouriteMovieScreen.rout)
                                     movieListViewModel.onEvent(MovieListUiEvents.Navigate, "Favourite movies")
                                 },
                             imageVector = Icons.Default.FavoriteBorder,
                             contentDescription = "Favourite"
                         )
                     },
                     modifier = Modifier.shadow(2.dp),
                     colors = topAppBarColors(
                         MaterialTheme.colorScheme.inverseOnSurface
                     ))
        },
        bottomBar = { BottomBar(navController = bottomNavController, onEvent = movieListViewModel::onEvent)}
    ) {
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(it)){
            NavHost(
                navController = bottomNavController,
                startDestination = Screen.PopularMovieList.rout,
            ){
                composable(Screen.PopularMovieList.rout){
                    PopularMovieScreen(
                        navController = navController,
                        movieStateList = movieState,
                        onEvent = movieListViewModel::onEvent
                    )
                }
                composable(Screen.UpcomingMovieList.rout){
                    UpcomingMovieScreen(
                        navController = navController,
                        movieStateList = movieState,
                        onEvent = movieListViewModel::onEvent
                    )
                }
                composable(Screen.TopRatedMovieList.rout){
                    TopRatedMovieScreen(
                        navController = navController,
                        movieStateList = movieState,
                        onEvent = movieListViewModel::onEvent
                    )
                }
                composable(Screen.NowPlayingMovieList.rout){
                    NowPlayingMovieScreen(
                        navController = navController,
                        movieStateList = movieState,
                        onEvent = movieListViewModel::onEvent
                    )
                }
                composable(Screen.FavouriteMovieScreen.rout){
                    FavouriteMovieScreen(navController = navController, movieStateList = movieState, onEvent = movieListViewModel::onEvent)
                }
            }
        }
    }
}

@Composable
fun BottomBar(
    navController: NavHostController,
    onEvent: (MovieListUiEvents, String) -> Unit
){
    val listElements = listOf(
        BottomItem.Popular,
        BottomItem.Upcoming,
        BottomItem.TopRated,
        BottomItem.NowPlaying
    )
    val selected = rememberSaveable{
        mutableIntStateOf(0)
    }
    NavigationBar(modifier = Modifier.background(MaterialTheme.colorScheme.inverseOnSurface)) {
        Row{
            listElements.forEachIndexed { index, item ->
                NavigationBarItem(
                    selected = selected.intValue == index,
                    onClick = {
                              selected.intValue = index
                        when(selected.intValue){
                            0 -> {onEvent(MovieListUiEvents.Navigate, "Popular Movies")
                                navController.popBackStack()
                                navController.navigate(Screen.PopularMovieList.rout)
                            }
                            1 -> {onEvent(MovieListUiEvents.Navigate, "Upcoming Movies")
                                navController.popBackStack()
                                navController.navigate(Screen.UpcomingMovieList.rout)
                            }
                            2 -> {onEvent(MovieListUiEvents.Navigate, "Top Rated Movies")
                                navController.popBackStack()
                                navController.navigate(Screen.TopRatedMovieList.rout)
                            }
                            3 -> {onEvent(MovieListUiEvents.Navigate, "Now Playing Movies")
                                navController.popBackStack()
                                navController.navigate(Screen.NowPlayingMovieList.rout)
                            }
                            4 -> {onEvent(MovieListUiEvents.Navigate, "Favourite Movies")
                                navController.popBackStack()
                                navController.navigate(Screen.FavouriteMovieScreen.rout)
                            }
                        }
                              },
                    icon = {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = "${item.title} icon",
                            tint = MaterialTheme.colorScheme.onBackground)
                    },
                    label = {
                        Text(text = item.title)

                    })
            }
        }
    }
}

