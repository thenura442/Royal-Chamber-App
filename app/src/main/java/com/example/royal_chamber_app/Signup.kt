package com.example.royal_chamber_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class Signup : AppCompatActivity() {
    private val database = FirebaseDatabase.getInstance("https://royalchamberapp-default-rtdb.firebaseio.com//")
    private val databaseref=database.reference.child("login")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        var logbutton=findViewById<Button>(R.id.logbutton4)
        var logbutton2=findViewById<Button>(R.id.logbutton3)



        logbutton.setOnClickListener(object: View.OnClickListener
        {
            override fun onClick(view: View?) {
                startActivity(Intent(this@Signup,Login::class.java))
            }
        })

        logbutton2.setOnClickListener(object: View.OnClickListener
        {
            override fun onClick(view: View?) { var Fname=findViewById<EditText>(R.id.username2)
                var Address=findViewById<EditText>(R.id.username3)
                var Tel=findViewById<EditText>(R.id.username4)
                val Username2=findViewById<EditText>(R.id.username5)
                var Password=findViewById<EditText>(R.id.username6)
                var email=findViewById<EditText>(R.id.username7)
                var utype="Customer"

               val InsertUser=SignupClass(Address.getText().toString(),email.getText().toString(),Fname.getText().toString(),Password.getText().toString(),Tel.getText().toString(),utype,Username2.getText().toString())
                databaseref.child(Username2.getText().toString()).setValue(InsertUser).addOnSuccessListener {
                    Toast.makeText(this@Signup,"Account Created Please Login",Toast.LENGTH_LONG).show()
                }.addOnFailureListener{
                    it.printStackTrace()
                }
                    }
        })


    }

}