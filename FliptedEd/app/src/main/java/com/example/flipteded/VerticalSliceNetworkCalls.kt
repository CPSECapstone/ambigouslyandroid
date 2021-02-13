package com.example.flipteded

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.JsonRequest
import com.android.volley.toolbox.StringRequest
import org.json.JSONObject
import java.util.*
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine


object VerticalSliceNetworkCalls {

    suspend fun sendStringToAWS(context: Context, str: String) =
        suspendCoroutine<Unit> { cont ->
            Log.d("MainViewModel", "Saving string \"$str\"");

            val url = "https://h931pnb4pe.execute-api.us-east-2.amazonaws.com/test/comments"
            val jsonBody = JSONObject("{" +
                    "  \"message\":  \"$str\"" +
                    "}")
            val stringRequest = JsonObjectRequest(url, jsonBody,
                Response.Listener<JSONObject> { _ ->
                    cont.resume(Unit)
                },
                Response.ErrorListener { cont.resume(Unit) })
            RequestHandler.getInstance(context).addToRequestQueue(stringRequest)
        }
    suspend fun getStringsFromAWS(context: Context) =
        suspendCoroutine<List<String>> { cont ->
            val url = "https://h931pnb4pe.execute-api.us-east-2.amazonaws.com/test/comments/abc"

            val jsonArrayRequest = JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                Response.Listener { response ->
                    val strList = response.getJSONArray("Items")
                    //TODO: this shouldn't crash the entire app when a network request fails
                    //val strList = response.getJSONArray("strings")
                    val returnList = mutableListOf<String>()

                    for (i in 0 until strList.length()) {
                        returnList.add(strList.getJSONObject(i).getJSONObject("message").getString("S"))
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