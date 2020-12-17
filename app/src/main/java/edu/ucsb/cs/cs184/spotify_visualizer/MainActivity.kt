package edu.ucsb.cs.cs184.spotify_visualizer

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var btn = findViewById<Button>(R.id.spotify_login_btn)

        //set button click to send to Spotify API Login
        btn.setOnClickListener {
            val int = Intent(this, Main_Screen::class.java)
            startActivity(int)
        }
    }
}