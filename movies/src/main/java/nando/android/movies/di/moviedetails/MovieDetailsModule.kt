package nando.android.movies.di.moviedetails

import nando.android.core.data.datasource.DataSourceType
import nando.android.movies.model.moviedetails.mapper.MovieItemMapper
import nando.android.movies.model.moviedetails.mapper.MovieItemToMovieEntityMapper
import nando.android.movies.viewmodel.moviedetails.MovieDetailsViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module

val movieDetailsModule = module {

    factory{
        MovieItemMapper(
            get { parametersOf(DataSourceType.LOCAL) }
        )
    }

    factory {
        MovieItemToMovieEntityMapper()
    }

    viewModel {
        MovieDetailsViewModel(get(), get(), get())
    }
}