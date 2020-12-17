package edu.ucsb.cs.cs184.spotify_visualizer.ui.dashboard

class Song {

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