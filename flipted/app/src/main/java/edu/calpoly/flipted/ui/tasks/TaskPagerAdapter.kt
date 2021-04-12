package edu.calpoly.flipted.ui.tasks

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.adapter.FragmentViewHolder
import edu.calpoly.flipted.businesslogic.goals.Goal
import edu.calpoly.flipted.businesslogic.tasks.Block
import edu.calpoly.flipted.businesslogic.tasks.QuizBlock

class TaskPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    private var blockData: List<Block> = listOf()

    fun setBlocks(blocks: List<Block>) {
        this.blockData = blocks
        notifyDataSetChanged()
    }

    override fun createFragment(position: Int): Fragment {
        var block = blockData.get(0)
        /*
        if (block is QuizBlock) {
            block = block as QuizBlock

        }
        */

        block = block as QuizBlock
        return FreeResponseFragment.newInstance(block.questions[position].question)
    }

    override fun getItemCount(): Int {
        Log.e("tag", blockData.size.toString())
        return 2
        //blockData.size
    }
}