package nando.android.core.mapper

import nando.android.core.model.db.entities.MovieEntity
import nando.android.core.model.movies.MovieModel

class MovieModelToMovieEntityMapper: Mapper<MovieModel, MovieEntity> {

    override suspend fun map(from: MovieModel): MovieEntity = MovieEntity(
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