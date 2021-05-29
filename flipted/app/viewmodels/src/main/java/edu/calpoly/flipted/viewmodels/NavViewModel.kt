package edu.calpoly.flipted.viewmodels

import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModel

class NavViewModel(val navigator: Navigator) : ViewModel() {
    interface Navigator {
        fun openTaskFragment(fragmentManager: FragmentManager, taskUid: String)
        fun openTaskResultsFragment(fragmentManager: FragmentManager)
        fun openMissionFragment(fragmentManager: FragmentManager, missionId: String)
        fun openMissionProgressDetailsFragment(fragmentManager: FragmentManager, missionId: String)
        fun openLearningTargetProgressDetailsFragment(fragmentManager: FragmentManager, targetId: String)
    }
}