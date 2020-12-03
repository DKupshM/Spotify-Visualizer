package edu.ucsb.cs.cs184.spotify_visualizer

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var btn = findViewById<Button>(R.id.spotify_login_btn)
        //set button click to send to Spotify API Login
        btn.setOnClickListener {
            val int = Intent(this,Main_Screen::class.java)
            startActivity(int)
        }
    }
}