package edu.calpoly.flipted.backend

import com.apollographql.apollo.ApolloClient
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response

abstract class ApolloRepo {
    companion object {
        const val BACKEND_URL = "https://f6t0mvy5y0.execute-api.us-east-1.amazonaws.com/dev/graphql"
    }

    private class AuthorizationInterceptor(val key: String): Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            val request = chain.request().newBuilder()
                    .addHeader("Authorization", key ?: "")
                    .build()

            return chain.proceed(request)
        }
    }

    protected fun unauthenticatedApolloClient() : ApolloClient = ApolloClient.builder()
            .serverUrl(BACKEND_URL)
            .build()

    protected fun apolloClient(key: String) : ApolloClient = ApolloClient.builder()
            .serverUrl(BACKEND_URL)
            .okHttpClient(OkHttpClient.Builder()
                .addInterceptor(AuthorizationInterceptor(key))
                .build()
            ).build()
}