package nando.android.core.di.mapper

import nando.android.core.mapper.MovieEntityMapper
import org.koin.dsl.module

internal val coreMapperModule = module {
    single { MovieEntityMapper() }
}