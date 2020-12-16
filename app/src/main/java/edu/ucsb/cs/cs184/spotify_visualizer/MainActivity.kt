package edu.ucsb.cs.cs184.spotify_visualizer

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.spotify.android.appremote.api.ConnectionParams
import com.spotify.android.appremote.api.Connector
import com.spotify.android.appremote.api.SpotifyAppRemote
import com.spotify.protocol.types.PlayerState
import com.spotify.protocol.types.Track
import edu.ucsb.cs.cs184.spotify_visualizer.ui.home.Sketch
import kotlinx.coroutines.launch
import processing.android.PFragment
import kotlin.coroutines.Continuation
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine


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