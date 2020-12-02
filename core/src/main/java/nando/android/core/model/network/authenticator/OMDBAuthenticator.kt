package nando.android.core.model.network.authenticator

import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route

class OMDBAuthenticator(private val apiKey: String): Authenticator {

    override fun authenticate(route: Route?, response: Response): Request? {
        val request = response.request
        val originalHttpUrl = request.url
        val url = originalHttpUrl.newBuilder()
            .addQueryParameter("apikey", apiKey)
            .build()
        val requestBuilder = request.newBuilder()
            .url(url)
        val newRequest: Request = requestBuilder.build()
        return newRequest
    }
}