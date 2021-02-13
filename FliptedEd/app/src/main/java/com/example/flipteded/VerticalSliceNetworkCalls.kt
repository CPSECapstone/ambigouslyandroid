package com.example.flipteded

import android.content.Context
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class VerticalSliceNetworkCalls {
    companion object {

        suspend fun sendStringToAWS(applicationContext: Context, str: String) =
            suspendCoroutine<Unit> { cont ->

                val url = "https://reqres.in/api/users"
                val stringRequest = StringRequest(Request.Method.POST, url,
                    Response.Listener<String> { response ->
                        cont.resume(Unit)
                    },
                    Response.ErrorListener { cont.resume(Unit) })

                RequestHandler.getInstance(applicationContext).addToRequestQueue(stringRequest)
            }

        suspend fun getStringsFromAWS(applicationContext: Context) =
            suspendCoroutine<List<String>> { cont ->
                val url = "https://reqres.in/api/users?page=2"

                val jsonArrayRequest = JsonObjectRequest(
                    Request.Method.GET,
                    url,
                    null,
                    Response.Listener { response ->
                        val strList = response.getJSONArray("strings")
                        val returnList = ArrayList<String>()
                        for (i in 0..strList.length()) {
                            returnList[i] = strList.getString(i)
                        }
                        cont.resume(listOf("HI!", "HELLO!", "SUP!"))
                    },
                    Response.ErrorListener {
                        cont.resume(listOf<String>())
                    }
                )
                RequestHandler.getInstance(applicationContext).addToRequestQueue(jsonArrayRequest)
            }
    }
}