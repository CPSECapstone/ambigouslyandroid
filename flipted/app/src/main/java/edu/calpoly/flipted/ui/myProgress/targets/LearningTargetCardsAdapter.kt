package edu.calpoly.flipted.ui.myProgress.targets

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import edu.calpoly.flipted.R
import edu.calpoly.flipted.businesslogic.UidToStableId
import edu.calpoly.flipted.businesslogic.targets.CalculateMastery
import edu.calpoly.flipted.businesslogic.targets.TargetProgress
import edu.calpoly.flipted.ui.MasteryResources

class LearningTargetCardViewHolder(view: View, private val fragment: LearningTargetDetailFragment) : RecyclerView.ViewHolder(view), View.OnClickListener {
    private val root: CardView = view as CardView
    private val title: TextView = view.findViewById(R.id.learning_target_card_title)
    private val masteryIndicator: ImageView = view.findViewById(R.id.learning_target_card_mastery_indicator)

    private lateinit var targetId: String

    fun bind(target: TargetProgress) {
        title.text = target.target.targetName

        val mastery = CalculateMastery.calculate(target)
        val colorResource = MasteryResources.colorResource(mastery)
        val color = ResourcesCompat.getColor(fragment.resources, colorResource, null)
        masteryIndicator.setColorFilter(color)

        targetId = target.target.uid
        root.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        fragment.targetId = targetId
    }
}

class LearningTargetCardsAdapter(private val fragment: LearningTargetDetailFragment) : RecyclerView.Adapter<LearningTargetCardViewHolder>() {
    var targets: List<TargetProgress> = listOf()

    private val idMap = UidToStableId<String>()

    init {
        setHasStableIds(true)
    }

    /**
     * Called when RecyclerView needs a new [ViewHolder] of the given type to represent
     * an item.
     *
     *
     * This new ViewHolder should be constructed with a new View that can represent the items
     * of the given type. You can either create a new View manually or inflate it from an XML
     * layout file.
     *
     *
     * The new ViewHolder will be used to display items of the adapter using
     * [.onBindViewHolder]. Since it will be re-used to display
     * different items in the data set, it is a good idea to cache references to sub views of
     * the View to avoid unnecessary [View.findViewById] calls.
     *
     * @param parent   The ViewGroup into which the new View will be added after it is bound to
     * an adapter position.
     * @param viewType The view type of the new View.
     * @return A new ViewHolder that holds a View of the given view type.
     * @see .getItemViewType
     * @see .onBindViewHolder
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LearningTargetCardViewHolder =
            LearningTargetCardViewHolder(
                    LayoutInflater.from(fragment.requireActivity()).inflate(R.layout.learning_target_card, parent, false),
                    fragment
            )

    /**
     * Called by RecyclerView to display the data at the specified position. This method should
     * update the contents of the [ViewHolder.itemView] to reflect the item at the given
     * position.
     *
     *
     * Note that unlike [android.widget.ListView], RecyclerView will not call this method
     * again if the position of the item changes in the data set unless the item itself is
     * invalidated or the new position cannot be determined. For this reason, you should only
     * use the `position` parameter while acquiring the related data item inside
     * this method and should not keep a copy of it. If you need the position of an item later
     * on (e.g. in a click listener), use [ViewHolder.getBindingAdapterPosition] which
     * will have the updated adapter position.
     *
     * Override [.onBindViewHolder] instead if Adapter can
     * handle efficient partial bind.
     *
     * @param holder   The ViewHolder which should be updated to represent the contents of the
     * item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    override fun onBindViewHolder(holder: LearningTargetCardViewHolder, position: Int) {
        holder.bind(targets[position])
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    override fun getItemCount(): Int = targets.size

    /**
     * Return the stable ID for the item at `position`. If [.hasStableIds]
     * would return false this method should return [.NO_ID]. The default implementation
     * of this method returns [.NO_ID].
     *
     * @param position Adapter position to query
     * @return the stable ID of the item at position
     */
    override fun getItemId(position: Int): Long = idMap.getStableId(targets[position].target.uid)
}