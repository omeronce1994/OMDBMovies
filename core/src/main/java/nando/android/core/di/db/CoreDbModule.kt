package nando.android.core.di.db

import androidx.room.Room
import nando.android.core.model.db.MoviesDatabase
import nando.android.core.util.CoreConstants.MOVIES_DB_NAME
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

internal val coreDbModule = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            MoviesDatabase::class.java,
            MOVIES_DB_NAME
        ).build()
    }

    single {
        get<MoviesDatabase>().moviesDao()
    }
}