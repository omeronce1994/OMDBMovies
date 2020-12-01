package nando.android.core.mapper

import nando.android.core.model.db.entities.MovieEntity
import nando.android.core.model.network.response.moviedetails.MovieDetailsResponse

class MovieEntityMapper: Mapper<MovieDetailsResponse, MovieEntity> {

    override suspend fun map(from: MovieDetailsResponse): MovieEntity
        = MovieEntity(
        from.imdbID,
        from.title,
        from.released,
        from.director,
        from.runtime,
        from.genre,
        from.actors,
        from.plot,
        from.metascore,
        from.poster
    )
}