package edu.calpoly.flipted.ui.missions

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.amplifyframework.auth.AuthUserAttributeKey
import edu.calpoly.flipted.R
import edu.calpoly.flipted.ui.goals.GoalsFragment
import edu.calpoly.flipted.ui.login.LoginFragment
import edu.calpoly.flipted.ui.login.LoginViewModel
import edu.calpoly.flipted.ui.tasks.TaskFragment

/**
 * A simple [Fragment] subclass.
 * Use the [MissionFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MissionFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView : RecyclerView = view.findViewById(R.id.fragment_mission_recycler_view)
        val adapter = MissionsRecyclerViewAdapter(requireActivity())
        recyclerView.adapter = adapter
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_mission, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance() = MissionFragment()
        }
}