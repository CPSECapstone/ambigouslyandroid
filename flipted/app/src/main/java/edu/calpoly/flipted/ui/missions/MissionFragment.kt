package edu.calpoly.flipted.ui.missions

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import edu.calpoly.flipted.R
import edu.calpoly.flipted.businesslogic.UidToStableId
import edu.calpoly.flipted.businesslogic.targets.Mastery
import edu.calpoly.flipted.businesslogic.targets.TaskObjectiveProgress
import edu.calpoly.flipted.ui.tasks.ReviewResultsFragment
import edu.calpoly.flipted.ui.tasks.TaskFragment
import edu.calpoly.flipted.ui.tasks.TaskViewModel

/**
 * A simple [Fragment] subclass.
 * Use the [MissionFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MissionFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        inflater.inflate(R.layout.fragment_mission, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewModel: MissionsViewModel =
            ViewModelProvider(requireActivity())[MissionsViewModel::class.java]
        val taskInfo: View = view.findViewById(R.id.task_info_card)
        val taskTitle: TextView = view.findViewById(R.id.task_name)
        val taskDesc: TextView = view.findViewById(R.id.task_insts)
        val continueBtn: Button = view.findViewById(R.id.task_start_button)
        val listViewTask: ListView = view.findViewById(R.id.learning_objectives_list)
        val taskFeedback: TextView = view.findViewById(R.id.task_feedback)
        val feedbackTitle: TextView = view.findViewById(R.id.task_feedback_title)
        val reviewBtn: Button = view.findViewById(R.id.task_review_button)
        val progressBar: ProgressBar = view.findViewById(R.id.task_info_progressbar)

        val adapter: CustomListAdapter = CustomListAdapter()
        listViewTask.adapter = adapter

        // mock task id for task 1
        val taskOneButton = view.findViewById<Button>(R.id.taskOneButton)
        taskOneButton.setOnClickListener {
            viewModel.fetchTaskInfo("4f681550ba9")
            taskInfo.visibility = View.GONE
            progressBar.visibility = View.VISIBLE
        }

        // mock task id for task 2
        val taskTwoButton = view.findViewById<Button>(R.id.taskTwoButton)
        taskTwoButton.setOnClickListener {
            viewModel.fetchTaskInfo("90e0c730e56")
            taskInfo.visibility = View.GONE
            progressBar.visibility = View.VISIBLE
        }



        viewModel.currTaskInfo.observe(viewLifecycleOwner, Observer {
            if (it == null) {
                progressBar.visibility = View.VISIBLE
                taskInfo.visibility = View.GONE
            } else {
                progressBar.visibility = View.GONE
                val currTaskInfo = viewModel.currTaskInfo.value ?: return@Observer

                if (viewModel.currTaskInfo.value?.task?.id != currTaskInfo.task.id)
                    return@Observer

                val currSparseTask = currTaskInfo.task
                viewModel.taskObjectives.observe(viewLifecycleOwner, {
                    val taskObjectives = viewModel.taskObjectives.value
                        ?: throw IllegalArgumentException("Null task objective")
                    adapter.data = taskObjectives
                })


                taskTitle.text = currSparseTask.name
                taskDesc.text = currSparseTask.instructions

                val currTaskProgress = currTaskInfo.submission
                if (currTaskProgress != null && currTaskProgress.graded) {
                    continueBtn.text = "Redo Task"
                    taskFeedback.text = if (currTaskProgress.teacherComment == null) {
                        "No feedback given."
                    } else {
                        currTaskProgress.teacherComment
                    }


                    taskFeedback.visibility = View.VISIBLE
                    feedbackTitle.visibility = View.VISIBLE
                    reviewBtn.visibility = View.VISIBLE

                    val taskViewModel =
                        ViewModelProvider(requireActivity())[TaskViewModel::class.java]
                    taskViewModel.setTaskSubmission(currTaskProgress)

                    reviewBtn.setOnClickListener {
                        parentFragmentManager.commit {
                            replace(R.id.main_view, ReviewResultsFragment.newInstance())
                            setReorderingAllowed(true)
                            addToBackStack("Start task")
                        }
                    }
                } else {
                    taskFeedback.visibility = View.GONE
                    feedbackTitle.visibility = View.GONE
                    reviewBtn.visibility = View.GONE
                }

                taskInfo.visibility = View.VISIBLE


                continueBtn.setOnClickListener {
                    parentFragmentManager.commit {
                        replace(R.id.main_view, TaskFragment.newInstance(currTaskInfo.task.id))
                        setReorderingAllowed(true)
                        addToBackStack("Start task")
                    }
                }
            }

        })


    }

    companion object {
        @JvmStatic
        fun newInstance() = MissionFragment()
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
            val textBox: TextView =
                fillInView.findViewById(R.id.learning_objective_item_mastery_name)
            val data = getItem(position)

            textBox.text = data.objectiveName

            if (data.mastery == Mastery.NOT_MASTERED) {
                textBox.background = context.let {
                    ContextCompat.getDrawable(
                        it!!,
                        R.drawable.learning_objective_color_box_not_mastered
                    )
                }
            }
            if (data.mastery == Mastery.NEARLY_MASTERED) {
                textBox.background = context.let {
                    ContextCompat.getDrawable(
                        it!!,
                        R.drawable.learning_objective_color_box_nearly_mastered
                    )
                }
            }
            if (data.mastery == Mastery.MASTERED) {
                textBox.background = context.let {
                    ContextCompat.getDrawable(
                        it!!,
                        R.drawable.learning_objective_color_box_mastered
                    )
                }
            }

            return fillInView


        }


    }
}