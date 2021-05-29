package edu.calpoly.flipted.ui.progress.targets

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.commit
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import edu.calpoly.flipted.businesslogic.targets.CalculateMastery
import edu.calpoly.flipted.businesslogic.targets.TargetProgress
import edu.calpoly.flipted.ui.common.MasteryResources
import edu.calpoly.flipted.ui.progress.R
import edu.calpoly.flipted.viewmodels.NavViewModel

class LearningTargetsViewHolder(view: View, private val fragment: LearningTargetProgressFragment): RecyclerView.ViewHolder(view), View.OnClickListener {
    private val root = view as CardView
    private val masteryIndicator:ImageView = view.findViewById(R.id.learning_target_card_mastery_indicator)
    private val masteryText:TextView = view.findViewById(R.id.learning_target_progress_name)
    private val learningTargetTitle:TextView = view.findViewById(R.id.learning_target_card_title)

    private val navViewModel = ViewModelProvider(fragment.requireActivity())[NavViewModel::class.java]

    private lateinit var targetId: String

    fun bind(target: TargetProgress) {
        targetId = target.target.uid

        learningTargetTitle.text = target.target.targetName

        val mastery = CalculateMastery.calculate(target)
        val colorResource = MasteryResources.colorResource(mastery)
        val color = ResourcesCompat.getColor(fragment.resources, colorResource, null)
        masteryIndicator.setColorFilter(color)

        val stringResource = MasteryResources.stringResource(mastery)
        masteryText.setText(stringResource)

        root.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        fragment.parentFragment?.parentFragmentManager?.let {
            navViewModel.navigator.openLearningTargetProgressDetailsFragment(it, targetId)
        }
    }
}

class LearningTargetsRecyclerViewAdapter(private val fragment: LearningTargetProgressFragment): RecyclerView.Adapter<LearningTargetsViewHolder>(){
    var targets:List<TargetProgress> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LearningTargetsViewHolder {
        val itemView = LayoutInflater.from(fragment.requireActivity()).inflate(R.layout.learning_target_progress_item, parent, false)
        return LearningTargetsViewHolder(itemView, fragment)
    }

    override fun getItemCount(): Int = targets.size

    override fun onBindViewHolder(holder: LearningTargetsViewHolder, position: Int) {
        holder.bind(targets[position])
    }

}