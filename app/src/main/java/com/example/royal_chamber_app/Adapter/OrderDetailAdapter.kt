package com.example.royal_chamber_app.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.royal_chamber_app.OrderDClass
import com.example.royal_chamber_app.R

class OrderDetailAdapter(private val orderlist:ArrayList<OrderDClass>) : RecyclerView.Adapter<OrderDetailAdapter.MyViewHolder>() {


    class MyViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {

        val orderid : TextView =itemView.findViewById(R.id.item_title)
        val orderpaymentmethod: TextView =itemView.findViewById(R.id.item_detail)
        val ordertot: TextView =itemView.findViewById(R.id.item_detail2)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
       val itemView = LayoutInflater.from(parent.context).inflate(R.layout.displaydata,parent,false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: (MyViewHolder), position: Int) {
      val currentitm=orderlist[position]
        holder.orderid.text=currentitm.orderid
        holder.orderpaymentmethod.text=currentitm.paymentMethod
        holder.ordertot.text=currentitm.totalprice
    }

    override fun getItemCount(): Int {
        return orderlist.size
    }


}