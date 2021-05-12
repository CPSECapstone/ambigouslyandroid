package edu.calpoly.flipted.ui.myProgress

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import edu.calpoly.flipted.R


class LearningTargetsAdapter (
        private val fragment: Fragment
) : RecyclerView.Adapter<LearningTargetsAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView

        init {
            // Define click listener for the ViewHolder's View.
            textView = view.findViewById(R.id.learning_target_name_text)
        }
    }

    private val inflater = fragment.layoutInflater
    //private val viewModel = ViewModelProvider(fragment.requireActivity())[TaskViewModel::class.java]

    var targetNames : List<String> = mutableListOf("1", "2")

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LearningTargetsAdapter.ViewHolder {
        val inflatedView = inflater.inflate(viewType, parent, false)
        return when (viewType) {
            R.layout.task_block_quiz -> LearningTargetsAdapter.ViewHolder(inflatedView, inflater, viewModel)
            else -> throw IllegalArgumentException("Unknown viewType")
        }
    }

    override fun onBindViewHolder(holder: TaskBlockViewHolder, position: Int) {
        holder.bind(taskBlocks[position], position)
    }

    override fun getItemCount(): Int = targetNames.size

}