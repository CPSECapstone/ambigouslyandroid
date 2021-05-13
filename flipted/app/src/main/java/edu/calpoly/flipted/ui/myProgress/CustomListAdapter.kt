package edu.calpoly.flipted.ui.myProgress

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter

import android.widget.ListView
import androidx.core.view.get
import androidx.core.view.size
import androidx.fragment.app.Fragment
import edu.calpoly.flipted.R

class CustomListAdapter(
    fragment: Fragment,
    private val listView: ListView
) : BaseAdapter() {

    private val inflater: LayoutInflater
            = fragment.context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater


    override fun getCount(): Int {
        return listView.size
    }

    override fun getItem(position: Int): Any {
        return listView[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        return inflater.inflate(R.layout.mission_task_fragment_exp_list, parent, false)
    }

}
