package edu.calpoly.flipted

import android.app.Application
import android.util.Log
import com.amplifyframework.AmplifyException
import com.amplifyframework.core.Amplify

class FliptedApp : Application() {

    override fun onCreate() {
        super.onCreate()

        try {
            Amplify.configure(applicationContext)
            Log.i("FliptedApplication", "Initialized Amplify")
        } catch (error: AmplifyException) {
            Log.e("FliptedApplication", "Could not initialize Amplify", error)
        }
    }
}