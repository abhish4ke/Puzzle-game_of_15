package com.abhiiscoding.puzzlegameof15

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class GameActivity : AppCompatActivity() {

    private lateinit var tiles: MutableList<Button>
    private lateinit var emptyTile: Button
    private var mUsername: String? = null
    private var mTime: String? = null

    //Timer-------------------
    private var startTime: Long = 0
    private var timerHandler: Handler = Handler()
    private lateinit var timerRunnable: Runnable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        initializeTiles()
        shuffleTiles()

        mUsername = intent.getStringExtra(Constants.USER_NAME)
        val playerName:TextView = findViewById(R.id.player_name)
        playerName.text = "Player name : $mUsername"

        //Timer-----------------------------------------------------
        val timerTextView = findViewById<TextView>(R.id.timerTextView)
        startTime = System.currentTimeMillis()
        timerRunnable = object : Runnable {
            override fun run() {
                val elapsedTime = System.currentTimeMillis() - startTime
                val seconds = (elapsedTime / 1000).toInt()
                val minutes = seconds / 60
                val secondsDisplay = seconds % 60
                val timeText = String.format("%02d:%02d", minutes, secondsDisplay)
                timerTextView.text = timeText
                mTime = timeText
                timerHandler.postDelayed(this, 1000)
            }
        }
        timerHandler.postDelayed(timerRunnable, 1000)


    }

    private fun initializeTiles() {
        tiles = mutableListOf(
            findViewById(R.id.btn1), findViewById(R.id.btn2), findViewById(R.id.btn3), findViewById(R.id.btn4),
            findViewById(R.id.btn5), findViewById(R.id.btn6), findViewById(R.id.btn7), findViewById(R.id.btn8),
            findViewById(R.id.btn9), findViewById(R.id.btn10), findViewById(R.id.btn11), findViewById(R.id.btn12),
            findViewById(R.id.btn13), findViewById(R.id.btn14), findViewById(R.id.btn15), findViewById(R.id.btn16)
        )

        // Find the empty tile
        emptyTile = findViewById(R.id.btn16)
    }


    private fun shuffleTiles() {
        val numbers = (1..15).shuffled() + 0 // 0 represents the empty tile
        for (i in 0..15) {
            if (numbers[i] == 0) {
                emptyTile = tiles[i]
                continue
            }
            tiles[i].text = numbers[i].toString()
            tiles[i].setOnClickListener { btnClicked(it) }
        }
    }



    fun btnClicked(view: View) {
        val clickedButton = view as Button

        // Check if the clicked button can be moved to the empty spot
        if (canMove(clickedButton)) {
            swapTiles(clickedButton, emptyTile)
        }

        emptyTile.setBackgroundResource(R.drawable.empty_tile_bg)

        // Check if the game has been won
        if (checkWinCondition()) {
            handleWin()
        }
    }

    private fun canMove(button: Button): Boolean {
        // Get the positions of the clicked button and the empty tile
        val clickedPosition = tiles.indexOf(button)
        val emptyPosition = tiles.indexOf(emptyTile)

        // Check if the clicked button is adjacent to the empty tile
        return (Math.abs(clickedPosition / 4 - emptyPosition / 4) == 1 && clickedPosition % 4 == emptyPosition % 4) ||
                (Math.abs(clickedPosition % 4 - emptyPosition % 4) == 1 && clickedPosition / 4 == emptyPosition / 4)
    }

    private fun swapTiles(button1: Button, button2: Button) {
        val tempText = button1.text
        val bg1 = button1.background
        val bg2 = button2.background

        button1.text = button2.text
        button2.text = tempText

        // Update the empty tile reference
        emptyTile = button1
        button1.background = bg2
        button2.background = bg1
    }

    private fun checkWinCondition(): Boolean {
        for (i in 0..14) {
            if (tiles[i].text.toString() != (i + 1).toString()) {
                return false
            }
        }
        return tiles[15].text.toString() == ""
    }

    private fun handleWin() {
        timerHandler.removeCallbacks(timerRunnable)
        Toast.makeText(this, "Congratulations! You've won!", Toast.LENGTH_SHORT).show()
        val intent = Intent(this, ResultActivity::class.java)
        intent.putExtra(Constants.USER_NAME, mUsername)
        intent.putExtra(Constants.TIME, mTime)
        startActivity(intent)
    }
}



























