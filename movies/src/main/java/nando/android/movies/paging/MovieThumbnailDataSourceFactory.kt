package nando.android.movies.paging

import androidx.paging.DataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import nando.android.movies.model.moviesearch.MovieThumbnailModel
import org.koin.core.KoinComponent
import org.koin.core.get
import org.koin.core.parameter.parametersOf

class MovieThumbnailDataSourceFactory(
    pagedDataSource: MovieThumbnailDataSource
): DataSource.Factory<Int, MovieThumbnailModel>(), KoinComponent {

    var sourceFlow = MutableStateFlow(pagedDataSource)

    override fun create(): DataSource<Int, MovieThumbnailModel> {
        return sourceFlow.value
    }
}