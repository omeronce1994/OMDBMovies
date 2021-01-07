package nando.android.core.mapper

import nando.android.core.model.db.entities.MovieEntity
import nando.android.core.model.movies.MovieModel
import nando.android.core.model.network.response.moviedetails.MovieDetailsResponse

class MovieResponseToMovieModelMapper(
    private val isFavourite: Boolean
): Mapper<MovieDetailsResponse, MovieModel> {

    override suspend fun map(from: MovieDetailsResponse): MovieModel
        = MovieModel(
        from.imdbID,
        from.title,
        from.released,
        from.director,
        from.runtime,
        from.genre,
        from.actors,
        from.plot,
        from.metascore,
        from.poster,
        isFavourite
    )
}