package com.example.flipteded.ui
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.util.Log
import android.widget.BaseExpandableListAdapter
import android.widget.TextView
import com.example.flipteded.R
import com.example.flipteded.businesslogic.goals.Goal
import java.util.*

class CustomExpandableListAdapter public constructor(
    private val viewModel: MainViewModel,
    private val context: Context,
    private val titleList: List<String>,
    private val dataList: HashMap<String, List<String>>,
    private var goalData: List<Goal> = listOf()

) : BaseExpandableListAdapter() {
    fun setGoals(goals: List<Goal>) {
        this.goalData = goals
        notifyDataSetChanged();
    }

    override fun getChild(listPosition: Int, expandedListPosition: Int): Any {
        return this.dataList[this.titleList[listPosition]]!![expandedListPosition]
    }
    override fun getChildId(listPosition: Int, expandedListPosition: Int): Long {
        return expandedListPosition.toLong()
    }
    override fun getChildView(
        listPosition: Int,
        expandedListPosition: Int,
        isLastChild: Boolean,
        convertView: View?,
        parent: ViewGroup
    ): View {
        var convertView = convertView
        val titleText = convertView!!.findViewById(R.id.Goal_Title_Text) as TextView
        val subtitle = convertView.findViewById(R.id.Goal_Subtitle) as TextView
        val countText = convertView.findViewById(R.id.Goal_Count) as TextView

        //listTitleTextView.setTypeface(null, Typeface.BOLD)
        currGoal = goalData[listPosition].completions[expandedListPosition]

        titleText.text = currGoal.title
        subtitle.text = "Target: ${currGoal.title} by ${currGoal.dueDate}"
        countText.text = "${currGoal.completions.size} ${currGoal.unitOfMeasurement}"
        val expandedListText = getChild(listPosition, expandedListPosition) as String
        if (convertView == null) {
            val layoutInflater =
                this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            if (isLastChild) {
                convertView = layoutInflater.inflate(R.layout.fragment_mark_progress, null)
            }
            else {
                //convertView = layoutInflater.inflate(R.layout.item_list, null)
                convertView = layoutInflater.inflate(R.layout.sub_goal_bar, null)
            }
        }
        //val expandedListTextView = convertView!!.findViewById<TextView>(R.id.info_text)
        //expandedListTextView.text = expandedListText
        return convertView!!
    }
    override fun getChildrenCount(listPosition: Int): Int {
        return this.dataList[this.titleList[listPosition]]!!.size
    }
    override fun getGroup(listPosition: Int): Any {
        return this.titleList[listPosition]
    }
    override fun getGroupCount(): Int {
        return this.titleList.size
    }
    override fun getGroupId(listPosition: Int): Long {
        return listPosition.toLong()
    }
    override fun getGroupView(
        listPosition: Int,
        isExpanded: Boolean,
        convertView: View?,
        parent: ViewGroup
    ): View {

        var convertView = convertView
        viewModel.reload()
        val goals : List<Goal>? = viewModel.goals.value
        //Log.d("TAG", goals!!.size.toString())
        var currGoal : Goal? = null

        currGoal = goalData[listPosition]
        //check not null
        val listTitle = getGroup(listPosition) as String
        if (convertView == null) {
            val layoutInflater =
                this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = layoutInflater.inflate(R.layout.top_goal_bar, null)
        }


            //.d("TAG", currGoal!.title)
            val titleText = convertView!!.findViewById(R.id.Goal_Title_Text) as TextView
            val subtitle = convertView.findViewById(R.id.Goal_Subtitle) as TextView
            val countText = convertView.findViewById(R.id.Goal_Count) as TextView

            //listTitleTextView.setTypeface(null, Typeface.BOLD)


            titleText.text = currGoal.title
            subtitle.text = "Target: ${currGoal.title} by ${currGoal.dueDate}"
            countText.text = "${currGoal.completions.size} ${currGoal.unitOfMeasurement}"
            return convertView

    }
    override fun hasStableIds(): Boolean {
        return false
    }
    override fun isChildSelectable(listPosition: Int, expandedListPosition: Int): Boolean {
        return true
    }




}
