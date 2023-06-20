package com.timife.movies.presentation.movieslist.ui

import android.content.Context
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsSelectable
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.performClick
import androidx.navigation.NavController
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.timife.movies.domain.model.Movie
import com.timife.movies.presentation.Screen
import org.junit.Before
import org.junit.Rule

import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito

@RunWith(AndroidJUnit4::class)
class MovieItemKtTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var navController: TestNavHostController

    @Test
    fun movieItemTest() {
         composeTestRule.setContent {
            navController = TestNavHostController(LocalContext.current)
            navController.navigatorProvider.addNavigator(ComposeNavigator())
            MovieItem(
                modifier = Modifier,
                movie = movie,
                isFavourite = false,
                navController = navController
            ) {}
        }
        composeTestRule.onNodeWithContentDescription("Movie Item").assertExists()
        composeTestRule.onNodeWithContentDescription("Movie Item").assertHasClickAction()
        composeTestRule.onNodeWithContentDescription("Favourites icon").assertExists()
        composeTestRule.onNodeWithContentDescription("Favourites icon").assertHasClickAction()
    }

    companion object {
        val movie = Movie(
            1073140,
            "English",
            "svg.com",
            "24-10",
            "Game of Thrones",
            3.2

        )
    }
}