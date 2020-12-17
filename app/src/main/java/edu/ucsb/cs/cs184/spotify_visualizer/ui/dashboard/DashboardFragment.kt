package edu.ucsb.cs.cs184.spotify_visualizer.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import edu.ucsb.cs.cs184.spotify_visualizer.R

class DashboardFragment : Fragment() {
    lateinit var songs: ArrayList<Song>

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
        // Initialize contacts
        songs = Song.createSongsList("Sample", 20)
        // Create adapter passing in the sample user data
        val adapter = ContactsAdapter(songs)
        // Attach the adapter to the recyclerview to populate items
        rvSongs.adapter = adapter
        // Set layout manager to position the items
        rvSongs.layoutManager = LinearLayoutManager(this.context)

        val svSongs = activity?.findViewById<SearchView>(R.id.Spotify_search)
        svSongs?.isIconified = false
        svSongs?.setOnSearchClickListener{
            val search = svSongs.query.toString()
            makeToast(search)
            searchSpotify(search)
        }

        return root
    }

    fun searchSpotify(songQuery : String) {

        var newSongs = Song.createSongsList(songQuery , 15)
        populateRV(newSongs)
    }

    fun populateRV(songList: ArrayList<Song>) {
        // Lookup the recyclerview in activity layout
        val rvSongs = activity?.findViewById<View>(R.id.searched_Songs) as RecyclerView

        if (this::songs.isInitialized) {
            // Initialize contacts
            var songs = Song.createSongsList("sample", 16)
            // Create adapter passing in the sample user data
            val adapter = ContactsAdapter(songs)
            // Attach the adapter to the recyclerview to populate items
            rvSongs.adapter = adapter
            // Set layout manager to position the items
            rvSongs.layoutManager = LinearLayoutManager(this.context)
        } else {       }
    }

    private fun makeToast(text: String){
        mToast?.cancel()
        mToast = Toast.makeText(this.activity, text, Toast.LENGTH_SHORT)
        mToast?.show()
    }
}

