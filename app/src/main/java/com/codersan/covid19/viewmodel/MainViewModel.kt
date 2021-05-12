package com.codersan.covid19.ui.main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codersan.covid19.MyParser
import com.codersan.covid19.WebUtils
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val webUtils=WebUtils()
    private lateinit var listener:MyListener
    var values=MutableLiveData<Array<String>?>()


    fun setListener(listener:MyListener){
        this.listener=listener
    }
    fun fetchData(name:String){
        viewModelScope.launch {
            Log.d("TRACE__", "VM : fetching data with name=$name")


            //get json object from response
            val jsonObject=webUtils.requestData(name)


            if (jsonObject==null){
                listener.onReady(false)
                return@launch
            }

            Log.d("TRACE__", "VM : JSON object received")


            //extract values from the json object
            val valuesArray=MyParser.parse(jsonObject)
            if (valuesArray==null){
                Log.d("TRACE__", "VM : invalid JSON Object!")
                listener.onReady(false)
                return@launch
            }


            Log.d("TRACE__", "VM : new values are ready and updated for VIEW")

            //update vals
            values.value= valuesArray
            listener.onReady(true)



        }

    }

   fun interface MyListener{
        fun onReady(status:Boolean)
    }
}