package com.example.mysharedprefapp

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private var userMax = 6

    private var usercountkey = "users_in"

    private var userpostkey = "Users_"

    private var usersIn = 0

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)

        if (newConfig.orientation === Configuration.ORIENTATION_LANDSCAPE) {
            Toast.makeText(this, "Set to landscape", Toast.LENGTH_SHORT).show()
        } else if (newConfig.orientation === Configuration.ORIENTATION_PORTRAIT) {
            Toast.makeText(this, "Set to portrait", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val sharedPreferences = getSharedPreferences("user_info", Context.MODE_PRIVATE)

        new_user_button.setOnClickListener {
            val name = username_edittext.text.toString()

            val number = Integer.parseInt(phone_number_edittext.text.toString())


            val editor = sharedPreferences.edit()

            usersIn = sharedPreferences.getInt(usercountkey, 0)
            Log.d("TAG_LOG", "Users in $usersIn")
            if (usersIn == userMax) {
                Toast.makeText(this.getApplicationContext(), "max reached", Toast.LENGTH_LONG)
                .show()
                clearText()
            } else {
                var newUser = "UserName:" + name + "|" + "PhoneNumber:" + number
                val editor = sharedPreferences.edit()
                editor.putString(userpostkey + (usersIn+1), newUser)
                editor.putInt(usercountkey, (usersIn + 1))

                editor.commit()
                Toast.makeText(this.getApplicationContext(), "users in ${usersIn+1}", Toast.LENGTH_LONG)
                    .show()
                usersIn++
                clearText()
            }
        }

        show_saves_button.setOnClickListener {
            displayUsers()
        }

    }

    override fun onResume() {
        super.onResume()
        displayUsers()
    }

    private fun clearText() {
        username_edittext.text.clear()
        phone_number_edittext.text.clear()
    }

    private fun displayUsers() {
        val sharedPreferences = getSharedPreferences("user_info", Context.MODE_PRIVATE)

        usersIn = sharedPreferences.getInt(usercountkey, 0)
        Log.d("TAG_LOG", "Users in $usersIn")
        if(usersIn > 0) {
            val myUser = StringBuffer()
            for (i in 0 until usersIn) {
                var user = sharedPreferences.getString(userpostkey + i, "Received")
                myUser.append(user + "\n")
            }
            user_View.text = myUser.toString()
        } else {
            user_View.text = "no users"
        }
    }

}