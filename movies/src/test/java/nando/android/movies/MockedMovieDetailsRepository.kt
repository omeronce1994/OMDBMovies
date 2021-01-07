package nando.android.movies

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import nando.android.core.data.repository.moviedetails.MovieDetailsRepository
import nando.android.core.model.Resource
import nando.android.core.model.movies.MovieModel

open class MockedMovieDetailsRepository(
    private val movies: MutableList<MovieModel> = mutableListOf()
): MovieDetailsRepository {

    override suspend fun getMovieById(imdbId: String): Flow<Resource<MovieModel>> = flow {
        val movie = movies.find { it.imdbId == imdbId } ?: throw Exception("movie not found")
        emit(Resource.Success(movie))
    }

    override suspend fun saveMovie(movieModel: MovieModel): Flow<Resource<Unit>> = flow {
        movies.add(movieModel)
        emit(Resource.Success(empty()))
    }

    override suspend fun deleteMovie(movieModel: MovieModel): Flow<Resource<Unit>> = flow {
        val index = movies.indexOfFirst { it.imdbId == movieModel.imdbId }
        val result = if (index == -1) {
            throw Exception("could not find movie")
        }
        else {
            Resource.Success(empty())
        }
        emit(result)
    }

    //just use this to return unit value
    private fun empty() {}

}