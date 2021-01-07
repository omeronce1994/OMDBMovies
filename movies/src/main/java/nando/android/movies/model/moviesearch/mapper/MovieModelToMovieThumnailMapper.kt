package nando.android.movies.model.moviesearch.mapper

import nando.android.core.mapper.Mapper
import nando.android.core.model.movies.MovieModel
import nando.android.movies.model.moviesearch.MovieThumbnailModel

/**
 * Mapper to map db model to feature model
 *
 * @property localDataSource
 */
class MovieModelToMovieThumnailMapper(): Mapper<MovieModel, MovieThumbnailModel> {

    override suspend fun map(from: MovieModel): MovieThumbnailModel {
        return MovieThumbnailModel(
            imdbID = from.imdbId,
            released = from.year,
            imagePath = from.imagePath,
            isFavourite = from.isFavourite
        )
    }
}