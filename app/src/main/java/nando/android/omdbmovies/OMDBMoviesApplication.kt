package nando.android.omdbmovies

import android.app.Application
import nando.android.core.di.coreModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class OMDBMoviesApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@OMDBMoviesApplication)
            modules(coreModules)
        }
    }
}