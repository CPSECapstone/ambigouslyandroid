package com.example.flipteded.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.lifecycle.Observer
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import android.widget.ExpandableListAdapter
import android.widget.ExpandableListView
import com.example.flipteded.R

import kotlinx.android.synthetic.main.activity_item_list.*
import kotlinx.android.synthetic.main.item_list.*

/**
 * An activity representing a list of Pings. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a [ItemDetailActivity] representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
class ItemListActivity : AppCompatActivity() {

    private lateinit var viewModel : MainViewModel
    private var expandableListView: ExpandableListView? = null
    private var adapter: ExpandableListAdapter? = null
    private var titleList: List<String>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_exp_list)

        setSupportActionBar(toolbar)
        toolbar.title = title

        expandableListView = findViewById(R.id.expandableList)

        val expandableListDetail =
            HashMap<String, List<String>>()
        val myFavCricketPlayers: MutableList<String> =
            ArrayList()
        myFavCricketPlayers.add("MS.Dhoni")
        myFavCricketPlayers.add("Sehwag")
        myFavCricketPlayers.add("Shane Watson")
        myFavCricketPlayers.add("Ricky Ponting")
        myFavCricketPlayers.add("Shahid Afridi")
        val myFavFootballPlayers: MutableList<String> = ArrayList()
        myFavFootballPlayers.add("Cristiano Ronaldo")
        myFavFootballPlayers.add("Lionel Messi")
        myFavFootballPlayers.add("Gareth Bale")
        myFavFootballPlayers.add("Neymar JR")
        myFavFootballPlayers.add("David de Gea")
        val myFavTennisPlayers: MutableList<String> = ArrayList()
        myFavTennisPlayers.add("Roger Federer")
        myFavTennisPlayers.add("Rafael Nadal")
        myFavTennisPlayers.add("Andy Murray")
        myFavTennisPlayers.add("Novak Jokovic")
        myFavTennisPlayers.add("Sania Mirza")
        expandableListDetail["CRICKET PLAYERS"] = myFavCricketPlayers
        expandableListDetail["FOOTBALL PLAYERS"] = myFavFootballPlayers
        expandableListDetail["TENNIS PLAYERS"] = myFavTennisPlayers

        if (expandableListView != null) {
            val listData = expandableListDetail
            titleList = ArrayList(listData.keys)
            adapter = CustomExpandableListAdapter(this, titleList as ArrayList<String>, listData)
            expandableListView!!.setAdapter(adapter)
            expandableListView!!.setOnGroupExpandListener { groupPosition ->
                Toast.makeText(
                    applicationContext,
                    (titleList as ArrayList<String>)[groupPosition] + " List Expanded.",
                    Toast.LENGTH_SHORT
                ).show()
            }
            expandableListView!!.setOnGroupCollapseListener { groupPosition ->
                Toast.makeText(
                    applicationContext,
                    (titleList as ArrayList<String>)[groupPosition] + " List Collapsed.",
                    Toast.LENGTH_SHORT
                ).show()
            }
            expandableListView!!.setOnChildClickListener { _, _, groupPosition, childPosition, _ ->
                Toast.makeText(
                    applicationContext,
                    "Clicked: " + (titleList as ArrayList<String>)[groupPosition] + " -> " + listData[(
                            titleList as
                                    ArrayList<String>
                            )
                            [groupPosition]]!!.get(
                        childPosition
                    ),
                    Toast.LENGTH_SHORT
                ).show()
                false
            }
        }
        viewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(application))[MainViewModel::class.java]

        //setupRecyclerView(item_list)
/*
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, AddStringFragment.newInstance())
                .commitNow()
        }
*/
    }

    private fun setupRecyclerView(recyclerView: RecyclerView) {
        val adapter = StringViewAdapter()
        recyclerView.adapter = adapter

        viewModel.data.observe(this, Observer {
            adapter.updateData(it);
        })
    }
}
