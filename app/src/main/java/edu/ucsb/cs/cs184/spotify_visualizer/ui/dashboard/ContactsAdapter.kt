package edu.ucsb.cs.cs184.spotify_visualizer.ui.dashboard

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import edu.ucsb.cs.cs184.spotify_visualizer.R

class ContactsAdapter (private val mSongs: List<Song>, val itemClickListener: (View, Int, Int) -> Unit, val setImageCover: (ImageView, Int) -> Unit) : RecyclerView.Adapter<ContactsAdapter.ViewHolder>() {

    fun <T : RecyclerView.ViewHolder> T.onClick(event: (view: View, position: Int, type: Int) -> Unit): T {
        itemView.setOnClickListener {
            event.invoke(it, getAdapterPosition(), getItemViewType())
        }
        return this
    }

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    inner class ViewHolder(listItemView: View) : RecyclerView.ViewHolder(listItemView) {
        // Your holder should contain and initialize a member variable
        // for any view that will be set as you render a row
        val songTextView = itemView.findViewById<TextView>(R.id.album_title)
        val coverArt = itemView.findViewById<ImageView>(R.id.album_cover)

    }

    // ... constructor and member variables
    // Usually involves inflating a layout from XML and returning the holder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactsAdapter.ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        // Inflate the custom layout
        val contactView = inflater.inflate(R.layout.song_display, parent, false)

        // Return a new holder instance
        val contactViewHolder = ViewHolder(contactView)

        contactViewHolder.onClick(itemClickListener)
        return contactViewHolder
    }
    // Involves populating data into the item through holder
    override fun onBindViewHolder(viewHolder: ContactsAdapter.ViewHolder, position: Int) {
        // Get the data model based on position
        val song: Song = mSongs[position]
        // Set item views based on your views and data model
        val songTv = viewHolder.songTextView
        songTv.text = song.getName()

        val art = viewHolder.coverArt
        setImageCover(art, position)
    }

    // Returns the total count of items in the list
    override fun getItemCount() = mSongs.size

}
