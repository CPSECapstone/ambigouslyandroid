package edu.calpoly.flipted.ui.tasks

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import edu.calpoly.flipted.businesslogic.tasks.data.Page

class TaskPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    var pages: List<Page> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount(): Int = pages.size

    override fun createFragment(position: Int): Fragment = TaskPageFragment.newInstance(position)

    override fun containsItem(itemId: Long): Boolean = pages.any{it.uid == itemId}

    override fun getItemId(position: Int): Long = pages[position].uid
}