package com.example.covid

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.timeline_list.view.*

class LeftFragmentAdapter(val list:List<CasesTimeSeriesItem>): RecyclerView.Adapter<LeftFragmentAdapter.timelineViewHolder>() {
    class timelineViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        fun bind(listitem: CasesTimeSeriesItem)
        {
            itemView?.dateTv?.text=listitem.date
            itemView?.confirmedTTv?.text=listitem.totalconfirmed
            itemView?.recoveredTTv?.text=listitem.totalrecovered
            itemView?.deathTTv?.text=listitem.totaldeceased
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): timelineViewHolder {
        return timelineViewHolder(LayoutInflater.from(parent?.context).inflate(R.layout.timeline_list,parent,false))
    }

    override fun getItemCount(): Int=list.size



    override fun onBindViewHolder(holder: timelineViewHolder, position: Int) {
        holder?.bind(list[position])
    }


}