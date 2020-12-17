package edu.ucsb.cs.cs184.spotify_visualizer.ui.home

import android.graphics.Color
import processing.core.PApplet

class Sketch : PApplet() {
    var size = 100f
    var fCount = 0
    var angle = 0f
    var numVertices = 20
    val spacing = 360f/numVertices
    var fOff = 0f
    var speed = 0.4f

    override fun settings(){
        //fullScreen()
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

        val m1 = 0.55*size*sin(fOff/7f)
        val m2 = 0.85*size*cos(fOff/12)

        val m3 = 1.7f*sin(fOff/8)
        val m4 = 2*cos(fOff/9)

        val layers = 5

        for(j in 1..layers) {
            val R = map(j.toFloat(),1f,layers.toFloat(),255f,30f)
            val A = map(j.toFloat(),1f,layers.toFloat(),200f,30f)
            val G = map(sin(fOff/8),-1f,1f,0f,255f)
            val B = map(sin(fOff/12),-1f,1f,0f,255f)

            val layerSize = map(j.toFloat(),1f,layers.toFloat(),1f,5f)
            fill(R,G,B,A)
            beginShape()

            for (i in 0..numVertices) {
                val angle = i * spacing

                var x : Float
                var y : Float
                if(j%2==0){
                    x = (cos(radians(angle)) * (layerSize*size - m1)).toFloat()
                    y = (sin(radians(angle)) * (layerSize*size + m2)).toFloat()
                } else {
                    x = (cos(radians(angle)) * (layerSize*size - m2)).toFloat()
                    y = (sin(radians(angle)) * (layerSize*size + m1)).toFloat()
                }


                if (i == 0) {
                    vertex(x.toFloat(), y.toFloat())
                } else {
                    val cAngle = angle - spacing / 2

                    val cX = cos(radians(cAngle)) * 180
                    val cY = sin(radians(cAngle)) * 180

                    if(j%2==0){
                        quadraticVertex(m3 * cX, (m4 * cY), x, y)
                    } else {
                        quadraticVertex(m4 * cX, (m3 * cY), x, y)
                    }

                }

            }

            endShape()
        }

    }
}