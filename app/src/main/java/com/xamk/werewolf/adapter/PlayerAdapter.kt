package com.xamk.werewolf.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.recyclerview.widget.RecyclerView
import com.xamk.werewolf.R
import com.xamk.werewolf.model.PlayerItem

class PlayerAdapter (var player: MutableList<PlayerItem>)
    : RecyclerView.Adapter<PlayerAdapter.ViewHolder>() {
    // onCreateViewHolder
    // create UI View Holder from XML layout
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
            : ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view =
            layoutInflater.inflate(R.layout.recyclerview_item, parent, false)
        return ViewHolder(view)
    }
    // ViewHolder
    // View Holder class to hold UI views
    inner class ViewHolder(view: View)
        : RecyclerView.ViewHolder(view) {
        val playerBox: CheckBox = view.findViewById(R.id.checkbox)

    }

    // onBindViewHolder
    // bind data to UI View Holder
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // item to bind UI
        val item: PlayerItem = player[position]
        holder.playerBox.text = item.name

        holder.playerBox.isChecked= player[position].isSelected

        holder.playerBox.setOnClickListener {
            val checkedPlayer = player[position]
            checkedPlayer.isSelected = !player[position].isSelected
            notifyDataSetChanged()
            }
    }

    fun getItem() = player

    // getItemCount
    override fun getItemCount(): Int = player.size

    // update
    fun update(newList: MutableList<PlayerItem>) {
        player = newList
        notifyDataSetChanged()
    }
}