package programmer.movie_application.core.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.ImageNotSupported
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.drawable.toBitmap
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import programmer.movie_application.core.presentation.components.RatingBar
import programmer.movie_application.core.presentation.util.getAverageColor
import programmer.movie_application.details.presentation.DetailsViewModel
import programmer.movie_application.movieList.data.remote.MovieAPI
import programmer.movie_application.movieList.presentation.MovieListViewModel
import programmer.movie_application.movieList.util.Category

@Composable
fun DetailsScreen() {
    val detailsViewModel = hiltViewModel<DetailsViewModel>()
    val detailsState = detailsViewModel.details.collectAsState().value

    val movieListViewModel = hiltViewModel<MovieListViewModel>()

    val image = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(MovieAPI.IMAGE_BASE_URL + detailsState.movie?.backdrop_path)
            .size(Size.ORIGINAL)
            .build()
    ).state

    val posterImage = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(MovieAPI.IMAGE_BASE_URL + detailsState.movie?.poster_path)
            .size(Size.ORIGINAL)
            .build()
    ).state

    val defaultIconFavourite = Icons.Default.FavoriteBorder
    var likeIcon by remember{
        mutableStateOf(defaultIconFavourite)
    }

    val defaultColor = MaterialTheme.colorScheme.onSecondary
    var dominantColor by remember {
        mutableStateOf(defaultColor)
    }
    Column(modifier = Modifier
        .fillMaxSize()
        .verticalScroll(rememberScrollState())
        .background(
            Brush.verticalGradient(colors = listOf(defaultColor, dominantColor))
        )) {
        if(image is AsyncImagePainter.State.Success){
            dominantColor = getAverageColor(imageBitmap = image.result.drawable.toBitmap().asImageBitmap())
            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp),
                painter = image.painter,
                contentDescription = detailsState.movie?.title,
                contentScale = ContentScale.Crop
            )
        }
        else{
            Box(modifier = Modifier
                .fillMaxWidth()
                .height(220.dp)
                .background(MaterialTheme.colorScheme.primaryContainer)){
                Icon(imageVector = Icons.Default.ImageNotSupported, contentDescription = detailsState.movie?.title)
            }
        }
        detailsState.movie?.let{movie ->
            Row {
                if (posterImage is AsyncImagePainter.State.Success) {
                    Box(modifier = Modifier.size(180.dp,260.dp)) {
                        Image(
                            modifier = Modifier
                                .matchParentSize()
                                .padding(16.dp)
                                .clip(RoundedCornerShape(12.dp)),
                            painter = posterImage.painter,
                            contentDescription = detailsState.movie.title
                        )
                        if(movie.category == Category.FAVOURITE){
                            likeIcon = Icons.Default.Favorite
                            var visibility by remember{ mutableStateOf(true) }
                            if(visibility) {
                                Icon(
                                    modifier = Modifier
                                        .align(Alignment.Center)
                                        .size(50.dp)
                                        .clickable(
                                            interactionSource = remember { MutableInteractionSource() },
                                            indication = null
                                        ) {
                                            detailsViewModel.removeMovieFromFavourite(movie)
                                            visibility = false
                                        },
                                    imageVector = likeIcon,
                                    contentDescription = "f",
                                )
                            }
                        }
                        else{
                            Icon(
                                modifier = Modifier
                                    .align(Alignment.Center)
                                    .size(50.dp)
                                    .clickable(
                                        interactionSource = remember { MutableInteractionSource() },
                                        indication = null
                                    ) {
                                        detailsViewModel.addMovieToFavourite(movie)
                                        likeIcon = if (likeIcon == Icons.Default.FavoriteBorder) {
                                            Icons.Default.Favorite
                                        } else {
                                            Icons.Default.FavoriteBorder
                                        }
                                    },
                                imageVector = likeIcon,
                                contentDescription = "f",
                            )
                        }
                    }
                } else {
                    Box(
                        modifier = Modifier
                            .width(160.dp)
                            .height(240.dp)
                            .background(MaterialTheme.colorScheme.secondaryContainer)
                            .clip(RoundedCornerShape(12.dp))
                    ) {
                        Icon(
                            imageVector = Icons.Default.ImageNotSupported,
                            contentDescription = detailsState.movie.title
                        )
                    }
                }
                Column {
                    Text(
                        modifier = Modifier.padding(vertical = 16.dp),
                        text = movie.title,
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp
                    )
                    Row {
                        RatingBar(
                            rating = movie.vote_average / 2,
                            modifier = Modifier.padding(bottom = 16.dp)
                        )
                        Text(text = (movie.vote_average / 2).toString().take(3))
                    }
                    Text(
                        modifier = Modifier.padding(bottom = 16.dp),
                        text = "Language: ${movie.original_language}"
                    )
                    Text(
                        modifier = Modifier.padding(bottom = 16.dp),
                        text = "Release Date: ${movie.release_date}"
                    )
                    Text(
                        modifier = Modifier.padding(bottom = 16.dp),
                        text = "${movie.vote_count} Votes"
                    )
                }
            }
            Column (
                Modifier
                    .fillMaxSize()
                    .padding(start = 16.dp, top = 24.dp)
            ){
                Text(
                    modifier = Modifier.padding(bottom = 16.dp),
                    text = "Overview:",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    modifier = Modifier.padding(end = 16.dp),
                    text = movie.overview,
                    textAlign = TextAlign.Justify
                )


            }
            
        }
    }
}

