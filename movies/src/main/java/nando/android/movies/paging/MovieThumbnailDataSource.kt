package nando.android.movies.paging

import android.util.Log
import androidx.paging.PageKeyedDataSource
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import nando.android.core.data.repository.moviesearch.MovieSearchRepository
import nando.android.core.mapper.Mapper
import nando.android.core.model.Resource
import nando.android.core.model.network.response.moviesearch.MovieSearchResult
import nando.android.movies.model.moviesearch.MovieThumbnailModel
import nando.android.movies.util.MoviesConstants.INITIAL_PAGE

class MovieThumbnailDataSource(
    private val repository: MovieSearchRepository,
    private val scope: CoroutineScope,
    private val mapper: Mapper<MovieSearchResult, MovieThumbnailModel>,
    private val query: String,
    private val initialPage: Int = INITIAL_PAGE,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
): PageKeyedDataSource<Int, MovieThumbnailModel>() {

    private val TAG = "MovieThumbnailDataSourc"

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, MovieThumbnailModel>
    ) {
        scope.launch(CoroutineExceptionHandler { coroutineContext, throwable ->
            Log.i(TAG, "loadInitial: ${throwable.message}")
        } + dispatcher) {
            Log.i(TAG, "loadInitial: ${isActive} ${isActive}")
            repository.searchMovie(query, initialPage).collect {
                when(it) {
                    is Resource.Success -> {
                        val items = it.data.searchResults.map {movieEntity ->
                            mapper.map(movieEntity)
                        }
                        callback.onResult(items, null, initialPage + 1)
                    }
                }
            }
        }
    }

    override fun loadAfter(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, MovieThumbnailModel>
    ) {
        scope.launch (CoroutineExceptionHandler { coroutineContext, throwable ->
            Log.i(TAG, "loadAfter: ${throwable.message}")
        } + dispatcher){
            Log.i(TAG, "loadAfter: ")
            repository.searchMovie(query, params.key).collect {
                when(it) {
                    is Resource.Success -> {
                        val items = it.data.searchResults.map {movieEntity ->
                            mapper.map(movieEntity)
                        }
                        callback.onResult(items, params.key + 1)
                    }
                }
            }
        }
    }

    override fun loadBefore(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, MovieThumbnailModel>
    ) {}
}