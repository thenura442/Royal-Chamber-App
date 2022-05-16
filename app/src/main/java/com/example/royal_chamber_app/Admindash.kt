package com.example.royal_chamber_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class Admindash : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admindash)

        var logout=findViewById<Button>(R.id.button)
        var one=findViewById<Button>(R.id.button2)
        var two=findViewById<Button>(R.id.button3)
        var three=findViewById<Button>(R.id.button4)

        logout.setOnClickListener{
            this.startActivity(Intent(this, Login::class.java))

        }
        three.setOnClickListener{
            this.startActivity(Intent(this, Displayproducts::class.java))
        }

    }
}