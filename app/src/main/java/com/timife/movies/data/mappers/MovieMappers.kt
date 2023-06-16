package com.timife.movies.data.mappers

import com.timife.movies.data.local.model.MoviesEntity
import com.timife.movies.data.remote.dtos.casts.CastDto
import com.timife.movies.data.remote.dtos.details.GenreDto
import com.timife.movies.data.remote.dtos.details.MovieDetailsDto
import com.timife.movies.data.remote.dtos.movies.MovieDto
import com.timife.movies.domain.model.Cast
import com.timife.movies.domain.model.Genre
import com.timife.movies.domain.model.Movie
import com.timife.movies.domain.model.MovieDetails

fun MovieDto.toMoviesEntity(): MoviesEntity {
    return MoviesEntity(
        id = id,
        original_language = originalLanguage,
        poster_path = posterPath,
        release_date = releaseDate,
        title = title,
        vote_average = voteAverage
    )
}

fun MoviesEntity.toMovie(): Movie {
    return Movie(
        id = id,
        original_language = original_language,
        poster_path = poster_path,
        release_date = release_date,
        title = title,
        vote_average = vote_average
    )
}

fun MovieDetailsDto.toMovieDetails(): MovieDetails {
    return MovieDetails(
        backdropPath = backdropPath,
        homepage = homepage,
        id = id ?: 0,
        posterPath = posterPath,
        title = title,
        language = originalLanguage,
        tagline = tagline,
        voteAverage = voteAverage,
        overview = overview,
        runtime = runtime,
        genres = genres.map {
            it.toGenre()
        },
        releaseDate = releaseDate ?: ""
    )
}


fun GenreDto.toGenre(): Genre {
    return Genre(
        id = id,
        name = name
    )
}

fun CastDto.toCast(): Cast {
    return Cast(
        castId = castId,
        character = character,
        name = name,
        profilePath = profilePath ?: ""
    )
}