package com.example.royal_chamber_app.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.royal_chamber_app.Cloth
import com.example.royal_chamber_app.HomeDash
import com.example.royal_chamber_app.R

class Horizontal_RecyclerView(private val clothlist:ArrayList<Cloth>):RecyclerView.Adapter<Horizontal_RecyclerView.clothviewholder>() {

    var onItemClick:((Cloth)-> Unit)?=null

    class clothviewholder(itemView:View):RecyclerView.ViewHolder(itemView){
        val imageView:ImageView=itemView.findViewById<ImageView>(R.id.imageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): clothviewholder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.homerow,parent,false)
        return clothviewholder(view)
    }

    override fun onBindViewHolder(holder: clothviewholder, position: Int) {
       val clothd=clothlist[position]
        holder.imageView.setImageResource(clothd.image)

        holder.itemView.setOnClickListener{
            onItemClick?.invoke(clothd)
        }
    }

    override fun getItemCount(): Int {
        return clothlist.size
    }
}