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

    /**
     * flow o observe pagedDataSource instances which are created on create() function invocation
     * this way we can observe each new instance of data source that is created each time we
     * call invalidate
     */
    var sourceFlow = MutableStateFlow(pagedDataSource)

    var query: String = ""
        set(value) {
            //invalidate and restart pagination on query change
            field = value
            sourceFlow.value.invalidate()
        }

    override fun create(): DataSource<Int, MovieThumbnailModel> {
        //inject data source instance and update flow
        val dataSource = get<MovieThumbnailDataSource>{ parametersOf(scope, query)}
        sourceFlow.value = dataSource
        return sourceFlow.value
    }
}