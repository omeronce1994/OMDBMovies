package nando.android.movies.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import nando.android.movies.paging.MovieThumbnailDataSourceFactory
import org.koin.core.KoinComponent
import org.koin.core.inject
import org.koin.core.parameter.parametersOf

class MovieSearchViewModel(
    private val config: PagedList.Config
): ViewModel(), KoinComponent {

    private val pagedFactory: MovieThumbnailDataSourceFactory by inject {
        parametersOf(viewModelScope, "")
    }

    val data = LivePagedListBuilder(pagedFactory, config).build()

    fun search(query: String) {
        pagedFactory.query = query
        viewModelScope
    }

    data class UiState(
        private val showLoading: Boolean = false
    )

}