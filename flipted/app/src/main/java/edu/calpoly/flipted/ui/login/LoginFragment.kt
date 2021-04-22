package edu.calpoly.flipted.ui.login

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.amplifyframework.auth.AuthProvider
import com.amplifyframework.core.Amplify
import edu.calpoly.flipted.R
import edu.calpoly.flipted.ui.home.StudentHomeFragment


class LoginFragment : Fragment() {
    private lateinit var viewModel : LoginViewModel
    private lateinit var loginWithGoogleButton : Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        inflater.inflate(R.layout.fragment_login, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity())[LoginViewModel::class.java]
        viewModel.isLoggedIn.observe(viewLifecycleOwner, Observer {
            if(it)
                parentFragmentManager.commit {
                    replace(R.id.main_view, StudentHomeFragment.newInstance())
                    setReorderingAllowed(true)
                }
        })

        loginWithGoogleButton = view.findViewById(R.id.login_google_button)
        loginWithGoogleButton.setOnClickListener {
            viewModel.logInFlow(requireActivity())
        }

    }

    companion object {
        @JvmStatic
        fun newInstance() = LoginFragment()
    }
}