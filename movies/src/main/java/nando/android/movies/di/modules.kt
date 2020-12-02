package nando.android.movies.di

import nando.android.movies.di.moviedetails.movieDetailsModule
import nando.android.movies.di.moviesearch.movieSearchModule

val moviesModules = listOf(movieSearchModule, movieDetailsModule)