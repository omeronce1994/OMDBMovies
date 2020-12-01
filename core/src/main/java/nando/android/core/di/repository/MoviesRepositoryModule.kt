package nando.android.core.di.repository

import nando.android.core.data.datasource.DataSourceType
import nando.android.core.data.datasource.moviedetails.MovieDetailsDataSource
import nando.android.core.data.repository.moviedetails.MovieDetailsRepository
import nando.android.core.data.repository.moviedetails.MovieDetailsRepositoryImpl
import nando.android.core.data.repository.moviesearch.MovieSearchRepository
import nando.android.core.data.repository.moviesearch.MovieSearchRepositoryImpl
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module


internal val moviesRepositoryModule = module {

    factory<MovieDetailsRepository> {
        MovieDetailsRepositoryImpl(
            get<MovieDetailsDataSource> { parametersOf(DataSourceType.LOCAL) },
            get<MovieDetailsDataSource> { parametersOf(DataSourceType.REMOTE) }
        )
    }

    factory<MovieSearchRepository> {
        MovieSearchRepositoryImpl(get())
    }
}