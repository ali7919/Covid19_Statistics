package com.codersan.covid19

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.codersan.covid19.databinding.MainActivityBinding
import com.codersan.covid19.ui.main.MainViewModel


class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //bind views
        val binding: MainActivityBinding = DataBindingUtil.setContentView(this, R.layout.main_activity)
        binding.lifecycleOwner=this

        //init view model
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        binding.vm=viewModel



        //set listener for request
        viewModel.setListener{
            if (it) Toast.makeText(this@MainActivity,"updated", Toast.LENGTH_SHORT).show()
            else Toast.makeText(this@MainActivity,"failed", Toast.LENGTH_SHORT).show()

            Log.d("TRACE__", "VIEW : new values are received with status : $it")
            binding.pb.visibility= View.INVISIBLE


        }



        //listener for button
        findViewById<Button>(R.id.button).setOnClickListener {
            binding.pb.visibility= View.VISIBLE
            Log.d("TRACE__", "VIEW : button clicked")

            val name = findViewById<EditText>(R.id.ed).text.toString()
            viewModel.fetchData(name)

        }
    }


}


