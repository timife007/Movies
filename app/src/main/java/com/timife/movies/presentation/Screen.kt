package com.timife.movies.presentation

sealed class Screen(val route: String) {
    object DiscoverMoviesScreen : Screen("discover_movies_screen")
    object MovieDetailsScreen : Screen("details_screen")
}