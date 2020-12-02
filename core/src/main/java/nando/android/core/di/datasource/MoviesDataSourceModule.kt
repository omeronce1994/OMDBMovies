package nando.android.core.di.datasource

import nando.android.core.data.datasource.DataSourceType
import nando.android.core.data.datasource.moviedetails.LocalMovieDetailsDataSource
import nando.android.core.data.datasource.moviedetails.MovieDetailsDataSource
import nando.android.core.data.datasource.moviedetails.RemoteMovieDetailsDataSource
import nando.android.core.data.datasource.moviesearch.MovieSearchDataSource
import nando.android.core.data.datasource.moviesearch.RemoteMovieSearchDataSource
import org.koin.dsl.module

internal val moviesDataSourceModule = module {

    factory<MovieSearchDataSource> {
        RemoteMovieSearchDataSource(get())
    }

    /**
     * factory to choose wich data source instance to inject
     */
    factory<MovieDetailsDataSource> { (type: DataSourceType) ->
        when (type) {
            DataSourceType.LOCAL -> get<LocalMovieDetailsDataSource>()
            else -> RemoteMovieDetailsDataSource(get(), get())
        }
    }

    /**
     * Local db will be used as singleton
     */
    single {
        LocalMovieDetailsDataSource(get())
    }
}