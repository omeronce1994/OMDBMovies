package nando.android.omdbmovies

import android.app.Application
import nando.android.core.di.coreModules
import nando.android.movies.di.moviesModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.core.module.Module
import org.koin.core.module.plus

class OMDBMoviesApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@OMDBMoviesApplication)
            modules(getKoinModules())
        }
    }

    private fun getKoinModules(): List<Module> = mutableListOf<Module>().apply {
        addAll(coreModules)
        addAll(moviesModules)
    }
}