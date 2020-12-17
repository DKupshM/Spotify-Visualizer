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
        songs.add(Song("spotify:playlist:37i9dQZF1DX4dyzvuaRJ0n",
            "Mint Playlist",
            "spotify:image:ab67616d0000b273e49b1bcaa060156dd2019e17"))
        songs.add(Song("spotify:album:79C6OXDHPGZtCb7ySUxyIV","Holiday Songs",
            "spotify:image:ab67616d0000b27356a8fbe72905057674ac41f7"))
        songs.add(Song("spotify:playlist:37i9dQZF1DXcBWIGoYBM5M","Todays Hits","spotify:image:ab67616d0000b27333b8541201f1ef38941024be"))
        songs.add(Song("spotify:playlist:3Di88mvYplBtkDBIzGLiiM","EDM Hits","spotify:image:ab67616d0000b273b6567be9f8b996a2b5f9b7fa"))
        songs.add(Song("spotify:playlist:37i9dQZF1DWXRqgorJj26U","Rock Classics","spotify:image:ab67616d0000b273fc4f17340773c6c3579fea0d"))


        // Create adapter passing in the sample user data
        val adapter = ContactsAdapter(songs, itemOnClick, setImageCover)
        // Attach the adapter to the recyclerview to populate items
        rvSongs.adapter = adapter
        // Set layout manager to position the items
        rvSongs.layoutManager = LinearLayoutManager(this.context)

        return root
    }




    val itemOnClick: (View, Int, Int) -> Unit = { view, position, type ->
        var song = songs.get(position)
        (activity as Main_Screen).playSong(song.getUri())
        Log.d("Hello", song.getImageURL())
    }

    val setImageCover: (ImageView, Int) -> Unit = { view, position ->
        var song = songs.get(position)
        (activity as Main_Screen).getTrackCoverArt(song.getImageURL())!!
            .setResultCallback { bitmap ->
                view.setImageBitmap(bitmap)
            }
    }
}

