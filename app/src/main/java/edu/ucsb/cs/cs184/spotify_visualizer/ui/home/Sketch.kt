package edu.ucsb.cs.cs184.spotify_visualizer.ui.home

import android.graphics.Color
import processing.core.PApplet
import kotlin.math.pow

class Sketch : PApplet() {
    var size = 100f
    var fCount = 0
    var angle = 0f
    var numVertices = 20
    val spacing = 360f/numVertices
    var fOff = 0f
    var speed = 0.1f

    override fun settings(){
        fullScreen()
    }

    override fun setup(){
        background(Color.parseColor("#FF8A80"))
        noStroke()
        size = (0.15*min(width,height)).toFloat()
    }

    override fun draw(){
        fCount += 1
        background(0)
        translate(width/2f,height/2f)
        fill(120f,30f,200f,100f)


//        val beat = fCount%30
//        if(beat in 0..10){
//            speed = 0.2f
//        } else {
//            speed = 0.03f
//        }


        fOff+=speed

        val m1 = 75*cos(fOff/1.5f)
        val m2 = 75*cos(fOff/4)

        val m3 = sin(fOff/3)
        val m4 = 2*cos(fOff)

        val layers = 10

        for(j in 1..layers) {
            val R = map(j.toFloat(),1f,layers.toFloat(),30f,255f)
            val A = map(j.toFloat(),1f,layers.toFloat(),200f,30f)
            val G = map(sin(fOff/2),-1f,1f,0f,255f)
            val B = map(sin(fOff/4),-1f,1f,0f,255f)

            val layerSize = map(j.toFloat(),1f,layers.toFloat(),1f,5f)
            fill(R,G,B,A)
            beginShape()

            for (i in 0..numVertices) {
                val angle = i * spacing

                var x = cos(radians(angle)) * (layerSize*size - m1)
                var y = sin(radians(angle)) * (layerSize*size + m2)

                if (i == 0) {
                    vertex(x, y)
                } else {
                    val cAngle = angle - spacing / 2

                    val cX = cos(radians(cAngle)) * 180
                    val cY = sin(radians(cAngle)) * 180

                    quadraticVertex(m3 * cX, (m4 * cY).toFloat(), x, y)
                }

            }

            endShape()
        }

    }
}