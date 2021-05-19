package edu.calpoly.flipted.ui.myProgress.missions

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.commit
import androidx.recyclerview.widget.RecyclerView
import edu.calpoly.flipted.R
import edu.calpoly.flipted.businesslogic.missions.MissionProgress
import edu.calpoly.flipted.businesslogic.targets.CalculateMastery
import edu.calpoly.flipted.businesslogic.targets.TargetProgress
import edu.calpoly.flipted.ui.MasteryResources
import edu.calpoly.flipted.ui.myProgress.targets.LearningTargetDetailFragment
import edu.calpoly.flipted.ui.myProgress.targets.LearningTargetProgressFragment

class LearningTargetsViewHolder(view: View, private val fragment: LearningTargetProgressFragment): RecyclerView.ViewHolder(view) {
    private val masteryIndicator:ImageView = view.findViewById(R.id.learning_target_card_mastery_indicator)
    private val masteryText:TextView = view.findViewById(R.id.learning_target_progress_name)
    private val learningTargetTitle:TextView = view.findViewById(R.id.learning_target_card_title)

    fun bind(target: TargetProgress) {
        learningTargetTitle.text = target.target.targetName

        val mastery = CalculateMastery.calculate(target)
        val colorResource = MasteryResources.colorResource(mastery)
        val color = ResourcesCompat.getColor(fragment.resources, colorResource, null)
        masteryIndicator.setColorFilter(color)

        val stringResource = MasteryResources.stringResource(mastery)
        masteryText.setText(stringResource)
    }
}

class LearningTargetsRecyclerViewAdapter(private val context: Context, private val fragment: LearningTargetProgressFragment): RecyclerView.Adapter<LearningTargetsViewHolder>(){
    var targets:List<TargetProgress> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LearningTargetsViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.learning_target_progress_item, parent, false)
        return LearningTargetsViewHolder(itemView, fragment)
    }

    override fun getItemCount(): Int = targets.size

    override fun onBindViewHolder(holder: LearningTargetsViewHolder, position: Int) {
        holder.bind(targets[position])

        val learningTargetDetailButton = holder.itemView.findViewById<TextView>(R.id.learning_target_card_title)

        learningTargetDetailButton.setOnClickListener {
            fragment.parentFragment?.parentFragmentManager?.commit{
                replace(R.id.main_view, LearningTargetDetailFragment.newInstance(targets[position].target.uid))}
        }
    }

}