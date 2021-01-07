package nando.android.core.mapper

import nando.android.core.model.movies.MovieModel
import nando.android.core.model.network.response.moviesearch.MovieSearchResult

class MovieSearchResultToMovieModelMapper(
    private val isFavourite: Boolean
): Mapper<MovieSearchResult, MovieModel> {

    override suspend fun map(from: MovieSearchResult): MovieModel
        = MovieModel(
        imdbId = from.imdbID,
        imagePath = from.poster,
        title = from.title,
        type = from.type,
        year = from.year,
        isFavourite = isFavourite
    )
}