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

    playlists.add(Playlist("spotify:playlist:37i9dQZF1DX4dyzvuaRJ0n",
        "Mint Playlist",
        "spotify:image:ab67616d0000b273e49b1bcaa060156dd2019e17"))
    playlists.add(Playlist("spotify:album:79C6OXDHPGZtCb7ySUxyIV","Holiday Songs",
        "spotify:image:ab67616d0000b27356a8fbe72905057674ac41f7"))
    playlists.add(Playlist("spotify:playlist:37i9dQZF1DXcBWIGoYBM5M","Todays Hits","spotify:image:ab67616d0000b27333b8541201f1ef38941024be"))
    playlists.add(Playlist("spotify:playlist:3Di88mvYplBtkDBIzGLiiM","EDM Hits","spotify:image:ab67616d0000b273b6567be9f8b996a2b5f9b7fa"))
    playlists.add(Playlist("spotify:playlist:37i9dQZF1DWXRqgorJj26U","Rock Classics","spotify:image:ab67616d0000b273fc4f17340773c6c3579fea0d"))

    return playlists
}