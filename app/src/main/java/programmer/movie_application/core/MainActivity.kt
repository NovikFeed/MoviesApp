package programmer.movie_application.core

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import programmer.movie_application.ui.theme.MobvesAppTheme
import dagger.hilt.android.AndroidEntryPoint
import programmer.movie_application.core.presentation.DetailsScreen
import programmer.movie_application.core.presentation.HomeScreen
import programmer.movie_application.movieNavigation.Screen

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MobvesAppTheme {
                setBarColor(color = MaterialTheme.colorScheme.inverseOnSurface)
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = Screen.Home.rout){
                        composable(Screen.Home.rout){
                            HomeScreen(navController)
                        }
                        composable(route = Screen.Details.rout + "/{moviedId}",
                            arguments = listOf(
                                navArgument("movieId"){
                                    type = NavType.IntType
                                }
                            )
                        ){backStackEntry ->
                            //DetailsScreen(backStackEntry)
                        }


                    }

                }
            }
        }
    }
    @Composable
    private fun setBarColor(color : Color){
        val systemUiController = rememberSystemUiController()
        LaunchedEffect(key1  = color){
            systemUiController.setSystemBarsColor(color)
        }
    }
}
