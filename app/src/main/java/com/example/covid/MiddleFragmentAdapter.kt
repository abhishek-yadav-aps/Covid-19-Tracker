package com.example.covid

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_list.view.*

class MiddleFragmentAdapter(val list:List<StatewiseItem>): RecyclerView.Adapter<MiddleFragmentAdapter.stateViewHolder>() {
    class stateViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        fun bind(list: StatewiseItem)
        {
            itemView?.stateLTv?.text=list.state
            itemView?.confirmedLTv?.text=list.confirmed
            itemView?.activeLTv?.text=list.active
            itemView?.recoveredLTv?.text=list.recovered
            itemView?.deathLTv?.text=list.deaths
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): stateViewHolder {
        return stateViewHolder(LayoutInflater.from(parent?.context).inflate(R.layout.item_list,parent,false))
    }

    override fun getItemCount(): Int=list.size



    override fun onBindViewHolder(holder: MiddleFragmentAdapter.stateViewHolder, position: Int) {
        holder?.bind(list[position])
    }


}