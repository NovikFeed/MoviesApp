package programmer.movie_application.dependencyInjection


import programmer.movie_application.movieList.data.repository.MovieListRepository
import programmer.movie_application.movieList.domain.repository.MovieRepositoryInterface
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindMovieRepository(
        movieListRepository: MovieListRepository
    ): MovieRepositoryInterface
}