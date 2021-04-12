package edu.calpoly.flipted.ui.tasks

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import edu.calpoly.flipted.businesslogic.goals.Goal
import edu.calpoly.flipted.businesslogic.tasks.Block
import edu.calpoly.flipted.businesslogic.tasks.QuizBlock

class TaskPagerAdapter(fragmentManger: FragmentManager) : FragmentPagerAdapter(fragmentManger, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private var blockData: List<Block> = listOf()

    fun setBlocks(blocks: List<Block>) {
        this.blockData = blocks
        notifyDataSetChanged()
    }

    override fun getItem(position: Int): Fragment {
        var block = blockData.get(position)
        /*
        if (block is QuizBlock) {
            block = block as QuizBlock

        }
        */

        block = block as QuizBlock
        return FreeResponseFragment.newInstance(block.questions.get(0).question)
    }

    override fun getCount(): Int {
        Log.e("tag", blockData.size.toString())
        return blockData.size
    }
}