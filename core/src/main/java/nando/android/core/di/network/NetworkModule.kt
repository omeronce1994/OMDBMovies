package nando.android.core.di.network

import nando.android.core.BuildConfig
import nando.android.core.model.network.interceptors.OmdbInterceptor
import nando.android.core.model.network.service.OMDBService
import nando.android.core.util.CoreConstants.API_KEY
import nando.android.core.util.CoreConstants.OMDB_BASE_API
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

internal val networkModule = module {

    single {
        OmdbInterceptor(API_KEY)
    }

    single {
        HttpLoggingInterceptor().apply {
            setLevel(HttpLoggingInterceptor.Level.BODY)
        }
    }

    single {
        OkHttpClient.Builder().apply {
            addInterceptor(get<OmdbInterceptor>())
            if (BuildConfig.DEBUG) {
                addInterceptor(get<HttpLoggingInterceptor>())
            }
        }.build()
    }

    single {
        Retrofit.Builder()
            .baseUrl(OMDB_BASE_API)
            .addConverterFactory(GsonConverterFactory.create())
            .client(get<OkHttpClient>())
            .build()
    }

    single {
        get<Retrofit>().create(OMDBService::class.java)
    }
}