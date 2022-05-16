package com.example.royal_chamber_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Message
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.royal_chamber_app.Adapter.Horizontal_RecyclerView
import com.example.royal_chamber_app.Adapter.MensclothAdapter
import com.example.royal_chamber_app.Eventbus.UpdateCartEvent
import com.example.royal_chamber_app.Listener.ICartLoadListner
import com.example.royal_chamber_app.Listener.IClothLoadListenerMen
import com.example.royal_chamber_app.Model.CartModel
import com.example.royal_chamber_app.Model.MenModel
import com.example.royal_chamber_app.Utils.SpaceItemDecoration
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_mensc.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class Mensc : AppCompatActivity(), IClothLoadListenerMen,ICartLoadListner {




    lateinit var clothLoadListenerMen:IClothLoadListenerMen
    lateinit var cartLoadListenerMen:ICartLoadListner
    override fun onStart() {
        super.onStart()

        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        super.onStop()
        super.onStop()
        if (EventBus.getDefault().hasSubscriberForEvent(UpdateCartEvent::class.java))
            EventBus.getDefault().removeStickyEvent(UpdateCartEvent::class.java)
        EventBus.getDefault()
            .unregister(this)
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
     fun onUpdateCartEvent(event : UpdateCartEvent){
        countCartFromFirebase()}

        override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mensc)
        Menu()
        init()
        loadMensFromFirebase()
        countCartFromFirebase()

    }

    private fun countCartFromFirebase() {
        val cartModels:MutableList<CartModel> = ArrayList()
        FirebaseDatabase.getInstance().getReference("Cart").child("UNIQUE_USER_ID").addListenerForSingleValueEvent(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for(cartSnapshot in snapshot.children)
                {
                    val cartModel=cartSnapshot.getValue(CartModel::class.java)
                    cartModel!!.key=cartSnapshot.key
                    cartModels.add(cartModel)

                }
                cartLoadListenerMen.onCartLoadSuccess(cartModels)
            }

            override fun onCancelled(error: DatabaseError) {
                cartLoadListenerMen.onCartLoadFailed(error.message)
            }

        })
    }

    private fun loadMensFromFirebase() {
        val menModels:MutableList<MenModel> = ArrayList()
        FirebaseDatabase.getInstance()
            .getReference("mens")

            .addListenerForSingleValueEvent(object:ValueEventListener{
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

    private fun Menu() {
        val drawerLayout: DrawerLayout = findViewById(R.id.drawerLayoutA2)
        val navView: NavigationView = findViewById(R.id.nav_viewA2)
        val btn = findViewById<Button>(R.id.menuA2)

        btn.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                drawerLayout.openDrawer(GravityCompat.START);
            }

        })


        navView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_dash -> {
                    this.startActivity(Intent(this, HomeDash::class.java))
                    Toast.makeText(
                        applicationContext,
                        "Clicked Dashboard",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                R.id.nav_profile -> {
                    Toast.makeText(
                        applicationContext,
                        "Clicked Profile",
                        Toast.LENGTH_SHORT
                    ).show()
                    this.startActivity(Intent(this, HomeDash::class.java))
                }
                R.id.nav_cart ->{ Toast.makeText(
                    applicationContext,
                    "Clicked Cart",
                    Toast.LENGTH_SHORT
                ).show()
                    this.startActivity(Intent(this, Cart::class.java))}
                R.id.nav_fav -> Toast.makeText(
                    applicationContext,
                    "Clicked Favourites",
                    Toast.LENGTH_SHORT
                ).show()
                R.id.nav_pay -> {Toast.makeText(
                    applicationContext,
                    "Clicked Payments",
                    Toast.LENGTH_SHORT
                ).show()
                    this.startActivity(Intent(this, Orderdisplay::class.java))}
                R.id.nav_settings -> Toast.makeText(
                    applicationContext,
                    "Clicked Settings",
                    Toast.LENGTH_SHORT
                ).show()
                R.id.nav_logout -> {
                    this.startActivity(Intent(this, Login::class.java))
                    Toast.makeText(
                        applicationContext,
                        "Clicked Logout",
                        Toast.LENGTH_SHORT
                    ).show()
                }

            }
            true

        }


    }

    private fun init()
    {
        clothLoadListenerMen=this
        cartLoadListenerMen=this
        val gridLayoutManager=GridLayoutManager(this,2)
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
        Snackbar.make(MainLayoutA2,message!!,Snackbar.LENGTH_LONG).show()
    }

    override fun onCartLoadSuccess(cartModelList: List<CartModel>?) {
        var cartsum=0
        for(cartModel in cartModelList!!)cartsum+=cartModel!!.quantity
        badge!!.setNumber(cartsum)
    }

    override fun onCartLoadFailed(message: String?) {
        Snackbar.make(MainLayoutA2,message!!,Snackbar.LENGTH_LONG).show()
    }

}