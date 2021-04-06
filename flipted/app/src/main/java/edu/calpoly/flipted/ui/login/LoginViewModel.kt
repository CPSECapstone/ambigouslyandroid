package edu.calpoly.flipted.ui.login

import android.app.Activity
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amplifyframework.auth.AuthException
import com.amplifyframework.auth.AuthProvider
import com.amplifyframework.kotlin.core.Amplify
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    val isLoggedIn : LiveData<Boolean>
        get() = _isLoggedIn

    private val _isLoggedIn = MutableLiveData<Boolean>(false)

    fun logInFlow(callingActivity : Activity) {
        viewModelScope.launch {
            _isLoggedIn.value = try {
                val result = Amplify.Auth.signInWithSocialWebUI(AuthProvider.google(), callingActivity)
                result.isSignInComplete
            } catch (error: AuthException) {
                Log.e("AuthQuickstart", "Sign in failed", error)
                false
            }
        }
    }

}