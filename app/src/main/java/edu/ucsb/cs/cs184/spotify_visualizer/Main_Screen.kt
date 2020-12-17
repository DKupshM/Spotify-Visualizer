package edu.ucsb.cs.cs184.spotify_visualizer

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.gson.GsonBuilder
import com.spotify.android.appremote.api.ConnectionParams
import com.spotify.android.appremote.api.Connector
import com.spotify.android.appremote.api.SpotifyAppRemote
import com.spotify.android.appremote.api.error.SpotifyDisconnectedException
import com.spotify.protocol.types.Image
import com.spotify.protocol.types.PlayerState
import com.spotify.protocol.types.Track
import kotlinx.coroutines.launch
import kotlin.coroutines.Continuation
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine


class Main_Screen : AppCompatActivity() {

    private val gson = GsonBuilder().setPrettyPrinting().create()

    private val CLIENT_ID = "aac5e0cf9ed24f71868e35c1faeeba9c"
    private val REDIRECT_URI = "http://edu.ucsb.cs.cs184.spotify_visualizer/callback"

    private var mSpotifyAppRemote: SpotifyAppRemote? = null

    private var mToast: Toast? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_screen)
        connect(true);
        val navView: BottomNavigationView = findViewById(R.id.nav_view)


        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
                setOf(
                        R.id.navigation_dashboard, R.id.navigation_home, R.id.navigation_profile
                )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    private fun showToast(text: String) {
        mToast?.cancel()
        mToast = Toast.makeText(this, text, Toast.LENGTH_SHORT)
        mToast?.show()
    }

    fun playPauseClicked(view: View) {
        mSpotifyAppRemote!!.playerApi.getPlayerState()
                .setResultCallback { playerState ->
                    if(playerState.isPaused) {
                        mSpotifyAppRemote!!.playerApi.resume()
                    } else {
                        mSpotifyAppRemote!!.playerApi.pause()
                    }
                }
                .setErrorCallback { throwable ->
                    showToast("Error Pausing")
                }
    }

    fun skipClicked(view: View) {
        mSpotifyAppRemote!!.playerApi.skipNext()
    }

    fun prevClicked(view: View){
        mSpotifyAppRemote!!.playerApi.skipPrevious()
    }


    private fun updateTrackCoverArt(playerState: PlayerState) {
        // Get image from track
        assertAppRemoteConnected()
                .imagesApi
                .getImage(playerState.track.imageUri, Image.Dimension.LARGE)
                .setResultCallback { bitmap ->
                    findViewById<ImageView>(R.id.track_cover).setImageBitmap(bitmap)
                }
    }

    private fun updateSongTitle(playerState: PlayerState) {
        val track: Track? = playerState.track
        if (track != null) {
            findViewById<TextView>(R.id.song_title).text = track.name.toString()
        } else {
            findViewById<TextView>(R.id.song_title).text = "None Playing"
        }
    }


    private fun updatePlayPauseButton(playerState: PlayerState) {
        // Invalidate play / pause
        if (playerState.isPaused) {
            findViewById<ImageButton>(R.id.pause_play_button).setImageResource(R.drawable.ic_play_arrow)
        } else {
            findViewById<ImageButton>(R.id.pause_play_button).setImageResource(R.drawable.ic_stop)
        }
    }

    private fun connect(showAuthView: Boolean) {
        SpotifyAppRemote.disconnect(mSpotifyAppRemote)
        lifecycleScope.launch {
            try {
                mSpotifyAppRemote = connectToAppRemote(showAuthView)
                onConnected()
            } catch (error: Throwable) {
                onDisconnected()
            }
        }
    }

    private fun onConnected() {
        showToast("Connected")

        mSpotifyAppRemote?.playerApi?.play("spotify:playlist:37i9dQZF1DX2sUQwD7tbmL");

        mSpotifyAppRemote!!.playerApi
                .subscribeToPlayerState()
                .setEventCallback { playerState: PlayerState ->
                    playerStateEventCallback(playerState)
                }
    }

    private fun playerStateEventCallback(playerState: PlayerState) {
        Log.v("Main Activity", String.format("Player State: %s", gson.toJson(playerState)))
        updatePlayPauseButton(playerState)
        updateTrackCoverArt(playerState)
        updateSongTitle(playerState)
    }

    private fun onDisconnected() {
        showToast("Disconnected")
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

    private fun assertAppRemoteConnected(): SpotifyAppRemote {
        mSpotifyAppRemote?.let {
            if (it.isConnected) {
                return it
            }
        }
        Log.e("", "Disconnected")
        throw SpotifyDisconnectedException()
    }

    override fun onStop() {
        super.onStop()
        SpotifyAppRemote.disconnect(mSpotifyAppRemote)
    }
}