package com.example.royal_chamber_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class Orderconfirmed : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_orderconfirmed)
        var btn6=findViewById<Button>(R.id.paybutton6)
        btn6.setOnClickListener{
            this.startActivity(Intent(this, HomeDash::class.java))
        }
    }
}