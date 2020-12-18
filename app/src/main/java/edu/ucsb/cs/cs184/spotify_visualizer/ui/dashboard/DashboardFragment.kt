package edu.ucsb.cs.cs184.spotify_visualizer.ui.dashboard

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import edu.ucsb.cs.cs184.spotify_visualizer.Main_Screen
import edu.ucsb.cs.cs184.spotify_visualizer.R

class DashboardFragment : Fragment() {
    var playlists: ArrayList<Playlist> = ArrayList<Playlist>()

    var mToast : Toast? = null

    private lateinit var dashboardViewModel: DashboardViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dashboardViewModel =
            ViewModelProvider(this).get(DashboardViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_dashboard, container, false)


        val rvPlaylists = root.findViewById<View>(R.id.searched_Songs) as RecyclerView
        playlists = createPlaylist()

        // Create adapter passing in the sample user data
        val adapter = ContactsAdapter(playlists, itemOnClick, setImageCover)
        // Attach the adapter to the recyclerview to populate items
        rvPlaylists.adapter = adapter
        // Set layout manager to position the items
        rvPlaylists.layoutManager = LinearLayoutManager(this.context)

        return root
    }


    val itemOnClick: (View, Int, Int) -> Unit = { view, position, type ->
        var playlist = playlists.get(position)
        (activity as Main_Screen).playSong(playlist.getUri())
        Log.d("Hello", playlist.getImageURL())
    }

    val setImageCover: (ImageView, Int) -> Unit = { view, position ->
        var playlist = playlists.get(position)
        (activity as Main_Screen).getTrackCoverArt(playlist.getImageURL())!!
            .setResultCallback { bitmap ->
                view.setImageBitmap(bitmap)
            }
    }
}

