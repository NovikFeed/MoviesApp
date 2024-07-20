package programmer.movie_application.core.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
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
import programmer.movie_application.movieList.presentation.MovieListUiEvents
import programmer.movie_application.movieList.presentation.MovieListViewModel
import programmer.movie_application.movieNavigation.BottomItem
import programmer.movie_application.movieNavigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController){
    val movieListViewModel = hiltViewModel<MovieListViewModel>()
    val movieState = movieListViewModel.movieListState.collectAsState().value
    val bottomNavController = rememberNavController()

    Scaffold(
        topBar = {
                 TopAppBar(
                     title = {
                     Text(text = movieState.currentScreenTitle)
                             },
                     modifier = Modifier.shadow(2.dp),
                     colors = topAppBarColors(
                         MaterialTheme.colorScheme.inverseOnSurface
                     ))
        },
        bottomBar = { BottomBar(navController = bottomNavController, onEvent = movieListViewModel::onEvent)}
    ) {
        Box(modifier = Modifier.fillMaxSize().padding(it)){
            NavHost(
                navController = bottomNavController,
                startDestination = Screen.PopularMovieList.rout,
            ){
                composable(Screen.PopularMovieList.rout){
                    PopularMovieScreen()
                }
                composable(Screen.UpcomingMovieList.rout){
                    UpcomingMovieScreen()
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
                            0 -> {onEvent(MovieListUiEvents.Navigate, "Popular")
                                navController.popBackStack()
                                navController.navigate(Screen.PopularMovieList.rout)
                            }
                            1 -> {onEvent(MovieListUiEvents.Navigate, "Upcoming")
                                navController.popBackStack()
                                navController.navigate(Screen.UpcomingMovieList.rout)
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

