package edu.calpoly.flipted.ui.myProgress

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.CheckBox
import android.widget.TextView
import androidx.fragment.app.Fragment
import edu.calpoly.flipted.R
import edu.calpoly.flipted.businesslogic.tasks.data.RubricRequirement

class LearningTargetsFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_progress, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance() =
                LearningTargetsFragment()
    }

    inner class TargetNamesListAdapter : BaseAdapter() {

        var data : List<LearningTarget> = mutableListOf("1", "2")

        override fun getCount(): Int = data.size

        override fun getItem(p0: Int): LearningTarget {
            if (p0 == 0) {

            }
            else {
                return data[p0 - 1]
            }
        }

        override fun getItemId(p0: Int): Long = uidMap.getStableId(data[p0].uid)

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val fillInView = convertView ?: layoutInflater.inflate(R.layout.learning_target_names_item, parent, false)
            val targetText : TextView = fillInView.findViewById(R.id.learning_target_name_text)

            val data = getItem(position)

            targetText.text = data.description

            targetText.isChecked = data.isComplete
            //color relates to if in current list or not

            targetText.setOnClickListener{ view ->
                if (.any{data.uid == it}) {
                    remove
                change color
            }
                else {
                    add
                change color
            }
            }


            return fillInView
        }

    }
}