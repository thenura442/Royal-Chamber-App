package com.example.royal_chamber_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class Login : AppCompatActivity() {
    private val database = FirebaseDatabase.getInstance("https://royalchamberapp-default-rtdb.firebaseio.com//")
    private val databaseref=database.reference.child("login")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        //Assigning XML compoenents to variables

        var username=findViewById<EditText>(R.id.username)
        var password=findViewById<EditText>(R.id.password)
        var logbutton=findViewById<Button>(R.id.logbutton)
        var logbutton2=findViewById<Button>(R.id.logbutton2)

logbutton2.setOnClickListener(object: View.OnClickListener
{
    override fun onClick(view: View?) {
        startActivity(Intent(this@Login,Signup::class.java))
    }
})
        logbutton.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                var username2 = username.getText().toString()
                var password2 = password.getText().toString()
                if (username2.isEmpty() or password2.isEmpty()) {
                    Toast.makeText(
                        this@Login,
                        "Please enter all credentials",
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    val run=databaseref.orderByChild("username").equalTo(username2)
                    val valuelistener=object : ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            for (ds in snapshot.children) {

                                var pass= ds.child("password").getValue().toString()
                                var type= ds.child("userType").getValue().toString()
                                if(pass.equals(password2))
                                {
                                    if(type.equals("Customer"))
                                    {
                                        startActivity(Intent(this@Login,HomeDash::class.java))
                                    }
                                    else if(type.equals("Admin"))

                                   {
                                       startActivity(Intent(this@Login,Admindash::class.java))
                                    }
                                }
                                else{
                                    Toast.makeText(this@Login,"Invalid credentials",Toast.LENGTH_LONG).show()
                                }

                            }

                        }


                        override fun onCancelled(error: DatabaseError) {
                            Log.d("Response", error.getMessage())
                        }
                    }
                    run.addListenerForSingleValueEvent(valuelistener)

                }

            }
        })
    }
}