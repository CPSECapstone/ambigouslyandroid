package edu.calpoly.flipted.backend

import android.util.Log
import com.amplifyframework.auth.cognito.AWSCognitoAuthSession
import com.amplifyframework.auth.result.AuthSessionResult
import com.amplifyframework.kotlin.core.Amplify
import com.apollographql.apollo.ApolloClient
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response

abstract class ApolloRepo {
    companion object {
        const val BACKEND_URL = "https://bz4ubl4t4e.execute-api.us-east-1.amazonaws.com/dev/graphql"
    }

    private class AuthorizationInterceptor(val key: String) : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            val request = chain.request().newBuilder()
                    .addHeader("Authorization", key ?: "")
                    .build()

            return chain.proceed(request)
        }
    }

    suspend fun apolloClient(): ApolloClient {

        val session = Amplify.Auth.fetchAuthSession() as AWSCognitoAuthSession

        val id = session.userPoolTokens

        if (id.type == AuthSessionResult.Type.SUCCESS) {
            Log.i("AuthQuickStart", "IdentityId: ${id.value}")
        } else if (id.type == AuthSessionResult.Type.FAILURE) {
            Log.i("AuthQuickStart", "IdentityId not present: ${id.error}")
            throw IllegalStateException();
        }

        val key = id.value!!.accessToken

        return ApolloClient.builder()
                .serverUrl(BACKEND_URL)
                .okHttpClient(OkHttpClient.Builder()
                        .addInterceptor(AuthorizationInterceptor(key))
                        .build()
                ).build()
    }
}