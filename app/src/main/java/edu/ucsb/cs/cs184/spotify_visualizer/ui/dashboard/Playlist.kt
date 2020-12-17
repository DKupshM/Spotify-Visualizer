package edu.ucsb.cs.cs184.spotify_visualizer.ui.dashboard

class Playlist {

    private var uri : String = ""
    private var name : String = ""
    private var imageURL : String = ""

    constructor( uri: String,  name: String, imageURL: String) {
        this.uri = uri
        this.name = name
        this.imageURL = imageURL
    }


    fun getUri() : String {
        return uri
    }

    fun getName() : String {
        return this.name
    }

    fun getImageURL() : String {
        return  this.imageURL
    }
}

fun createPlaylist(): ArrayList<Playlist> {
    var playlists = ArrayList<Playlist>()

    playlists.add(Playlist("spotify:playlist:37i9dQZF1DX4dyzvuaRJ0n", "Mint Playlist",
        "spotify:image:ab67616d0000b273e49b1bcaa060156dd2019e17"))
    playlists.add(Playlist("spotify:album:79C6OXDHPGZtCb7ySUxyIV","Holiday Songs",
        "spotify:image:ab67616d0000b27356a8fbe72905057674ac41f7"))
    playlists.add(Playlist("spotify:playlist:37i9dQZF1DXcBWIGoYBM5M","Todays Hits",
        "spotify:image:ab67616d0000b27333b8541201f1ef38941024be"))
    playlists.add(Playlist("spotify:playlist:3Di88mvYplBtkDBIzGLiiM","EDM Hits",
        "spotify:image:ab67616d0000b273b6567be9f8b996a2b5f9b7fa"))
    playlists.add(Playlist("spotify:playlist:37i9dQZF1DWXRqgorJj26U","Rock Classics",
        "spotify:image:ab67616d0000b273fc4f17340773c6c3579fea0d"))
    playlists.add(Playlist("spotify:playlist:3Di88mvYplBtkDBIzGLiiM", "EDM Hits of 2020",
        "spotify:image:ab67616d0000b2735e90ff76fd49a23f7333de76"))
    playlists.add(Playlist("spotify:playlist:7IBJWKSiPeYvMqU3KPztbX", "Vibes For The Day",
        "spotify:image:ab67616d0000b2737aeaadf1d7f5b8167802af73"))
    playlists.add(Playlist("spotify:playlist:37i9dQZF1DXbcP8BbYEQaO", "Night Pop",
        "spotify:image:ab67616d0000b273ac36c62572cc8251f251f335"))
    playlists.add(Playlist("spotify:playlist:37i9dQZEVXbLRQDuF5jeBp", "US Top 50",
        "spotify:image:ab67616d0000b273c8ac560f803bba4902f8bbc5"))
    playlists.add(Playlist("spotify:playlist:0vvXsWCC9xrXsKd4FyS8kM", "Lofi Hip-Hop Beats To Study To",
        "spotify:image:ab67616d0000b2739219df1009403ac8f4d4f0b7"))
    playlists.add(Playlist("spotify:playlist:0vvXsWCC9xrXsKd4FyS8kM", "Lofi Hip-Hop Beats To Study To",
        "spotify:image:ab67616d0000b27397699e56b6a19f3d2703f32d"))
    return playlists
}