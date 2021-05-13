package edu.calpoly.flipted.ui.myProgress.targets

import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import edu.calpoly.flipted.R
import edu.calpoly.flipted.businesslogic.targets.TargetProgress


class LearningTargetsAdapter (
        private val fragment: Fragment
) : RecyclerView.Adapter<LearningTargetViewHolder>() {

    var selectedTargets : List<TargetProgress> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    private val inflater = fragment.layoutInflater

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LearningTargetViewHolder {
        val itemView = inflater.inflate(R.layout.learning_target_item, parent, false)
        return LearningTargetViewHolder(itemView, fragment.requireActivity())
    }

    override fun onBindViewHolder(holder: LearningTargetViewHolder, position: Int) {
        holder.bind(selectedTargets[position])
    }

    override fun getItemCount(): Int = selectedTargets.size

}