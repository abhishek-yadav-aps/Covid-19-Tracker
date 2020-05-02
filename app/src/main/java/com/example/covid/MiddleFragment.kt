package com.example.covid

import android.app.AlertDialog
import android.content.Context.CONNECTIVITY_SERVICE
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.fragment_middle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class MiddleFragment : Fragment() {
    lateinit var dataString:String
    lateinit var middleFragmentAdapter: MiddleFragmentAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        dataString= arguments?.getString("DataAsJson").toString()
        return inflater.inflate(R.layout.fragment_middle, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fetchResult()

    }
    private fun fetchResult() {
        GlobalScope.launch {
            val gsonBuilder = GsonBuilder()
            gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            val gson = gsonBuilder.create()
            val data=gson.fromJson(dataString,TotalResponse::class.java)
            launch(Dispatchers.Main) {
                bindCombinedData(data.statewise[0])
                bindsatewisedata(data.statewise.subList(1,data.statewise.size))
            }
        }
    }
    private fun bindsatewisedata(subList: List<StatewiseItem>) {
        middleFragmentAdapter= MiddleFragmentAdapter(subList)
        listRv.layoutManager= LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL,false)
        listRv.adapter=middleFragmentAdapter
    }

    private fun bindCombinedData(statewiseItem: StatewiseItem) {
        val lastUpdatedTime = statewiseItem.lastupdatedtime
        val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm:ss")
        lastUpdatedTv.text = "Last Updated\n ${getTimeAgo(simpleDateFormat.parse(lastUpdatedTime))}"
        confirmedTv.text=statewiseItem.confirmed
        activeTv.text=statewiseItem.active
        recoveredTv.text=statewiseItem.recovered
        deathTv.text=statewiseItem.deaths
    }
    fun getTimeAgo(past: Date): String {
        val now = Date()
        val seconds = TimeUnit.MILLISECONDS.toSeconds(now.time - past.time)
        val minutes = TimeUnit.MILLISECONDS.toMinutes(now.time - past.time)
        val hours = TimeUnit.MILLISECONDS.toHours(now.time - past.time)

        return when {
            seconds < 60 -> {
                "Few seconds ago"
            }
            minutes < 60 -> {
                "$minutes minutes ago"
            }
            hours < 24 -> {
                "$hours hour ${minutes % 60} min ago"
            }
            else -> {
                SimpleDateFormat("dd/MM/yy, hh:mm a").format(past).toString()
            }
        }
    }
}
