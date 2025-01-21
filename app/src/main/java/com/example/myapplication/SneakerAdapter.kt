package com.example.myapplication

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class SneakerAdapter(var sneakers: List<Sneaker>, var photos: Map<Int, String>) : RecyclerView.Adapter<SneakerAdapter.SneakerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SneakerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_sneaker, parent, false)
        return SneakerViewHolder(view)
    }

    override fun onBindViewHolder(holder: SneakerViewHolder, position: Int) {
        val sneaker = sneakers[position]
        holder.bind(sneaker, photos[sneaker.sneaker_id])
    }

    override fun getItemCount(): Int = sneakers.size

    class SneakerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val sneakerImage: ImageView = itemView.findViewById(R.id.sneakerImage)
        private val sneakerName: TextView = itemView.findViewById(R.id.sneakerName)
        private val sneakerPrice: TextView = itemView.findViewById(R.id.sneakerPrice)

        @SuppressLint("SetTextI18n")
        fun bind(sneaker: Sneaker, photoUrl: String?) {
            sneakerName.text = sneaker.name
            sneakerPrice.text = "$${sneaker.price}"
            if (photoUrl != null) {
                Glide.with(itemView.context).load(photoUrl).into(sneakerImage)
            }
        }
    }
}