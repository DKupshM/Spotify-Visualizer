package edu.ucsb.cs.cs184.spotify_visualizer

import android.content.Intent
import android.graphics.Bitmap
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
import com.spotify.protocol.client.CallResult
import com.spotify.protocol.types.Image
import com.spotify.protocol.types.ImageUri
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
                setOf( R.id.navigation_dashboard, R.id.navigation_home ))

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    private fun showToast(text: String) {
        mToast?.cancel()
        mToast = Toast.makeText(this, text, Toast.LENGTH_SHORT)
        mToast?.show()
    }

    fun playSong(uri:String) {
        assertAppRemoteConnected().playerApi.play(uri)
    }

    fun playPauseClicked(view: View) {
        assertAppRemoteConnected().playerApi.getPlayerState()
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
        assertAppRemoteConnected().playerApi.skipNext()
    }

    fun prevClicked(view: View){
        assertAppRemoteConnected().playerApi.skipPrevious()
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

    fun getTrackCoverArt(imageURI : String): CallResult<Bitmap>? {
        return assertAppRemoteConnected()
            .imagesApi
            .getImage(ImageUri(imageURI),Image.Dimension.LARGE)

    }

    private fun updateSongTitle(playerState: PlayerState) {
        val track: Track? = playerState.track
        if (track != null) {
            findViewById<TextView>(R.id.song_title).text = track.name.toString()
        } else {
            findViewById<TextView>(R.id.song_title).text = "None Playing"
        }
    }

    private fun updateArtistName(playerState: PlayerState) {
        val track: Track? = playerState.track
        if (track != null) {
            findViewById<TextView>(R.id.artist_name).text = track.artist.name.toString()
        } else {
            findViewById<TextView>(R.id.artist_name).text = ""
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
                val errorStr = error.toString()

                if (errorStr == "com.spotify.android.appremote.api.error.CouldNotFindSpotifyApp")
                    showToast("Please Download Spotify App from Play Store to connect to Visualizer")

                else if(errorStr == "com.spotify.android.appremote.api.error.NotLoggedInException: {\"message\":\"The user must go to the Spotify and log-in\"}")
                    showToast("User must go to Spotify App and log-in or sign up")

                else{ showToast(errorStr) }

                onDisconnected()
            }
        }
    }

    private fun onConnected() {
        showToast("Connected")

        assertAppRemoteConnected().playerApi
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
        updateArtistName(playerState)
        Log.d("S", playerState.track.imageUri.toString())
    }


    private fun onDisconnected() {
        val int = Intent(this, MainActivity::class.java)
        startActivity(int)
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
        showToast("Disconnected")
        val int = Intent(this, MainActivity::class.java)
        startActivity(int)

        throw SpotifyDisconnectedException()
    }

    override fun onStop() {
        super.onStop()
        SpotifyAppRemote.disconnect(mSpotifyAppRemote)
    }
}