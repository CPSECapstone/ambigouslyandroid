package edu.calpoly.flipted.ui.login

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.amplifyframework.auth.AuthProvider
import com.amplifyframework.core.Amplify
import edu.calpoly.flipted.R
import edu.calpoly.flipted.ui.home.StudentHomeFragment


class LoginFragment : Fragment() {

    private lateinit var loginWithGoogleButton : Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        inflater.inflate(R.layout.fragment_login, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loginWithGoogleButton = view.findViewById(R.id.login_google_button)

        loginWithGoogleButton.setOnClickListener {
            Amplify.Auth.signInWithSocialWebUI(AuthProvider.google(), requireActivity(),
                {
                    Log.i("LoginFragment", "Sign in OK: $it")
                    parentFragmentManager.beginTransaction().replace(R.id.main_view, StudentHomeFragment.newInstance()).commitNow()
                },
                { Log.e("LoginFragment", "Sign in failed", it) }
            )
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = LoginFragment()
    }
}