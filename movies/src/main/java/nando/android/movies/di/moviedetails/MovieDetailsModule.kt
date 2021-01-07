package nando.android.movies.di.moviedetails

import nando.android.movies.model.moviedetails.mapper.MovieModelToMovieDetailsMapper
import nando.android.movies.model.moviedetails.mapper.MovieDetailsToMovieModelMapper
import nando.android.movies.viewmodel.moviedetails.MovieDetailsViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val movieDetailsModule = module {

    factory{
        MovieModelToMovieDetailsMapper()
    }

    factory {
        MovieDetailsToMovieModelMapper()
    }

    viewModel {
        MovieDetailsViewModel(get(), get(), get())
    }
}