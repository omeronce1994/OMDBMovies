package nando.android.movies.di.moviesearch

import androidx.paging.PagedList
import androidx.paging.PagedListAdapter
import kotlinx.coroutines.CoroutineScope
import nando.android.core.data.datasource.DataSourceType
import nando.android.core.data.repository.moviesearch.MovieSearchRepository
import nando.android.core.mapper.Mapper
import nando.android.core.model.network.response.moviesearch.MovieSearchResult
import nando.android.movies.model.moviesearch.MovieThumbnailModel
import nando.android.movies.model.moviesearch.mapper.MovieThumnailMapper
import nando.android.movies.paging.MovieThumbnailDataSource
import nando.android.movies.paging.MovieThumbnailDataSourceFactory
import nando.android.movies.ui.moviesearch.MovieSearchListAdapter
import nando.android.movies.util.MoviesConstants.PAGE_SIZE
import nando.android.movies.viewmodel.MovieSearchViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module


val movieSearchModule = module {

    factory<Mapper<MovieSearchResult, MovieThumbnailModel>> {
        MovieThumnailMapper(
            get { parametersOf(DataSourceType.LOCAL) }
        )
    }

    viewModel {
        MovieSearchViewModel(
            providePagedConfig()
        )
    }

    factory { (scope: CoroutineScope, query: String) ->
        val dataSource = get<MovieThumbnailDataSource>{ parametersOf(scope, query)}
        provideMovieSearchDataFactory(dataSource, scope)
    }

    factory { (scope: CoroutineScope, query: String) ->
        provideMovieThumbnailPagedDataSource(
            get(),scope, get(), query
        )
    }

    factory<PagedListAdapter<MovieThumbnailModel, MovieSearchListAdapter.MovieViewHolder>> {
        MovieSearchListAdapter()
    }
}

fun provideMovieSearchDataFactory(dataSource: MovieThumbnailDataSource, scope: CoroutineScope) =
    MovieThumbnailDataSourceFactory(dataSource, scope)

fun provideMovieThumbnailPagedDataSource(
    repository: MovieSearchRepository,
    scope: CoroutineScope,
    mapper: Mapper<MovieSearchResult, MovieThumbnailModel>,
    query: String
) = MovieThumbnailDataSource(
    repository,
    scope,
    mapper,
    query
)

fun providePagedConfig() = PagedList.Config.Builder()
    .setEnablePlaceholders(false)
    .setInitialLoadSizeHint(PAGE_SIZE)
    .setPageSize(PAGE_SIZE).build()