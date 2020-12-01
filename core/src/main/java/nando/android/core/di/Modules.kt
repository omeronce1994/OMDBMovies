package nando.android.core.di

import nando.android.core.di.datasource.moviesDataSourceModule
import nando.android.core.di.db.coreDbModule
import nando.android.core.di.mapper.coreMapperModule
import nando.android.core.di.network.networkModule
import nando.android.core.di.repository.moviesRepositoryModule

val coreModules = listOf(
    coreDbModule,
    networkModule,
    coreMapperModule,
    moviesDataSourceModule,
    moviesRepositoryModule
)