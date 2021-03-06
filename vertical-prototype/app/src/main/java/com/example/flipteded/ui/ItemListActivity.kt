package com.example.flipteded.ui


import android.app.AlertDialog
import android.os.Bundle
import android.widget.EditText;
import android.widget.ExpandableListAdapter
import android.widget.ExpandableListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.flipteded.R
import kotlinx.android.synthetic.main.activity_item_list.*
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.List
import kotlin.collections.MutableList
import kotlin.collections.set


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
    private var titleValue: EditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_exp_list)

        setSupportActionBar(toolbar)
        toolbar.title = title

        expandableListView = findViewById(R.id.expandableList)

        //Ref for switching group indicator: https://stackoverflow.com/questions/5800426/expandable-list-view-move-group-icon-indicator-to-right
        val newDisplay = windowManager.defaultDisplay
        val width = newDisplay.getWidth()
        expandableListView!!.setIndicatorBoundsRelative(width - 50, width)

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
            expandableListView!!.setOnChildClickListener { _, v, groupPosition, childPosition, _ ->
                titleList = ArrayList(listData.keys)
                if (v.id == R.id.fragment_mark_progress) {
                    val mAlertDialog = AlertDialog.Builder(this)
                    val inflater = this.layoutInflater;

                    //mAlertDialog.setIcon(R.mipmap.ic_launcher_round) //set alertdialog icon
                    mAlertDialog.setTitle("Mark Progress") //set alertdialog title
                    //mAlertDialog.setMessage("Your message here") //set alertdialog message
                    mAlertDialog.setPositiveButton("Save") { dialog, id ->
                        //perform some tasks here
                        titleValue = findViewById(R.id.Progress_Title)
                        //GetValue.getText().toString()
                        Toast.makeText(this, "Yes", Toast.LENGTH_SHORT).show()

                    }
                    mAlertDialog.setNegativeButton("Cancel") { dialog, id ->
                        //perform som tasks here
                        Toast.makeText(this, "No", Toast.LENGTH_SHORT).show()
                    }
                    mAlertDialog.setView(inflater.inflate(R.layout.mark_progress_form, null))
                    mAlertDialog.show()
                    false
                }
                else {
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
