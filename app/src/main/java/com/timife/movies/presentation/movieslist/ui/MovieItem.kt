package com.timife.movies.presentation.movieslist.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconToggleButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.StarRate
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.timife.movies.BuildConfig
import com.timife.movies.domain.model.Movie
import com.timife.movies.presentation.Screen
import com.timife.movies.ui.theme.Purple40
import com.timife.movies.ui.theme.PurpleGrey80
import com.timife.movies.ui.theme.Shapes

@Composable
fun MovieItem(
    modifier: Modifier = Modifier,
    movie: Movie,
    isFavourite:Boolean,
    navController: NavController,
    onCheckChange : (Boolean) -> Unit,
) {
    Column(
        modifier = modifier.background(Color.Transparent),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Card(
            modifier = Modifier
                .shadow(10.dp)
                .fillMaxWidth()
                .height(200.dp)
                .clip(RoundedCornerShape(12.dp))
        ) {
            Box {
                val imageLink = BuildConfig.IMAGE_BASE_URL + movie.poster_path
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(imageLink)
                        .decoderFactory(SvgDecoder.Factory())
                        .build(),
                    contentDescription = "Movie Item",
                    modifier = Modifier
                        .fillMaxSize()
                        .clickable {
                            navController.navigate(Screen.MovieDetailsScreen.route + "/${movie.id}")
                        },
                    contentScale = ContentScale.FillBounds
                )
                IconToggleButton(
                    checked = isFavourite,
                    onCheckedChange = onCheckChange,
                    modifier = Modifier.align(Alignment.BottomEnd)
                ) {
                    Surface(
                        shape = Shapes.medium,
                        color = Color.White,
                        modifier = Modifier.size(25.dp)
                    ) {
                        Icon(
                            tint = if (isFavourite){ Color.Red} else{ Color.LightGray},
                            modifier = modifier.graphicsLayer {
                                scaleX = 1.3f
                                scaleY = 1.3f
                            },
                            imageVector = if (isFavourite) {
                                Icons.Filled.Favorite
                            } else {
                                Icons.Default.FavoriteBorder
                            },
                            contentDescription = null
                        )
                    }

                }
            }

        }

        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = movie.title,
            color = Color.Gray,
            textAlign = TextAlign.Center,
            style = MaterialTheme
                .typography
                .body1.copy(fontWeight = FontWeight.SemiBold)
        )
    }
}
