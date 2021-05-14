package edu.calpoly.flipted.ui.myProgress.targets

import android.content.Context
import android.graphics.drawable.AnimatedVectorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import edu.calpoly.flipted.R
import edu.calpoly.flipted.businesslogic.UidToStableId
import edu.calpoly.flipted.businesslogic.targets.CalculateObjectiveMastery
import edu.calpoly.flipted.businesslogic.targets.ObjectiveProgress
import edu.calpoly.flipted.ui.MasteryResources

class LearningTargetExpandableListAdapter(private val context: Context) : BaseExpandableListAdapter() {

    var objectives: List<ObjectiveProgress> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    private val objectiveStableIds = UidToStableId<String>()
    private val taskStableIds = UidToStableId<String>()

    /**
     * Gets the number of groups.
     *
     * @return the number of groups
     */
    override fun getGroupCount(): Int = objectives.size

    /**
     * Gets the number of children in a specified group.
     *
     * @param groupPosition the position of the group for which the children
     * count should be returned
     * @return the children count in the specified group
     */
    override fun getChildrenCount(groupPosition: Int): Int = objectives[groupPosition].tasks.size

    /**
     * Gets the data associated with the given group.
     *
     * @param groupPosition the position of the group
     * @return the data child for the specified group
     */
    override fun getGroup(groupPosition: Int) = objectives[groupPosition]

    /**
     * Gets the data associated with the given child within the given group.
     *
     * @param groupPosition the position of the group that the child resides in
     * @param childPosition the position of the child with respect to other
     * children in the group
     * @return the data of the child
     */
    override fun getChild(groupPosition: Int, childPosition: Int)
        = objectives[groupPosition].tasks[childPosition]

    /**
     * Gets the ID for the group at the given position. This group ID must be
     * unique across groups. The combined ID (see
     * [.getCombinedGroupId]) must be unique across ALL items
     * (groups and all children).
     *
     * @param groupPosition the position of the group for which the ID is wanted
     * @return the ID associated with the group
     */
    override fun getGroupId(groupPosition: Int): Long
        = objectiveStableIds.getStableId(objectives[groupPosition].objectiveId)

    /**
     * Gets the ID for the given child within the given group. This ID must be
     * unique across all children within the group. The combined ID (see
     * [.getCombinedChildId]) must be unique across ALL items
     * (groups and all children).
     *
     * @param groupPosition the position of the group that contains the child
     * @param childPosition the position of the child within the group for which
     * the ID is wanted
     * @return the ID associated with the child
     */
    override fun getChildId(groupPosition: Int, childPosition: Int): Long
        = taskStableIds.getStableId(objectives[groupPosition].tasks[childPosition].taskId)

    /**
     * Indicates whether the child and group IDs are stable across changes to the
     * underlying data.
     *
     * @return whether or not the same ID always refers to the same object
     * @see Adapter.hasStableIds
     */
    override fun hasStableIds(): Boolean = true

    /**
     * Whether the child at the specified position is selectable.
     *
     * @param groupPosition the position of the group that contains the child
     * @param childPosition the position of the child within the group
     * @return whether the child is selectable.
     */
    override fun isChildSelectable(groupPosition: Int, childPosition: Int): Boolean = true

    /**
     * Gets a View that displays the given group. This View is only for the
     * group--the Views for the group's children will be fetched using
     * [.getChildView].
     *
     * @param groupPosition the position of the group for which the View is
     * returned
     * @param isExpanded whether the group is expanded or collapsed
     * @param convertView the old view to reuse, if possible. You should check
     * that this view is non-null and of an appropriate type before
     * using. If it is not possible to convert this view to display
     * the correct data, this method can create a new view. It is not
     * guaranteed that the convertView will have been previously
     * created by
     * [.getGroupView].
     * @param parent the parent that this view will eventually be attached to
     * @return the View corresponding to the group at the specified position
     */
    override fun getGroupView(groupPosition: Int, isExpanded: Boolean, convertView: View?, parent: ViewGroup?): View {
        val fillInView = convertView ?: LayoutInflater.from(context).inflate(R.layout.learning_objective_item, parent, false)

        val groupIndicator: ImageView = fillInView.findViewById(R.id.learning_objective_item_group_indicator)
        val masteryIndicator: ImageView = fillInView.findViewById(R.id.learning_objective_item_mastery_indicator)
        val objectiveTitle: TextView = fillInView.findViewById(R.id.learning_objective_item_title)

        val objective = objectives[groupPosition]

        val objectiveMastery = CalculateObjectiveMastery.execute(objective)
        val colorResource = MasteryResources.colorResource(objectiveMastery)
        val color = ResourcesCompat.getColor(context.resources, colorResource, null)
        masteryIndicator.setColorFilter(color)

        objectiveTitle.text = objective.objectiveName

        val indicatorDrawable = groupIndicator.drawable as AnimatedVectorDrawable
        if(isExpanded)
            indicatorDrawable.start()
        else
            indicatorDrawable.reset()

        return fillInView
    }

    /**
     * Gets a View that displays the data for the given child within the given
     * group.
     *
     * @param groupPosition the position of the group that contains the child
     * @param childPosition the position of the child (for which the View is
     * returned) within the group
     * @param isLastChild Whether the child is the last child within the group
     * @param convertView the old view to reuse, if possible. You should check
     * that this view is non-null and of an appropriate type before
     * using. If it is not possible to convert this view to display
     * the correct data, this method can create a new view. It is not
     * guaranteed that the convertView will have been previously
     * created by
     * [.getChildView].
     * @param parent the parent that this view will eventually be attached to
     * @return the View corresponding to the child at the specified position
     */
    override fun getChildView(groupPosition: Int, childPosition: Int, isLastChild: Boolean, convertView: View?, parent: ViewGroup?): View {
        //TODO("Not yet implemented")
        return View(context)
    }
}