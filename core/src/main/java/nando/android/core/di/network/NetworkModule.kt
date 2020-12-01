package nando.android.core.di.network

import nando.android.core.model.network.interceptors.OmdbInterceptor
import nando.android.core.model.network.service.OMDBService
import nando.android.core.util.CoreConstants.API_KEY
import nando.android.core.util.CoreConstants.OMDB_BASE_API
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {

    single {
        OmdbInterceptor(API_KEY)
    }

    single {
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    single {
        OkHttpClient.Builder()
            .addInterceptor(get<OmdbInterceptor>())
            .addInterceptor(get<HttpLoggingInterceptor>())
            .build()
    }

    single {
        Retrofit.Builder()
            .baseUrl(OMDB_BASE_API)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single {
        get<Retrofit>().create(OMDBService::class.java)
    }
}