package com.example.covid

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.fragment_left.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass.
 */
class LeftFragment : Fragment() {
    lateinit var dataString:String
    lateinit var leftFragmentAdapter: LeftFragmentAdapter
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        dataString= arguments?.getString("DataAsJson").toString()
        return inflater.inflate(R.layout.fragment_left, container, false)
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
                bindtimeline(data.casesTimeSeries)
            }
        }
    }
    private fun bindtimeline(list: List<CasesTimeSeriesItem>) {
        leftFragmentAdapter= LeftFragmentAdapter(list)
        rvTimeline.layoutManager= LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL,false)
        rvTimeline.adapter=leftFragmentAdapter
    }

}
