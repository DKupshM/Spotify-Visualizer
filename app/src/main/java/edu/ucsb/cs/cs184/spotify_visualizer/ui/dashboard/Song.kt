package edu.ucsb.cs.cs184.spotify_visualizer.ui.dashboard

class Song(val name: String, val artist: String) {

    companion object {
        private var lastSongId = 0
        fun createSongsList(search: String, numSongs: Int): ArrayList<Song> {
            val songs = ArrayList<Song>()
            for (i in 1..numSongs) {
                songs.add(Song("$search " + ++lastSongId, "Artist $lastSongId"))
            }
            return songs
        }

        fun getSongsList(songs: ArrayList<Song>){
            //function should find the songs that would be reccomended by search

        }
    }
}