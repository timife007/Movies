package com.timife.movies.presentation.movieslist.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
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
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ExperimentalComposeApi
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.timife.movies.domain.model.Movie
import com.timife.movies.presentation.Screen
import com.timife.movies.presentation.moviedetails.MovieDetailViewModel
import com.timife.movies.presentation.movieslist.MoviesViewModel

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@ExperimentalComposeApi
@Composable
fun DiscoverMoviesScreen(
    navController: NavController,
) {
    val viewModel = hiltViewModel<MoviesViewModel>()
    val movies = viewModel.movies.collectAsLazyPagingItems()
    Scaffold(
        topBar = { TopAppBar() }
    ) {

        Column(modifier = Modifier.fillMaxSize()) {
            Row(modifier = Modifier.height(35.dp)) {

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
                                .clickable {
                                    navController.navigate(Screen.MovieDetailsScreen.route + "/${movie.id}")
                                }
                                .padding(4.dp)
                        )
                    }
                }
                item {
                    if (movies.loadState.append is LoadState.Loading) {
                        CircularProgressIndicator()
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
                        Text(
                            text = "Please check internet connection",
                            textAlign = TextAlign.Center,
                            color = MaterialTheme.colors.onPrimary
                        )
                        Text(text = "Retry", color = Color.Green, modifier = Modifier.clickable {
                            viewModel.fetchPagedData()
                        })

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
