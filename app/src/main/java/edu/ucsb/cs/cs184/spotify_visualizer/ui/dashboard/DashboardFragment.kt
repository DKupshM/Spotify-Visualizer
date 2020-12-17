package edu.ucsb.cs.cs184.spotify_visualizer.ui.dashboard

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import edu.ucsb.cs.cs184.spotify_visualizer.Main_Screen
import edu.ucsb.cs.cs184.spotify_visualizer.R

class DashboardFragment : Fragment() {
    var songs: ArrayList<Song> = ArrayList<Song>()

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
//        val textView: TextView = root.findViewById(R.id.text_dashboard)
//        dashboardViewModel.text.observe(viewLifecycleOwner, Observer {
//            textView.text = it
//        })


        val rvSongs = root.findViewById<View>(R.id.searched_Songs) as RecyclerView
        songs.add(Song("spotify:playlist:37i9dQZF1DX4dyzvuaRJ0n","Mint Playlist",""))
        songs.add(Song("spotify:album:79C6OXDHPGZtCb7ySUxyIV","Holiday Songs",""))
        songs.add(Song("spotify:playlist:37i9dQZF1DXcBWIGoYBM5M","Todays Hits",""))
        songs.add(Song("spotify:playlist:3Di88mvYplBtkDBIzGLiiM","EDM Hits",""))
        songs.add(Song("spotify:playlist:37i9dQZF1DWXRqgorJj26U","Rock Classics",""))


        // Create adapter passing in the sample user data
        val adapter = ContactsAdapter(songs, itemOnClick)
        // Attach the adapter to the recyclerview to populate items
        rvSongs.adapter = adapter
        // Set layout manager to position the items
        rvSongs.layoutManager = LinearLayoutManager(this.context)

        return root
    }


    val itemOnClick: (View, Int, Int) -> Unit = { view, position, type ->
        var song = songs.get(position)
        (activity as Main_Screen).playSong(song.getUri())
        Log.d("Hello", song.getName())
    }
}

