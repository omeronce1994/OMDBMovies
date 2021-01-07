package nando.android.movies.model.moviedetails.mapper

import nando.android.core.mapper.Mapper
import nando.android.core.model.db.entities.MovieEntity
import nando.android.core.model.movies.MovieModel
import nando.android.movies.model.moviedetails.MovieDetailsModel

/**
 * Mapper to map our feature model back to our db model
 *
 */
class MovieDetailsToMovieModelMapper(): Mapper<MovieDetailsModel, MovieModel> {

    override suspend fun map(from: MovieDetailsModel): MovieModel
        = MovieModel(
        from.imdbId,
        from.title,
        from.releaseDate,
        from.director,
        from.runtime,
        from.genre,
        from.actors,
        from.plot,
        from.metaScore,
        from.imagePath
    )
}