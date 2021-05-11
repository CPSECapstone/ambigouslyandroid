package edu.calpoly.flipted.ui.myProgress

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import edu.calpoly.flipted.R
import edu.calpoly.flipted.businesslogic.targets.*

class MyProgressFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_progress, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView : RecyclerView = view.findViewById(R.id.fragment_my_progress_recyclerview)

        val adapter = object : RecyclerView.Adapter<LearningTargetViewHolder>() {
            val data = listOf(
                    TargetProgress(
                            LearningTarget("lt10", "Learning target 1"),
                            listOf(
                                    ObjectiveProgress("o11", "Objective 1", listOf(
                                            TaskObjectiveProgress("t12", "Task 1", Mastery.NOT_MASTERED),
                                            TaskObjectiveProgress("t13", "Something Else", Mastery.NOT_MASTERED),
                                            TaskObjectiveProgress("t14", "Task 3.14", Mastery.NOT_MASTERED),
                                            TaskObjectiveProgress("t15", "Task kasT", Mastery.NEARLY_MASTERED),
                                            TaskObjectiveProgress("t16", "Do your homework", Mastery.NOT_GRADED),
                                            TaskObjectiveProgress("t17", "Blah blah blah", Mastery.NOT_GRADED)
                                    )),
                                    ObjectiveProgress("o21", "Objective 2", listOf(
                                            TaskObjectiveProgress("t22", "Task 42", Mastery.NOT_MASTERED),
                                            TaskObjectiveProgress("t23", "NOTHING", Mastery.NEARLY_MASTERED),
                                            TaskObjectiveProgress("t24", "Task 2.01", Mastery.NOT_MASTERED),
                                            TaskObjectiveProgress("t25", "ksaT Task", Mastery.MASTERED),
                                            TaskObjectiveProgress("t26", "I don't believe in homework", Mastery.NOT_GRADED),
                                            TaskObjectiveProgress("t27", "NOPENOPENOPENOPE", Mastery.NOT_GRADED)
                                    ))
                            ), "s01"
                    ),
                    TargetProgress(
                            LearningTarget("lt30", "Learning target 1"),
                            listOf(
                                    ObjectiveProgress("o31", "Objective 1", listOf(
                                            TaskObjectiveProgress("t32", "FU", Mastery.MASTERED),
                                            TaskObjectiveProgress("t33", "BLEH", Mastery.MASTERED),
                                            TaskObjectiveProgress("t34", "Task 1337", Mastery.MASTERED),
                                            TaskObjectiveProgress("t35", "H4X0R", Mastery.MASTERED),
                                            TaskObjectiveProgress("t36", "aaAAAaa", Mastery.NEARLY_MASTERED),
                                            TaskObjectiveProgress("t37", "0x13456babF", Mastery.NOT_GRADED)
                                    ))
                            ), "s01"
                    ),
                    TargetProgress(
                            LearningTarget("lt40", "Learning target 1"),
                            listOf(
                                    ObjectiveProgress("o41", "Objective 1", listOf(
                                            TaskObjectiveProgress("t42", "4 FU", Mastery.MASTERED),
                                            TaskObjectiveProgress("t43", "4 BLEH", Mastery.MASTERED),
                                            TaskObjectiveProgress("t44", "4 Task 1337", Mastery.MASTERED),
                                            TaskObjectiveProgress("t45", "4 H4X0R", Mastery.MASTERED),
                                            TaskObjectiveProgress("t46", "4 aaAAAaa", Mastery.NEARLY_MASTERED),
                                            TaskObjectiveProgress("t47", "4 0x13456babF", Mastery.NOT_GRADED)
                                    )),
                                    ObjectiveProgress("o51", "Objective 1", listOf(
                                            TaskObjectiveProgress("t52", "5 FU", Mastery.MASTERED),
                                            TaskObjectiveProgress("t53", "5 BLEH", Mastery.MASTERED),
                                            TaskObjectiveProgress("t54", "5 Task 1337", Mastery.MASTERED),
                                            TaskObjectiveProgress("t55", "5 H4X0R", Mastery.MASTERED),
                                            TaskObjectiveProgress("t56", "5 aaAAAaa", Mastery.NEARLY_MASTERED),
                                            TaskObjectiveProgress("t57", "5 0x13456babF", Mastery.NOT_GRADED)
                                    ))
                            ), "s01"
                    ),
                    TargetProgress(
                            LearningTarget("lt50", "Learning target 1"),
                            listOf(
                                    ObjectiveProgress("o61", "Objective 1", listOf(
                                            TaskObjectiveProgress("t62", "6 FU", Mastery.MASTERED),
                                            TaskObjectiveProgress("t63", "6 BLEH", Mastery.MASTERED),
                                            TaskObjectiveProgress("t64", "6 Task 1337", Mastery.MASTERED),
                                            TaskObjectiveProgress("t65", "6 H4X0R", Mastery.MASTERED),
                                            TaskObjectiveProgress("t66", "6 aaAAAaa", Mastery.NEARLY_MASTERED),
                                            TaskObjectiveProgress("t67", "6 0x13456babF", Mastery.NOT_GRADED)
                                    )),
                                    ObjectiveProgress("o71", "Objective 1", listOf(
                                            TaskObjectiveProgress("t72", "7 FU", Mastery.MASTERED),
                                            TaskObjectiveProgress("t73", "7 BLEH", Mastery.MASTERED),
                                            TaskObjectiveProgress("t74", "7 Task 1337", Mastery.MASTERED),
                                            TaskObjectiveProgress("t75", "7 H4X0R", Mastery.MASTERED),
                                            TaskObjectiveProgress("t76", "7 aaAAAaa", Mastery.NEARLY_MASTERED),
                                            TaskObjectiveProgress("t77", "7 0x13456babF", Mastery.NOT_GRADED)
                                    ))
                            ), "s01"
                    )
            )

            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LearningTargetViewHolder {
                val itemView = LayoutInflater.from(requireActivity()).inflate(R.layout.learning_target_item, parent, false)
                return LearningTargetViewHolder(itemView, requireActivity())
            }


            override fun onBindViewHolder(holder: LearningTargetViewHolder, position: Int) {
                holder.bind(data[position])
            }


            override fun getItemCount(): Int = data.size

        }

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireActivity())
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            MyProgressFragment()
    }
}