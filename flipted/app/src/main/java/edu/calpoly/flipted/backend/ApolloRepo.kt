package edu.calpoly.flipted.backend

import android.util.Log
import com.amplifyframework.auth.cognito.AWSCognitoAuthSession
import com.amplifyframework.auth.result.AuthSessionResult
import com.amplifyframework.kotlin.core.Amplify
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.CustomTypeAdapter
import com.apollographql.apollo.api.CustomTypeValue
import edu.calpoly.flipted.type.CustomType
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import java.text.SimpleDateFormat
import java.util.*

abstract class ApolloRepo {
    companion object {
        const val BACKEND_URL = "https://5orf8nzr57.execute-api.us-east-1.amazonaws.com/production/graphql"
    }

    private val dateCustomTypeAdapter = object: CustomTypeAdapter<Date> {
        private val dateFormat = SimpleDateFormat("y-MM-dd", Locale.US)
        override fun decode(value: CustomTypeValue<*>): Date {
            return dateFormat.parse(value.value.toString()) ?: throw IllegalArgumentException("Bad date")
        }

        override fun encode(value: Date): CustomTypeValue<*> {
            return CustomTypeValue.GraphQLString(dateFormat.format(value))
        }
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
        Log.e("tag", key)

        return ApolloClient.builder()
                .serverUrl(BACKEND_URL)
                .addCustomTypeAdapter(CustomType.DATE, dateCustomTypeAdapter)
                .okHttpClient(OkHttpClient.Builder()
                        .addInterceptor(AuthorizationInterceptor(key))
                        .build()
                )
                .build()
    }
}