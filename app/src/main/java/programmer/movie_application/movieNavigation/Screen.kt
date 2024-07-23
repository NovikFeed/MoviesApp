package programmer.movie_application.movieNavigation

sealed class Screen(val rout : String) {
    object Home : Screen("main")
    object PopularMovieList : Screen("popularMovie")
    object UpcomingMovieList : Screen("upcomingMovie")
    object TopRatedMovieList : Screen("topRatedMovie")
    object NowPlayingMovieList : Screen("nowPlayingMovie")

    object Details : Screen("details")
}