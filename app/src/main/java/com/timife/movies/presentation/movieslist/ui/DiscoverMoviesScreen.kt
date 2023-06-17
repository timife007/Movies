package com.timife.movies.presentation.movieslist.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.items
import com.timife.movies.domain.model.Movie
import com.timife.movies.presentation.Screen

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@ExperimentalComposeApi
@Composable
fun DiscoverMoviesScreen(
    navController: NavController,
    movies: LazyPagingItems<Movie>,
) {
    Scaffold(
        topBar = { TopAppBar() }
    ) {

        Column(modifier = Modifier.fillMaxSize()) {
            Row(modifier = Modifier) {
                Text(
                    text = "Discover Movies",
                    style = MaterialTheme.typography.h5,
                    modifier = Modifier
                        .padding(start = 16.dp, top = 16.dp, bottom = 5.dp)
                        .fillMaxWidth(0.75f),
                    color = MaterialTheme.colors.onBackground,
                    fontWeight = FontWeight.ExtraBold
                )
            }
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(4.dp)
            ) {
                items(movies) { movie ->
                    if (movie != null) {
                        MovieItem(movie = movie, modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                navController.navigate(Screen.MovieDetailsScreen.route + "/${movie.id}")
                            }
                            .padding(16.dp)
                        )
                        Divider(modifier = Modifier.padding(horizontal = 16.dp))
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
            } else if (movies.loadState.refresh is LoadState.Error) {
                (movies.loadState.refresh as LoadState.Error).error.message?.let { errorMessage ->
                    Text(
                        text = errorMessage,
                        color = MaterialTheme.colors.onPrimary
                    )
                }
            }
        }
    }
}

@Composable
fun TopAppBar() {
    Row(modifier = Modifier.background(Color.Transparent)) {
        IconButton(onClick = { /*TODO*/ }) {
            Icon(
                imageVector = Icons.Default.Menu,
                contentDescription = "Menu",
                modifier = Modifier.size(30.dp),
                tint = MaterialTheme.colors.onBackground
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        IconButton(onClick = { /*TODO*/ }) {
            Icon(
                imageVector = Icons.Filled.MoreVert,
                contentDescription = "Notification",
                modifier = Modifier.size(30.dp),
                tint = MaterialTheme.colors.onBackground
            )
        }
    }
}