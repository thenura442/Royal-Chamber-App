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
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.royal_chamber_app.Adapter.Horizontal_RecyclerView
import com.google.android.material.navigation.NavigationView

class HomeDash : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: Horizontal_RecyclerView
    private lateinit var clothlist: ArrayList<Cloth>
    lateinit var imageId: Array<Int>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_dash)
        Menu()
        clothlist = ArrayList()

        recyclerView = findViewById(R.id.recylerview)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        clothlist.add(Cloth(R.drawable.group_23, 1))
        clothlist.add(Cloth(R.drawable.group_25, 2))
        clothlist.add(Cloth(R.drawable.group_24, 3))

        adapter = Horizontal_RecyclerView(clothlist)
        recyclerView.adapter = adapter

        adapter.onItemClick = {

            val intent = Intent(this, HomeDash::class.java)
            intent.putExtra("Pass", it)
            startActivity(intent)
            val cloth = intent.getParcelableExtra<Cloth>("Pass")
            if (cloth != null) {
                val check = cloth.no
                if (check == 1) {
                    val intent = Intent(this, Mensc::class.java)

                    startActivity(intent)
                } else if (check == 2) {
                    val intent2 = Intent(this, WomensCloth::class.java)

                    startActivity(intent2)
                } else {
                    val intent3 = Intent(this, KidsCloth::class.java)

                    startActivity(intent3)
                }
            }
        }

    }

    private fun Menu() {
        val drawerLayout: DrawerLayout = findViewById(R.id.drawerLayoutA1)
        val navView: NavigationView = findViewById(R.id.nav_viewA5)
        val btn = findViewById<Button>(R.id.menuA1)

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