package edu.calpoly.flipted.ui.missions

import android.graphics.Typeface
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat
import android.widget.Toast
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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import edu.calpoly.flipted.ui.myProgress.missions.MissionsViewModel

private const val MISSION_ID_ARG_PARAM = "missionId"

/**
 * A simple [Fragment] subclass.
 * Use the [MissionFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MissionFragment : Fragment() {
    private lateinit var missionId: String
    private lateinit var viewModel: MissionsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            missionId = it.getString(MISSION_ID_ARG_PARAM)
                ?: throw IllegalArgumentException("Missing parameter")
        } ?: throw IllegalArgumentException("Missing parameter")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        inflater.inflate(R.layout.mission_fragment, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val taskInfo: View = view.findViewById(R.id.task_info_card)
        val taskTitle: TextView = view.findViewById(R.id.task_name)
        val taskDesc: TextView = view.findViewById(R.id.task_insts)
        val continueBtn: Button = view.findViewById(R.id.task_start_button)
        val listViewTask: ListView = view.findViewById(R.id.learning_objectives_list)
        val taskFeedback: TextView = view.findViewById(R.id.task_feedback)
        val feedbackTitle: TextView = view.findViewById(R.id.task_feedback_title)
        val reviewBtn: Button = view.findViewById(R.id.task_review_button)
        val taskList: RecyclerView = view.findViewById(R.id.mission_tasks_recyclerview)

        viewModel = ViewModelProvider(requireActivity())[MissionsViewModel::class.java]

        val adapter = MissionTaskRecyclerAdapter(this, viewModel)

        val infoAdapter: CustomListAdapter = CustomListAdapter()
        listViewTask.adapter = infoAdapter


        taskList.adapter = adapter
        taskList.layoutManager = LinearLayoutManager(requireActivity())
        taskList.addItemDecoration(MissionTasksItemDecoration(this))
        taskInfo.visibility = View.GONE

        viewModel.currTaskInfo.observe(viewLifecycleOwner, Observer {
            if (it == null) {
                taskInfo.visibility = View.GONE
            } else {
                val currTaskInfo = viewModel.currTaskInfo.value ?: return@Observer

                if (viewModel.currTaskInfo.value?.task?.id != currTaskInfo.task.id)
                    return@Observer

                val currSparseTask = currTaskInfo.task
                val taskViewModel =
                    ViewModelProvider(requireActivity())[TaskViewModel::class.java]
                viewModel.taskObjectives.observe(viewLifecycleOwner, {
                    val taskObjectives = viewModel.taskObjectives.value
                        ?: throw IllegalArgumentException("Null task objective")
                    infoAdapter.data = taskObjectives
                    taskViewModel.setTaskObjectives(viewModel.taskObjectives.value!!)
                })


                taskTitle.text = currSparseTask.name
                taskTitle.movementMethod = ScrollingMovementMethod()
                taskDesc.text = currSparseTask.instructions
                taskDesc.movementMethod = ScrollingMovementMethod()


                val currTaskProgress = currTaskInfo.submission
                if (currTaskProgress != null) {
                    Log.e("tag", currTaskProgress.taskId)
                    taskViewModel.retrieveTaskSubmission(currTaskProgress.taskId)
                    continueBtn.text = "Redo Task"
                    taskFeedback.text = if (!currTaskProgress.graded) {
                        "No feedback is available; Task is not fully graded."
                    } else if (currTaskProgress.teacherComment == null) {
                        "No feedback is available."
                    } else {
                        currTaskProgress.teacherComment
                    }

                    if (!currTaskProgress.graded) {
                        taskFeedback.setTypeface(null, Typeface.ITALIC)
                    }

                    taskFeedback.movementMethod = ScrollingMovementMethod()
                    taskFeedback.visibility = View.VISIBLE
                    feedbackTitle.visibility = View.VISIBLE
                    reviewBtn.visibility = View.VISIBLE

                    reviewBtn.setOnClickListener {
                        taskViewModel.taskAndResponseValid.observe(viewLifecycleOwner, { valid ->
                            if (valid) {
                                parentFragmentManager.commit {
                                    replace(
                                        R.id.main_view,
                                        ReviewResultsFragment.newInstance()
                                    )
                                    setReorderingAllowed(true)
                                    addToBackStack("Start task")
                                }
                            }
                        })
                    }
                } else {
                    continueBtn.text = "Continue Task"
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



        viewModel.missionsProgress.observe(viewLifecycleOwner, Observer {
            viewModel.setCurrMissionId(missionId)
            val mission = it[missionId]
            if(mission == null)
                Toast.makeText(requireActivity(), "Failed to retrieve mission!", Toast.LENGTH_LONG).show()
            adapter.data = mission
            adapter.notifyDataSetChanged()
        })

        viewModel.fetchMissionsProgress()
    }

    companion object {
        @JvmStatic
        fun newInstance(missionId: String) = MissionFragment().apply {
            arguments = Bundle().apply {
                putString(MISSION_ID_ARG_PARAM, missionId)
            }
        }
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