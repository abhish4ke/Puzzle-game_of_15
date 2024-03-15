package com.abhiiscoding.puzzlegameof15

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class ResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)


        val name: TextView = findViewById(R.id.name_result_activity)
        val time: TextView = findViewById(R.id.time_result_activity)
        time.text = intent.getStringExtra(Constants.TIME)
        name.text = intent.getStringExtra(Constants.USER_NAME)
    }
}