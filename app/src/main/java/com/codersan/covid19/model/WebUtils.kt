  package com.codersan.covid19

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject

class WebUtils {

    suspend fun requestData(name: String): JSONObject? = withContext(Dispatchers.IO) {

        val client = OkHttpClient()

        //create a request
        val request = Request.Builder()
            .url("https://covid-193.p.rapidapi.com/statistics?country=$name")
            .get()
            .addHeader("x-rapidapi-key", "1314517ef7msh8ebf7ebd6e14860p19b072jsn3d3707c031d1")
            .addHeader("x-rapidapi-host", "covid-193.p.rapidapi.com")
            .build()

        Log.d("TRACE__", "M(WebUtils) : request is ready to send")


        //get response
        client.newCall(request).execute().use {
            if (!it.isSuccessful) return@withContext null


            val res = it.body()!!.string()

            Log.d("TRACE__", "M(WebUtils) : successful call, respond is as fallows")

            Log.d("TRACE__", res)

            return@withContext JSONObject(res)
        }
    }
}