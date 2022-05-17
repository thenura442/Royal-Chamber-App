package com.example.royal_chamber_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.recyclerview.widget.GridLayoutManager
import com.example.royal_chamber_app.Adapter.MensclothAdapter
import com.example.royal_chamber_app.Listener.ICartLoadListner
import com.example.royal_chamber_app.Listener.IClothLoadListenerMen
import com.example.royal_chamber_app.Model.CartModel
import com.example.royal_chamber_app.Model.MenModel
import com.example.royal_chamber_app.Utils.SpaceItemDecoration
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_mensc.*

class Displayproducts : AppCompatActivity(), IClothLoadListenerMen ,ICartLoadListner{

    lateinit var clothLoadListenerMen:IClothLoadListenerMen
    lateinit var cartLoadListenerMen: ICartLoadListner
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_displayproducts)
        init()
        loadMensFromFirebase()





    }
    private fun loadMensFromFirebase() {
        val menModels:MutableList<MenModel> = ArrayList()
        FirebaseDatabase.getInstance()
            .getReference("mens")

            .addListenerForSingleValueEvent(object: ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if(snapshot.exists())
                    {
                        for(clothSnapshot in snapshot.children)
                        {
                            val menModel=clothSnapshot.getValue(MenModel::class.java)
                            menModel!!.key=clothSnapshot.key
                            menModels.add(menModel)
                        }
                        clothLoadListenerMen.onLoadSuccess(menModels)
                    }
                    else{
                        clothLoadListenerMen.onLoadFailed("No Items Available")
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    clothLoadListenerMen.onLoadFailed(error.message)
                }

            })

    }
    private fun init()
    {
        clothLoadListenerMen=this
        cartLoadListenerMen=this
        val gridLayoutManager= GridLayoutManager(this,2)
        recycler_men.layoutManager=gridLayoutManager
        recycler_men.addItemDecoration(SpaceItemDecoration())
        var btnCart=findViewById<ImageView>(R.id.cartButton)
        btnCart.setOnClickListener{
            startActivity(Intent(this,Cart::class.java))
        }
    }

    override fun onLoadSuccess(clothModelList: List<MenModel>?) {
        val adapter= MensclothAdapter(this,clothModelList!!,cartLoadListenerMen)
        recycler_men.adapter=adapter

    }

    override fun onLoadFailed(message: String?) {
        Snackbar.make(MainLayoutA2,message!!, Snackbar.LENGTH_LONG).show()
    }

    override fun onCartLoadSuccess(cartModelList: List<CartModel>?) {
        TODO("Not yet implemented")
    }

    override fun onCartLoadFailed(message: String?) {
        TODO("Not yet implemented")
    }
}