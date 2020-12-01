package nando.android.core.data.datasource.moviedetails

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import nando.android.core.model.Resource
import nando.android.core.model.db.dao.MoviesDao
import nando.android.core.model.db.entities.MovieEntity
import nando.android.core.model.db.util.safeDbCall

class LocalMovieDetailsDataSource(
    private val moviesDao: MoviesDao
): MovieDetailsDataSource {

    override suspend fun getMovieById(imdbId: String): Flow<Resource<MovieEntity>> = flow {
        val result = safeDbCall {
            moviesDao.getMovieById(imdbId) ?: throw Exception("Item Not Found")
        }
        emit(result)
    }

    override suspend fun saveMovie(movieEntity: MovieEntity): Flow<Resource<Unit>> = flow {
        val result = safeDbCall {
            moviesDao.insertMovie(movieEntity)
        }
        emit(result)
    }

    override suspend fun deleteMovie(movieEntity: MovieEntity): Flow<Resource<Unit>> = flow {
        val result = safeDbCall {
            moviesDao.deleteMovie(movieEntity)
        }
        emit(result)
    }
}