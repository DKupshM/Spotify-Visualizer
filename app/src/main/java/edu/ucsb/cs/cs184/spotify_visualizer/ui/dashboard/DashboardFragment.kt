package edu.ucsb.cs.cs184.spotify_visualizer.ui.dashboard

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
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
        songs.add(Song("spotify:playlist:37i9dQZF1DX4dyzvuaRJ0n","Hi",""))
        songs.add(Song("spotify:playlist:37i9dQZF1DX4dyzvuaRJ0n","There",""))
        songs.add(Song("spotify:playlist:37i9dQZF1DX4dyzvuaRJ0n","Ladies",""))
        songs.add(Song("spotify:playlist:37i9dQZF1DX4dyzvuaRJ0n","And",""))
        songs.add(Song("spotify:playlist:37i9dQZF1DX4dyzvuaRJ0n","Gents",""))


        // Create adapter passing in the sample user data
        val adapter = ContactsAdapter(songs, itemOnClick)
        // Attach the adapter to the recyclerview to populate items
        rvSongs.adapter = adapter
        // Set layout manager to position the items
        rvSongs.layoutManager = LinearLayoutManager(this.context)

        val svSongs = activity?.findViewById<SearchView>(R.id.Spotify_search)
        svSongs?.isIconified = false
        svSongs?.setOnSearchClickListener{
            val search = svSongs.query.toString()
            makeToast(search)
        }

        return root
    }


    val itemOnClick: (View, Int, Int) -> Unit = { view, position, type ->
        var song = songs.get(position)
        (activity as Main_Screen).playSong(song.getUri())
        Log.d("Hello", song.getName())
    }


    private fun makeToast(text: String){
        mToast?.cancel()
        mToast = Toast.makeText(this.activity, text, Toast.LENGTH_SHORT)
        mToast?.show()
    }
}

