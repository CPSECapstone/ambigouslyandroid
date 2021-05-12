package edu.calpoly.flipted.ui.myProgress

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import androidx.fragment.app.commit
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import edu.calpoly.flipted.R
import edu.calpoly.flipted.businesslogic.tasks.data.RubricRequirement
import edu.calpoly.flipted.ui.tasks.TaskPagerAdapter
import edu.calpoly.flipted.ui.tasks.TaskViewModel
import edu.calpoly.flipted.ui.tasks.rubric.TaskRubricFragment

class LearningTargetsFragment : Fragment() {

    private lateinit var progressBar: ProgressBar
    private lateinit var targetNamesView: FragmentContainerView
    private lateinit var targetlistView: RecyclerView
    private lateinit var viewModel: TargetsViewModel
    private lateinit var allTargets: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_progress, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity())[TargetsViewModel::class.java]

        progressBar = view.findViewById(R.id.learning_target_progressbar)
        targetlistView = view.findViewById(R.id.learning_targets_container)
        targetNamesView = view.findViewById(R.id.learning_target_names_container)

        allTargets = view.findViewById(R.id.all_targets_text)

        val targetsAdapter = LearningTargetsAdapter(this)


        viewModel.selectedTargetList.observe(viewLifecycleOwner, Observer { selectedList ->
            val allProgress = viewModel.allProgress.value ?: throw IllegalArgumentException("Null all progress")
            val uids = selectedList!!.map{it.targetName}
            targetsAdapter.selectedTargets = allProgress.filter{it.target.uid in uids}

                childFragmentManager.commit {
                    replace(R.id.learning_target_names_container, LearningTargetNamesFragment.newInstance())
                    setReorderingAllowed(true)
                }
            }
        )
    }

    companion object {
        @JvmStatic
        fun newInstance() =
                LearningTargetsFragment()
    }

}