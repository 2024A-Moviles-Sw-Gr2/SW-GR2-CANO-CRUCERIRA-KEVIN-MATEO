package com.example.deber03

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ItemAdapter(private val items: List<Item>) : RecyclerView.Adapter<ItemAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_element, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = items.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val pinImageView: ImageView = itemView.findViewById(R.id.pinImageView)
        private val avatarImageView: ImageView = itemView.findViewById(R.id.avatarImageView)
        private val usernameTextView: TextView = itemView.findViewById(R.id.usernameTextView)

        fun bind(item: Item) {
            pinImageView.setImageResource(item.imageResId)
            avatarImageView.setImageResource(item.avatarResId)
            usernameTextView.text = item.username
        }
    }
}
