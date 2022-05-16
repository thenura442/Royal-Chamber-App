package com.example.royal_chamber_app.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.royal_chamber_app.Eventbus.UpdateCartEvent
import com.example.royal_chamber_app.Listener.ICartLoadListner
import com.example.royal_chamber_app.Listener.IRecyclerClickListener
import com.example.royal_chamber_app.Model.CartModel
import com.example.royal_chamber_app.Model.MenModel
import com.example.royal_chamber_app.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import org.greenrobot.eventbus.EventBus
import java.lang.StringBuilder

class MensclothAdapter(
    private val context: Context,
    private val list:List<MenModel>,
    private val cartListener: ICartLoadListner

): RecyclerView.Adapter<MensclothAdapter.MensclothViewHolder>() {

    class MensclothViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        var imageView: ImageView?=null
        var txtName:TextView?=null
        var txtprice:TextView?=null
        private var clickListener:IRecyclerClickListener?=null

        fun setClickListener(clickListener: IRecyclerClickListener)
        {
            this.clickListener=clickListener;
        }
        init{
            imageView = itemView.findViewById(R.id.imageView) as ImageView
            txtName = itemView.findViewById(R.id.txtName) as TextView;
            txtprice = itemView.findViewById(R.id.txtPrice) as TextView;

            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            clickListener!!.onItemClickListener(v,adapterPosition)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MensclothViewHolder {
        return MensclothViewHolder(LayoutInflater.from(context).inflate(R.layout.layout_men_item,parent,false))
    }

    override fun onBindViewHolder(holder: MensclothViewHolder, position: Int) {
       Glide.with(context)
           .load(list[position].image)
           .into(holder.imageView!!)
        holder.txtName!!.text=StringBuilder().append(list[position].name)
        holder.txtprice!!.text=StringBuilder("Rs ").append(list[position].price)

        holder.setClickListener(object :IRecyclerClickListener{
            override fun onItemClickListener(view: View?, position: Int) {
                addToCart(list[position])
            }

        })
    }

    private fun addToCart(menModel: MenModel) {
    val userCart=FirebaseDatabase.getInstance().getReference("Cart").child("UNIQUE_USER_ID")

        userCart.child(menModel.key!!)
            .addListenerForSingleValueEvent(object :ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    if(snapshot.exists())
                    {
                        val cartModel=snapshot.getValue(CartModel::class.java)
                        val updateData:MutableMap<String,Any> = HashMap()
                        cartModel!!.quantity=cartModel!!.quantity+1;
                        updateData["quantity"]=cartModel!!.quantity+1;
                        updateData["totallPrice"]=cartModel!!.quantity*cartModel.price!!.toFloat();
                        userCart.child(menModel.key!!).updateChildren(updateData).addOnSuccessListener {
                            EventBus.getDefault().postSticky(UpdateCartEvent())
                            cartListener.onCartLoadFailed("Success Add to Cart")
                        }
                            .addOnFailureListener{e->  cartListener.onCartLoadFailed(e.message)}
                    }else{
                        val cartModel=CartModel()
                        cartModel.key=menModel.key
                        cartModel.name=menModel.name
                        cartModel.image=menModel.image
                        cartModel.price=menModel.price
                        cartModel.quantity=1
                        cartModel.totalPrice=menModel.price!!.toFloat()

                        userCart.child(menModel.key!!).setValue(cartModel).addOnSuccessListener {
                            EventBus.getDefault().postSticky(UpdateCartEvent())
                            cartListener.onCartLoadFailed("Sucess add to cart")
                        }.addOnFailureListener{e->  cartListener.onCartLoadFailed(e.message)}
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                   cartListener.onCartLoadFailed(error.message)
                }

            })
    }

    override fun getItemCount(): Int {
        return list.size
    }
}