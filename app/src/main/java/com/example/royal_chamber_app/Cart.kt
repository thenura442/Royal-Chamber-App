package com.example.royal_chamber_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.royal_chamber_app.Adapter.MyCartAdapter
import com.example.royal_chamber_app.Eventbus.UpdateCartEvent
import com.example.royal_chamber_app.Listener.ICartLoadListner
import com.example.royal_chamber_app.Model.CartModel
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_cart.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class Cart : AppCompatActivity(), ICartLoadListner {
    var cartLoadListner:ICartLoadListner?=null
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
        loadCartFromFirebase()}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)
        Menu()

        init()
        loadCartFromFirebase()

        var paybtn=findViewById<Button>(R.id.paybutton2)

        paybtn.setOnClickListener{

            caalActivity()

        }

    }

    private fun caalActivity() {
        var total=findViewById<TextView>(R.id.txtTotal)
        val intent=Intent(this, Payment::class.java).also{
            it.putExtra("Totalcost",total.getText().toString())
            startActivity(it)
        }


    }

    private fun loadCartFromFirebase() {
        val cartModels:MutableList<CartModel> = ArrayList()
        FirebaseDatabase.getInstance().getReference("Cart").child("UNIQUE_USER_ID").addListenerForSingleValueEvent(object :
            ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for(cartSnapshot in snapshot.children)
                {
                    val cartModel=cartSnapshot.getValue(CartModel::class.java)
                    cartModel!!.key=cartSnapshot.key
                    cartModels.add(cartModel)

                }
                cartLoadListner!!.onCartLoadSuccess(cartModels)
            }

            override fun onCancelled(error: DatabaseError) {
                cartLoadListner!!.onCartLoadFailed(error.message)
            }

        })
    }

    private fun Menu() {
        val drawerLayout: DrawerLayout = findViewById(R.id.drawerLayoutA6)
        val navView: NavigationView = findViewById(R.id.nav_viewA6)
        val btn = findViewById<Button>(R.id.menuA6)

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
                R.id.nav_cart -> Toast.makeText(
                    applicationContext,
                    "Clicked Cart",
                    Toast.LENGTH_SHORT
                ).show()
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
    private fun init(){
        cartLoadListner=this
        val layoutManager=LinearLayoutManager(this)
        recycler_cart!!.layoutManager=layoutManager
        recycler_cart!!.addItemDecoration(DividerItemDecoration(this,layoutManager.orientation))

    }

    override fun onCartLoadSuccess(cartModelList: List<CartModel>?) {
        var sum = 0.0
        for (cartModel in cartModelList!!)
        {
            sum+= cartModel!!.totalPrice
        }
        txtTotal.text = StringBuilder("Rs.").append(sum)
        val adapter = MyCartAdapter(this, cartModelList)
        recycler_cart!!.adapter = adapter
    }

    override fun onCartLoadFailed(message: String?) {
        Snackbar.make(mainlayout, message!!, Snackbar.LENGTH_LONG).show()
    }
}