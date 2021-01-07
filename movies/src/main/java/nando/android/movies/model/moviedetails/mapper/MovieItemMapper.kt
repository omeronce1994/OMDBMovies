package nando.android.movies.model.moviedetails.mapper

import nando.android.core.data.datasource.moviedetails.MovieDetailsDataSource
import nando.android.core.mapper.Mapper
import nando.android.core.model.movies.MovieModel
import nando.android.movies.model.moviedetails.MovieDetailsModel

/**
 * Mapper used to map our db model to feature model
 *
 */
class MovieItemMapper(): Mapper<MovieModel, MovieDetailsModel> {

    override suspend fun map(from: MovieModel): MovieDetailsModel {
        return MovieDetailsModel(
            from.imdbId,
            from.title,
            from.releaseDate,
            from.director,
            from.runtime,
            from.genre,
            from.actors,
            from.plot,
            from.metaScore,
            from.imagePath,
            from.isFavourite
        )
    }
}