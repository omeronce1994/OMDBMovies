package nando.android.movies.viewmodel.moviedetails

import androidx.lifecycle.*
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import nando.android.core.data.repository.moviedetails.MovieDetailsRepository
import nando.android.core.model.Resource
import nando.android.core.model.error.ErrorHandler
import nando.android.core.model.error.LocalizedErrorHandler
import nando.android.movies.model.moviedetails.MovieItemModel
import nando.android.movies.model.moviedetails.mapper.MovieItemMapper
import nando.android.movies.model.moviedetails.mapper.MovieItemToMovieEntityMapper

class MovieDetailsViewModel(
    private val repository: MovieDetailsRepository,
    private val mapper: MovieItemMapper,
    private val reveresedMapper: MovieItemToMovieEntityMapper,
    private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO
): ViewModel() {

    private val _liveData = MediatorLiveData<UiState>()
    val liveData: LiveData<UiState> = _liveData
    private var movieItemModel = MutableLiveData<MovieItemModel>()

    init {
        _liveData.addSource(movieItemModel, Observer {
            _liveData.postValue(UiState(ViewState.ShowDetails, data = it))
        })
    }

    fun getMovie(imdbId: String) {
        _liveData.value = UiState(ViewState.Loading)
        viewModelScope.launch(coroutineDispatcher) {
            repository.getMovieById(imdbId).collect {
                when(it) {
                    is Resource.Success -> {
                        movieItemModel.postValue(mapper.map(it.data))
                    }
                    is Resource.Error -> {
                        val state = UiState(ViewState.Error, errorHandler = it.errorHandler)
                        _liveData.postValue(state)
                    }
                    else -> {
                        val state = UiState(ViewState.Loading)
                        _liveData.postValue(state)
                    }
                }
            }
        }
    }

    fun decideFavourite() {
        val current = movieItemModel.value
        if (current == null) {
            _liveData.postValue(UiState(ViewState.Error))
        }
        else {
            if (current.isFavourite) {
                deleteMovie(current)
            }
            else {
                saveMovie(current)
            }
        }
    }

    private fun saveMovie(current: MovieItemModel) {
        _liveData.value = UiState(ViewState.Loading)
        viewModelScope.launch(coroutineDispatcher) {
            val newEntity = reveresedMapper.map(current)
            repository.saveMovie(newEntity).collect {
                when (it) {
                    is Resource.Success -> {
                        val copied = current.copy(isFavourite = true)
                        movieItemModel.postValue(copied)
                    }
                    is Resource.Error -> {
                        val state = UiState(ViewState.Error, errorHandler = it.errorHandler)
                        _liveData.postValue(state)
                    }
                    else -> {
                        val state = UiState(ViewState.Loading)
                        _liveData.postValue(state)
                    }
                }
            }
        }
    }

    private fun deleteMovie(current: MovieItemModel) {
        _liveData.value = UiState(ViewState.Loading)
        viewModelScope.launch(coroutineDispatcher) {
            val removedEntity = reveresedMapper.map(current)
            repository.deleteMovie(removedEntity).collect {
                when (it) {
                    is Resource.Success -> {
                        val copied = current.copy(isFavourite = false)
                        movieItemModel.postValue(copied)
                    }
                    is Resource.Error -> {
                        val state = UiState(ViewState.Error, errorHandler = it.errorHandler)
                        _liveData.postValue(state)
                    }
                    else -> {
                        val state = UiState(ViewState.Loading)
                        _liveData.postValue(state)
                    }
                }
            }
        }
    }

    data class UiState(
        val state: ViewState = ViewState.Loading,
        val data: MovieItemModel = MovieItemModel(),
        val errorHandler: ErrorHandler = LocalizedErrorHandler(0)
    )

    sealed class ViewState {
        object Loading : ViewState()
        object ShowDetails : ViewState()
        object Error : ViewState()
    }
}