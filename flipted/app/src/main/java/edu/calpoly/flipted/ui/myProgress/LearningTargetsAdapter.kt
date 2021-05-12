package edu.calpoly.flipted.ui.myProgress

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import edu.calpoly.flipted.R
import edu.calpoly.flipted.businesslogic.learningTargets.LearningTarget
import edu.calpoly.flipted.businesslogic.learningTargets.TargetProgress
import edu.calpoly.flipted.businesslogic.tasks.data.blocks.TaskBlock


class LearningTargetsAdapter (
        private val fragment: Fragment
) : RecyclerView.Adapter<LearningTargetViewHolder>() {

    var selectedTargets : List<TargetProgress> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    private val inflater = fragment.layoutInflater
    private val viewModel = ViewModelProvider(fragment.requireActivity())[TargetsViewModel::class.java]


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LearningTargetViewHolder {
        val inflatedView = inflater.inflate(viewType, parent, false)
        return when (viewType) {
            R.layout.task_block_quiz -> LearningTargetViewHolder(inflatedView, fragment.requireContext())
            else -> throw IllegalArgumentException("Unknown viewType")
        }
    }

    override fun onBindViewHolder(holder: LearningTargetViewHolder, position: Int) {
        holder.bind(selectedTargets[position])
    }

    override fun getItemCount(): Int = selectedTargets.size

}