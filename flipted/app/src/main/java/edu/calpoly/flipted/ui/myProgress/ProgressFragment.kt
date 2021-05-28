package edu.calpoly.flipted.ui.myProgress

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import edu.calpoly.flipted.R
import edu.calpoly.flipted.ui.myProgress.missions.MissionProgressFragment
import edu.calpoly.flipted.ui.myProgress.targets.LearningTargetDetailFragment
import edu.calpoly.flipted.ui.myProgress.targets.LearningTargetProgressFragment

class ProgressFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.progress_fragment, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tabs: TabLayout = view.findViewById(R.id.progress_pager_tabs)
        val pager: ViewPager2 = view.findViewById(R.id.progress_pager)

        pager.adapter = object: FragmentStateAdapter(this) {
            override fun getItemCount(): Int = 2

            override fun createFragment(position: Int): Fragment = when(position) {
                0 -> MissionProgressFragment.newInstance()
                1 -> LearningTargetProgressFragment.newInstance()
                else -> throw IllegalArgumentException("Invalid ViewPager page")
            }
        }

        TabLayoutMediator(tabs, pager) { tab, position ->
            tab.text = when (position) {
                0 -> "Missions"
                1 -> "Learning Targets"
                else -> throw IllegalArgumentException("Invalid TabLayoutMediator page")
            }
        }.attach()
    }

    companion object {
        @JvmStatic
        fun newInstance() = ProgressFragment()
    }
}