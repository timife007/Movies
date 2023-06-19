package com.timife.movies.presentation.movieslist.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonColors
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Shapes
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ErrorOutline
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ExperimentalComposeApi
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.timife.movies.domain.model.Movie
import com.timife.movies.presentation.Screen
import com.timife.movies.presentation.moviedetails.MovieDetailViewModel
import com.timife.movies.presentation.movieslist.MoviesViewModel
import com.timife.movies.ui.theme.Purple40
import com.timife.movies.ui.theme.PurpleGrey80
import com.timife.movies.ui.theme.Shapes

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@ExperimentalComposeApi
@Composable
fun DiscoverMoviesScreen(
    navController: NavController,
) {
    val viewModel = hiltViewModel<MoviesViewModel>()
    val movies = viewModel.movies.collectAsLazyPagingItems()
    val filterState = viewModel.filterState.collectAsStateWithLifecycle().value
    Scaffold(
        topBar = { TopAppBar() }
    ) {

        Column(modifier = Modifier.fillMaxSize()) {
            Row(
                modifier = Modifier
                    .height(35.dp)
                    .padding(start = 15.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                FilterChip(
                    isSelected = filterState.filterItem == "All",
                    onFilterSelected = {
                        viewModel.filter("All")
                        if (filterState.filterItem != "All") {
                            viewModel.fetchPagedData()
                        }
                    }, text = "All"
                )
                FilterChip(
                    isSelected = filterState.filterItem == "Favourites",
                    onFilterSelected = {
                        viewModel.filter("Favourites")
                        if (filterState.filterItem != "Favourites") {
                            viewModel.fetchPagedFavourites()
                        }
                    },
                    text = "Favourites"
                )
            }

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                horizontalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                items(movies.itemCount) { index ->
                    movies[index]?.let { movie ->
                        MovieItem(
                            movie = movie, modifier = Modifier
                                .fillMaxWidth()
                                .padding(4.dp),
                            navController = navController,
                            isFavourite = movie.isFavourite,
                            onCheckChange = { isFav ->
                                movie.id?.let { id ->
                                    viewModel.toggleFavourite(id, isFav)
                                }
                            }
                        )
                    }
                }
            }
        }
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            if (movies.loadState.refresh is LoadState.Loading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            } else if (movies.loadState.refresh is LoadState.Error && movies.itemCount == 0) {
                (movies.loadState.refresh as LoadState.Error).error.message?.let {
                    Column(
                        modifier = Modifier,
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            imageVector = Icons.Default.ErrorOutline,
                            contentDescription = "Unexpected error",
                            modifier = Modifier.size(100.dp),
                            colorFilter = ColorFilter.tint(MaterialTheme.colors.onBackground),
                        )
                        Text(
                            text = "Please check internet connection",
                            textAlign = TextAlign.Center,
                            color = MaterialTheme.colors.onPrimary
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Retry",
                            modifier = Modifier
                                .clip(RoundedCornerShape(30.dp))
                                .clickable {
                                    viewModel.fetchPagedData()
                                }
                                .background(color = Color.Gray)
                                .scale(0.6f),
                            color = Color.White,
                            fontSize = 30.sp,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun TopAppBar() {
    Row(modifier = Modifier.background(Color.Transparent)) {
        Text(
            text = "Discover Movies",
            style = MaterialTheme.typography.h5,
            modifier = Modifier
                .padding(start = 20.dp, top = 16.dp, bottom = 5.dp)
                .fillMaxWidth(0.75f),
            color = MaterialTheme.colors.onBackground,
            fontWeight = FontWeight.ExtraBold
        )
    }
}
