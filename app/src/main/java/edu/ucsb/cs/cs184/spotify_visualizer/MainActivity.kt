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

    private val CLIENT_ID = "aac5e0cf9ed24f71868e35c1faeeba9c"
    private val REDIRECT_URI = "http://edu.ucsb.cs.cs184.spotify_visualizer/callback"
    private var mSpotifyAppRemote: SpotifyAppRemote? = null

    //val myCanvas = Sketch()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var btn = findViewById<Button>(R.id.spotify_login_btn)
        //set button click to send to Spotify API Login
        btn.setOnClickListener {
            connect(true)
            val int = Intent(this, Main_Screen::class.java)
            startActivity(int)
        }

//        setContentView(R.layout.fragment_home)
//        val myFragment = PFragment(myCanvas)
//        val canvas_container = findViewById<FrameLayout>(R.id.canvas_container)
//        myFragment.setView(canvas_container,this)


    }

    override fun onStart() {
        Log.d("Main Activity", "On Start!")
        super.onStart()

    }

    private fun connect(showAuthView: Boolean) {

        SpotifyAppRemote.disconnect(mSpotifyAppRemote)
        lifecycleScope.launch {
            try {
                mSpotifyAppRemote = connectToAppRemote(showAuthView)
                onConnected()
            } catch (error: Throwable) {
                Log.e("Main Activity", error.toString())
                onDisconnected()
            }
        }
    }

    private fun onConnected() {
        Log.d("Main Activity", "Connected")
    }

    private fun onDisconnected() {
        Log.d("Main Activity", "Disconnected")
    }

    private suspend fun connectToAppRemote(showAuthView: Boolean): SpotifyAppRemote? =
            suspendCoroutine { cont: Continuation<SpotifyAppRemote> ->
                SpotifyAppRemote.connect(
                        application,
                        ConnectionParams.Builder(CLIENT_ID)
                                .setRedirectUri(REDIRECT_URI)
                                .showAuthView(showAuthView)
                                .build(),
                        object : Connector.ConnectionListener {
                            override fun onConnected(spotifyAppRemote: SpotifyAppRemote) {
                                cont.resume(spotifyAppRemote)
                            }

                            override fun onFailure(error: Throwable) {
                                cont.resumeWithException(error)
                            }
                        })
            }

    override fun onStop() {
        super.onStop()
    }
}