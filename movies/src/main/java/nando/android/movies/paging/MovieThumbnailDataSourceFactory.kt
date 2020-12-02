package nando.android.movies.paging

import androidx.paging.DataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import nando.android.movies.model.moviesearch.MovieThumbnailModel
import org.koin.core.KoinComponent
import org.koin.core.get
import org.koin.core.parameter.parametersOf
import org.koin.ext.scope

class MovieThumbnailDataSourceFactory(
    pagedDataSource: MovieThumbnailDataSource,
    private val scope: CoroutineScope
): DataSource.Factory<Int, MovieThumbnailModel>(), KoinComponent {

    var sourceFlow = MutableStateFlow(pagedDataSource)
    var query: String = ""
        set(value) {
            field = value
            sourceFlow.value.invalidate()
        }

    override fun create(): DataSource<Int, MovieThumbnailModel> {
        val dataSource = get<MovieThumbnailDataSource>{ parametersOf(scope, query)}
        sourceFlow.value = dataSource
        return sourceFlow.value
    }
}