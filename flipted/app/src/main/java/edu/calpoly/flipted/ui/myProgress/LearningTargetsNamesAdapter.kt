package edu.calpoly.flipted.ui.myProgress

import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import edu.calpoly.flipted.R


class LearningTargetsNamesAdapter (
        private val fragment: Fragment
) : RecyclerView.Adapter<LearningTargetNameViewholder>() {

    private val inflater = fragment.layoutInflater
    //private val viewModel = ViewModelProvider(fragment.requireActivity())[TaskViewModel::class.java]

    var targetNames : List<String> = mutableListOf("1", "2")

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskBlockViewHolder {
        val inflatedView = inflater.inflate(viewType, parent, false)
        return when (viewType) {
            R.layout.task_block_quiz -> ResultBlockViewHolder(inflatedView, inflater, viewModel)
            else -> throw IllegalArgumentException("Unknown viewType")
        }
    }

    override fun onBindViewHolder(holder: TaskBlockViewHolder, position: Int) {
        holder.bind(taskBlocks[position], position)
    }

    override fun getItemCount(): Int = targetNames.size

}