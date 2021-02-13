package com.example.flipteded

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class StringViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
    private val textView: TextView = view.findViewById(R.id.id_text);

    private lateinit var data: String
    fun bindData(data: String) {
        this.data = data
        textView.text = data
    }
}

class StringViewAdapter : RecyclerView.Adapter<StringViewHolder>() {
    private var data : List<String> = listOf("TEST 1", "TEST 2", "test 3")

    fun updateData(newData : List<String>) {
        data = newData
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StringViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return StringViewHolder(inflater.inflate(R.layout.item_list_content, parent, false))
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: StringViewHolder, position: Int) {
        holder.bindData(data[position])
    }

}