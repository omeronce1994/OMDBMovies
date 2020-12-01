package nando.android.core.model.network.interceptors

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

/**
 * Interceptor to add api key to each omdb api call
 */
class OmdbInterceptor(
    private val apiKey: String
): Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val originalHttpUrl = original.url
        val url = originalHttpUrl.newBuilder()
            .addQueryParameter("apikey", apiKey)
            .build()
        val requestBuilder = original.newBuilder()
            .url(url)
        val request: Request = requestBuilder.build()
        return chain.proceed(request)

    }
}