package com.abhiiscoding.puzzlegameof15

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnStartGame: Button = findViewById(R.id.btnStartGame)
        val userName: EditText = findViewById(R.id.et_name)

        btnStartGame.setOnClickListener {
            if (userName.text.isEmpty()){
                Toast.makeText(this,"Please enter a name first.", Toast.LENGTH_SHORT).show()
            }else{
                val intent = Intent(this, GameActivity::class.java)
                intent.putExtra(Constants.USER_NAME, userName.text.toString())
                startActivity(intent)
            }
        }
    }
}