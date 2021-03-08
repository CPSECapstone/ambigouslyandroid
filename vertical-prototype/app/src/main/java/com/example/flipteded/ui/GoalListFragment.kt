package com.example.flipteded.ui


import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import android.widget.EditText
import android.widget.ExpandableListAdapter
import android.widget.ExpandableListView
import android.widget.Toast
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.example.flipteded.R
import com.example.flipteded.businesslogic.goals.Goal

import kotlin.collections.ArrayList
import kotlin.collections.set
import com.example.flipteded.ui.CustomExpandableListAdapter


/**
 * A simple [Fragment] subclass.
 * Use the [AddStringFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class GoalListFragment : Fragment() {

    private lateinit var viewModel : MainViewModel
    private lateinit var expandableListView: ExpandableListView
    private lateinit var adapter: CustomExpandableListAdapter
    private var titleList: List<String>? = null
    private var titleValue: EditText? = null
    private var thiscontext: Context? = null
    //private lateinit var goalData: List<Goal>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        thiscontext = container!!.getContext();
        return inflater.inflate(R.layout.fragment_exp_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // viewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
        viewModel = ViewModelProvider(requireActivity(), ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application))[MainViewModel::class.java]

        expandableListView = view.findViewById(R.id.expandableList)
        //Ref for switching group indicator: https://stackoverflow.com/questions/5800426/expandable-list-view-move-group-icon-indicator-to-right
        val newDisplay = expandableListView!!.getViewTreeObserver()
        newDisplay.addOnGlobalLayoutListener(OnGlobalLayoutListener {
            expandableListView!!.setIndicatorBoundsRelative(
                expandableListView!!.getRight() - 40,
                expandableListView!!.getWidth()
            )
        })

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
            adapter = CustomExpandableListAdapter(viewModel, thiscontext!!, titleList as ArrayList<String>, listData)
            expandableListView!!.setAdapter(adapter)
            /*
            expandableListView!!.setOnGroupExpandListener { groupPosition ->
                Toast.makeText(
                    applicationContext,
                    (titleList as ArrayList<String>)[groupPosition] + " List Expanded.",
                    Toast.LENGTH_SHORT
                ).show()
            }
            */
             /*
            expandableListView!!.setOnGroupCollapseListener { groupPosition ->
                Toast.makeText(
                    applicationContext,
                    (titleList as ArrayList<String>)[groupPosition] + " List Collapsed.",
                    Toast.LENGTH_SHORT
                ).show()
            }
            */

            expandableListView!!.setOnChildClickListener { _, v, groupPosition, childPosition, _ ->
                titleList = ArrayList(listData.keys)
                if (v.id == R.id.fragment_mark_progress) {
                    val mAlertDialog = AlertDialog.Builder(thiscontext!!)
                    val inflater = this.layoutInflater;

                    //mAlertDialog.setIcon(R.mipmap.ic_launcher_round) //set alertdialog icon
                    mAlertDialog.setTitle("Mark Progress") //set alertdialog title
                    //mAlertDialog.setMessage("Your message here") //set alertdialog message
                    mAlertDialog.setPositiveButton("Save") { dialog, id ->
                        //perform some tasks here
                        titleValue = v.findViewById(R.id.Progress_Title)
                        //GetValue.getText().toString()


                    }
                    mAlertDialog.setNegativeButton("Cancel") { dialog, id ->
                        //perform som tasks here

                    }
                    mAlertDialog.setView(inflater.inflate(R.layout.mark_progress_form, null))
                    mAlertDialog.show()
                    false
                }

                else {
                    Toast.makeText(
                        thiscontext!!,
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
        viewModel.goals.observe(viewLifecycleOwner, Observer { newGoals ->
            adapter.setGoals(newGoals)
            //viewModel.reload()
            //goalData = viewModel.goals
        })
    }



    /*
    viewModel.goals.observe(this, new Observer<List<Goal>>() {
        @Override
        public void onChanged(@Nullable List<Goal> goals) {
            adapter.setGoals(goals);
        }
    });
*/
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AddStringFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() = GoalListFragment()
    }
}