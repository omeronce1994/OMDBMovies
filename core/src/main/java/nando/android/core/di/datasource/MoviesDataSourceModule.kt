package nando.android.core.di.datasource

import nando.android.core.data.datasource.DataSourceType
import nando.android.core.data.datasource.moviedetails.LocalMovieDetailsDataSource
import nando.android.core.data.datasource.moviedetails.MovieDetailsDataSource
import nando.android.core.data.datasource.moviedetails.RemoteMovieDetailsDataSource
import org.koin.dsl.module

val moviesDataSourceModule = module {

    factory<MovieDetailsDataSource> { (type: DataSourceType) ->
        when(type) {
            DataSourceType.LOCAL -> get<LocalMovieDetailsDataSource>()
            else -> RemoteMovieDetailsDataSource(get(), get())
        }
    }
    single {
        LocalMovieDetailsDataSource(get())
    }
}