package com.example.flipteded

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

object VerticalSliceNetworkCalls {

    suspend fun sendStringToAWS(context: Context, str: String) =
        suspendCoroutine<Unit> { cont ->
            Log.d("MainViewModel", "Saving string \"$str\"");

            val url = "https://h931pnb4pe.execute-api.us-east-2.amazonaws.com/test"
            val stringRequest = StringRequest(Request.Method.POST, url,
                Response.Listener<String> { response ->
                    cont.resume(Unit)
                },
                Response.ErrorListener { cont.resume(Unit) })

            RequestHandler.getInstance(context).addToRequestQueue(stringRequest)
        }
    suspend fun getStringsFromAWS(context: Context) =
        suspendCoroutine<List<String>> { cont ->
            val url = "https://h931pnb4pe.execute-api.us-east-2.amazonaws.com/test"

            val jsonArrayRequest = JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                Response.Listener { response ->
                    val strList = response.getJSONArray("Items")
                    //TODO: this shouldn't crash the entire app when a network request fails
                    //val strList = response.getJSONArray("strings")
                    val returnList = ArrayList<String>()
                    for (i in 0..strList.length()) {
                        returnList[i] = strList.getString(i)
                    }
                    cont.resume(returnList)
                },
                Response.ErrorListener {
                    cont.resume(listOf<String>())
                }
            )
            RequestHandler.getInstance(context).addToRequestQueue(jsonArrayRequest)
        }
}