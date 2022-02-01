package com.xamk.werewolf.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.xamk.werewolf.R

class ListAdapter (var name: ArrayList<String>, var character: ArrayList<String>)
    : RecyclerView.Adapter<ListAdapter.ViewHolder>() {
    // onCreateViewHolder
    // create UI View Holder from XML layout
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
            : ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view =
            layoutInflater.inflate(R.layout.listview_item, parent, false)
        return ViewHolder(view)
    }
    // ViewHolder
    // View Holder class to hold UI views
    inner class ViewHolder(view: View)
        : RecyclerView.ViewHolder(view) {
        val tv_name: TextView = view.findViewById(R.id.tvplayer_name)
        val tv_char: TextView = view.findViewById(R.id.tvplayer_char)

    }

    // onBindViewHolder
    // bind data to UI View Holder
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // item to bind UI
        holder.tv_name.text = name[position]
        holder.tv_char.text = character[position]

    }

    // getItemCount
    override fun getItemCount(): Int = name.size

}
