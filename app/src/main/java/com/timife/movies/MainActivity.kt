package com.timife.movies

import android.os.Bundle
import android.view.Surface
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ExperimentalComposeApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.asFlow
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.paging.compose.collectAsLazyPagingItems
import com.timife.movies.presentation.Screen
import com.timife.movies.presentation.moviedetails.ui.MovieDetailScreen
import com.timife.movies.presentation.movieslist.MoviesViewModel
import com.timife.movies.presentation.movieslist.ui.DiscoverMoviesScreen
import com.timife.movies.ui.theme.MoviesTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalComposeApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MoviesTheme {
                val navController = rememberNavController()
                val viewModel = hiltViewModel<MoviesViewModel>()
                NavHost(
                    navController = navController,
                    startDestination = Screen.DiscoverMoviesScreen.route
                ) {
                    composable(Screen.DiscoverMoviesScreen.route) {
                        DiscoverMoviesScreen(
                            movies = viewModel.movies.asFlow().collectAsLazyPagingItems(),
                            navController = navController
                        )
                    }
                    composable(Screen.MovieDetailsScreen.route + "/{movieId}",
                        arguments = listOf(
                            navArgument(name = "movieId") {
                                type = NavType.IntType
                            }
                        )) {
                        MovieDetailScreen(navController = navController)
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MoviesTheme {
        Greeting("Android")
    }
}