package nando.android.movies.viewmodel.moviesearch

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import nando.android.core.model.Resource
import nando.android.core.model.error.ErrorHandler
import nando.android.core.model.error.LocalizedErrorHandler
import nando.android.movies.paging.MovieThumbnailDataSourceFactory
import org.koin.core.KoinComponent
import org.koin.core.inject
import org.koin.core.parameter.parametersOf

class MovieSearchViewModel(
    config: PagedList.Config,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
): ViewModel(), KoinComponent {

    private val pagedFactory: MovieThumbnailDataSourceFactory by inject {
        parametersOf(viewModelScope, "")
    }

    val data = LivePagedListBuilder(pagedFactory, config).build()

    private val _uiState = MutableLiveData<UiState>(UiState(ViewState.Loading))
    val uiState: LiveData<UiState> = _uiState

    init {
        //each time paged data source instance is created we are notified about it
        //using flatMapLatest, then we can observe the new instance's network state
        viewModelScope.launch(dispatcher) {
            pagedFactory.sourceFlow.flatMapLatest { dataSource ->
                dataSource.networkState
            }.distinctUntilChanged().collect {
                when(it) {
                    is Resource.Loading -> {
                        _uiState.postValue(UiState(ViewState.Loading))
                    }
                    is Resource.Error -> {
                        _uiState.postValue(UiState(
                            ViewState.Error,
                            it.errorHandler
                        ))
                    }
                    is Resource.Success -> {
                        _uiState.postValue(UiState(ViewState.Success))
                    }
                }
            }
        }
    }


    fun search(query: String) {
        pagedFactory.query = query
        viewModelScope
    }

    data class UiState(
        val state: ViewState = ViewState.Loading,
        val errorHandler: ErrorHandler = LocalizedErrorHandler(0)
    )

    sealed class ViewState() {
        object Loading: ViewState()
        object Error: ViewState()
        object Success: ViewState()
    }
}