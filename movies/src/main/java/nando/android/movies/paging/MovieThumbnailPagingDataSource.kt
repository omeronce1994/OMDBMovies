package nando.android.movies.paging

import android.util.Log
import androidx.paging.PageKeyedDataSource
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import nando.android.core.data.repository.moviesearch.MovieSearchRepository
import nando.android.core.mapper.Mapper
import nando.android.core.model.Resource
import nando.android.core.model.movies.MovieModel
import nando.android.core.model.network.response.moviesearch.MovieSearchResult
import nando.android.movies.model.moviesearch.MovieThumbnailModel
import nando.android.movies.util.MoviesConstants.INITIAL_PAGE

class MovieThumbnailPagingDataSource(
    private val repository: MovieSearchRepository,
    private val scope: CoroutineScope,
    private val mapper: Mapper<MovieModel, MovieThumbnailModel>,
    private val query: String,
    private val initialPage: Int = INITIAL_PAGE,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
): PageKeyedDataSource<Int, MovieThumbnailModel>() {

    private val TAG = "MovieThumbnailDataSourc"

    //flow used to observe state of pagination api calls
    private val _networkState = MutableStateFlow<Resource<Int>>(Resource.Loading())
    val networkState: Flow<Resource<Int>> = _networkState

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, MovieThumbnailModel>
    ) {
        //avoid executing api call for empty query
        if (query.isEmpty()) {
            _networkState.value = Resource.Success(0)
            return
        }
        scope.launch(CoroutineExceptionHandler { coroutineContext, throwable ->
            Log.i(TAG, "loadInitial: ${throwable.message}")
        } + dispatcher) {
            repository.searchMovie(query, initialPage).collect {
                when(it) {
                    is Resource.Success -> {
                        val items = it.data.map {movie ->
                            mapper.map(movie)
                        }
                        callback.onResult(items, null, initialPage + 1)
                    }
                    is Resource.Error -> {
                        _networkState.value = Resource.Error(it.errorHandler)
                    }
                    else -> {
                        _networkState.value = Resource.Loading()
                    }
                }
            }
        }
    }

    override fun loadAfter(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, MovieThumbnailModel>
    ) {
        if (query.isEmpty()) {
            _networkState.value = Resource.Success(0)
            return
        }
        scope.launch (CoroutineExceptionHandler { coroutineContext, throwable ->
            Log.i(TAG, "loadAfter: ${throwable.message}")
        } + dispatcher){
            repository.searchMovie(query, params.key).collect {
                when(it) {
                    is Resource.Success -> {
                        _networkState.value = Resource.Success(0)
                        val items = it.data.map {movie ->
                            mapper.map(movie)
                        }
                        callback.onResult(items, params.key + 1)
                    }
                    is Resource.Error -> {
                        _networkState.value = Resource.Error(it.errorHandler)
                    }
                    else -> { }
                }
            }
        }
    }

    override fun loadBefore(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, MovieThumbnailModel>
    ) {}
}