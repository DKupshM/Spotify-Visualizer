package edu.ucsb.cs.cs184.spotify_visualizer.ui.home

import android.graphics.Color
import processing.core.PApplet
import kotlin.math.pow

class Sketch : PApplet() {
    var size = 100f
    var fCount = 0f
    var angle = 0f
    override fun settings(){
        fullScreen()
    }

    override fun setup(){
        background(Color.parseColor("#FF8A80"))
    }

    override fun draw(){
        background(Color.parseColor("#FF8A80"))
        fill(255)
        strokeWeight(4f)
        push()
        translate(width/2.toFloat(),height/2.toFloat())
        rotate(angle)
        angle+=0.01f
        var x:Float
        var y : Float
        val s = 150f
        stroke(255)
        var a = 200* sin((fCount))
        for(i in 0..750) {
            val t = map(i.toFloat(),0f,750f,0f,2* PI)
            x = cos(9*t) - (cos(a*t)).pow(3)
            y = sin(a*t) - (sin(9*t)).pow(3)
            point(s*x,y*s)
        }
        pop()
        fCount+=0.0003f
        //troke(Color.GREEN)
        //rect(0f,0f,size,size)
        //size+=5
    }
}