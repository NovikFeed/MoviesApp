package programmer.movie_application.movieNavigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DataExploration
import androidx.compose.material.icons.filled.DisplaySettings
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.Upcoming
import androidx.compose.ui.graphics.vector.ImageVector


sealed class BottomItem(
    val title : String,
    val icon : ImageVector
){
    object Popular : BottomItem(
        title = "Popular",
        icon = Icons.Default.Movie
    )
    object Upcoming : BottomItem(
        title = "Upcoming",
        icon = Icons.Default.Upcoming
    )

    object TopRated : BottomItem(
        title = "TopRated",
        icon = Icons.Default.DataExploration
    )

    object NowPlaying : BottomItem(
        title = "NowPlaying",
        icon = Icons.Default.DisplaySettings
    )
}