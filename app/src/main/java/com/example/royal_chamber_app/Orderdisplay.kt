package com.example.royal_chamber_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.royal_chamber_app.Adapter.OrderDetailAdapter
import com.google.android.material.navigation.NavigationView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class Orderdisplay : AppCompatActivity() {
    private lateinit var  OrderRecylcerview:RecyclerView
    private lateinit var orderArrayList: ArrayList<OrderDClass>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_orderdisplay)
        Menu()
        OrderRecylcerview=findViewById(R.id.rview)
        OrderRecylcerview.layoutManager=LinearLayoutManager(this)
        OrderRecylcerview.setHasFixedSize(true)

        orderArrayList= arrayListOf<OrderDClass>()
        getOrderData()

    }

    private fun getOrderData() {
       var db= FirebaseDatabase.getInstance().getReference("payment")
        db.addValueEventListener(object:ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for(ordersnap in snapshot.children){
                        val order=ordersnap.getValue(OrderDClass::class.java)
                        orderArrayList.add(order!!)
                    }
                    OrderRecylcerview.adapter=OrderDetailAdapter(orderArrayList)
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }

    private fun Menu()
    {
        val drawerLayout: DrawerLayout = findViewById(R.id.drawerLayoutA9)
        val navView: NavigationView = findViewById(R.id.nav_viewA9)
        val btn = findViewById<Button>(R.id.menuA9)

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

}