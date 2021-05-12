package com.codersan.covid19

import android.util.Log
import org.json.JSONObject
import java.text.DecimalFormat
import java.text.NumberFormat

object MyParser {
    fun parse(obj: JSONObject): Array<String>? {
        Log.d("TRACE__", "M(MyParser) : parsing JSON object")


        val covid_data = mutableListOf<String>()
        val formatter: NumberFormat = DecimalFormat("#,###")


        if (obj.getJSONArray("response").length() == 0) return null
        val cases = (obj.getJSONArray("response")[0] as JSONObject).getJSONObject("cases")

        try { covid_data.add(formatter.format(cases.getInt("new")))
        }catch (e:Exception) {covid_data.add("no data")}

        try { covid_data.add(formatter.format((obj.getJSONArray("response")[0] as JSONObject).getJSONObject("deaths").getInt("new")))
        }catch (e:Exception) {covid_data.add("no data")}

        try { covid_data.add(formatter.format(cases.getInt("critical")))
        }catch (e:Exception) {covid_data.add("no data")}

        covid_data.add(formatter.format(cases.getInt("active")))
        covid_data.add(formatter.format(cases.getInt("recovered")))
        covid_data.add(formatter.format(cases.getInt("total")))


        Log.d("TRACE__", "M(MyParser) : parsing was successful, extracted data is as fallows")
        Log.d("TRACE__", covid_data.toString())




        return covid_data.toTypedArray()


    }
}