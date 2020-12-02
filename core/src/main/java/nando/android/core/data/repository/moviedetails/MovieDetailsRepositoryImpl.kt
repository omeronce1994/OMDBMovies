package nando.android.core.data.repository.moviedetails

import kotlinx.coroutines.flow.*
import nando.android.core.data.datasource.moviedetails.MovieDetailsDataSource
import nando.android.core.model.Resource
import nando.android.core.model.db.entities.MovieEntity

class MovieDetailsRepositoryImpl(
    private val localDataSource: MovieDetailsDataSource,
    private val remoteDataSource: MovieDetailsDataSource
): MovieDetailsRepository {

    override suspend fun getMovieById(imdbId: String): Flow<Resource<MovieEntity>> = flow {
        localDataSource.getMovieById(imdbId).collect {
            if (it is Resource.Success) {
                emit(it)
            }
        }
        remoteDataSource.getMovieById(imdbId).collect {
            if (it is Resource.Success) {
                localDataSource.saveMovie(it.data)
            }
            emit(it)
        }
    }

    override suspend fun saveMovie(movieEntity: MovieEntity): Flow<Resource<Unit>>
        = localDataSource.saveMovie(movieEntity)

    override suspend fun deleteMovie(movieEntity: MovieEntity): Flow<Resource<Unit>>
        = localDataSource.deleteMovie(movieEntity)
}