package edu.calpoly.flipted.ui.tasks

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ListView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import edu.calpoly.flipted.R
import edu.calpoly.flipted.businesslogic.UidToStableId
import edu.calpoly.flipted.businesslogic.targets.Mastery
import edu.calpoly.flipted.businesslogic.targets.TaskObjectiveProgress
import edu.calpoly.flipted.ui.MasteryResources


class TaskResultsSummaryFragment : Fragment() {

    private lateinit var viewModel: TaskViewModel
    private lateinit var adapter: CustomListAdapter

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.task_results_fragment_summary, container, false)



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[TaskViewModel::class.java]

        val listViewTask: ListView = view.findViewById(R.id.learning_objectives_list)
        val taskPercentage: TextView = view.findViewById(R.id.total_percentage)
        val taskProgressbarScore: ProgressBar = view.findViewById(R.id.results_review_progress_bars_percent)

        adapter = CustomListAdapter()
        listViewTask.adapter = adapter

        val taskObjectives = viewModel.taskObjectives.value ?: throw IllegalArgumentException("Null task objective")
        val taskResults = viewModel.currResponse.value ?: throw IllegalArgumentException("Null task results")

        adapter.data = taskObjectives
        taskPercentage.text = "${(((taskResults.pointsAwarded.toFloat() / taskResults.pointsPossible.toFloat()) * 100).toInt().toString())}%"
        val progressVal = ((taskResults.pointsAwarded.toFloat() / taskResults.pointsPossible.toFloat()) * 100).toInt()
        taskProgressbarScore.progress = progressVal
        if (progressVal <= 50){
            taskProgressbarScore.progressDrawable = context.let { ContextCompat.getDrawable(it!!, R.drawable.progress_bar_failed) }
        }
        if (progressVal < 80 && progressVal >= 50){
            taskProgressbarScore.progressDrawable = context.let { ContextCompat.getDrawable(it!!, R.drawable.progress_bar_almost_pass) }
        }

    }

    companion object {
        @JvmStatic
        fun newInstance() =
                TaskResultsSummaryFragment()
    }

inner class CustomListAdapter(


    ) : BaseAdapter() {

        var data: List<TaskObjectiveProgress> = listOf()
            set(value) {
                field = value
                notifyDataSetChanged()
            }

        private val uidMap = UidToStableId<String>()

        override fun getCount(): Int {
            return data.size
        }

        override fun getItem(position: Int): TaskObjectiveProgress {
            return data[position]
        }

        override fun getItemId(position: Int): Long {
            return uidMap.getStableId(data[position].taskId)
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val fillInView = convertView
                    ?: layoutInflater.inflate(R.layout.learning_objective_list_item, parent, false)
            val textBox: TextView = fillInView.findViewById(R.id.learning_objective_item_mastery_name)
            val data = getItem(position)

            textBox.text = data.objectiveName

            val colorResource = MasteryResources.colorResource(data.mastery)
            val color = ResourcesCompat.getColor(requireActivity().resources, colorResource, null)
            textBox.background.setTint(color)

            return fillInView


        }


    }






}