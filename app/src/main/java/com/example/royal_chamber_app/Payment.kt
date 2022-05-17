package com.example.royal_chamber_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_payment.*
import java.util.*

class Payment : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)
        Menu()

        val tot=intent.getStringExtra("Totalcost")
        val random= (1000..9999).random()
        val s="AZ"
        Otxt.text=s+random
        Ptxt.text=tot
        Ctxt.text="Cash On Delivery (COD)"

        var btn3=findViewById<Button>(R.id.send)

        btn3.setOnClickListener(object:View.OnClickListener{
            override fun onClick(p0: View?) {
                var OrderiD = findViewById<TextView>(R.id.Otxt)
                var TotalCost = findViewById<TextView>(R.id.Ptxt)
                var Payment = findViewById<TextView>(R.id.Ctxt)

                val InsertPay = PaymentClass(
                    OrderiD.getText().toString(),
                    Payment.getText().toString(),TotalCost.getText().toString()
                )
                FirebaseDatabase.getInstance().getReference("payment").child(OrderiD.getText().toString()).setValue(InsertPay)
                    .addOnSuccessListener {
                        Toast.makeText(this@Payment, "Order Confirmed!!!!!!! ", Toast.LENGTH_LONG)
                            .show()
                        Toast.makeText(this@Payment, "Thank You!!!!!!! ", Toast.LENGTH_LONG).show()
                        var intent3 = (Intent(this@Payment, Orderconfirmed::class.java))
                        startActivity(intent3)
                    }.addOnFailureListener {
                    it.printStackTrace()
                }
            }
        })



    }
    private fun Menu() {
        val drawerLayout: DrawerLayout = findViewById(R.id.drawerLayoutA7)
        val navView: NavigationView = findViewById(R.id.nav_viewA7)
        val btn = findViewById<Button>(R.id.menuA7)

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