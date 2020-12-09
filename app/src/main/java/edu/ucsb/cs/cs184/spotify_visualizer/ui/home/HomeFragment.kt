package edu.ucsb.cs.cs184.spotify_visualizer.ui.home

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import edu.ucsb.cs.cs184.spotify_visualizer.R
import processing.android.PFragment


class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel

    val myCanvas = Sketch()
    private lateinit var canvas_container : FrameLayout

    lateinit var listener : FragmentActivity


    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is Activity) {
            listener = (context as FragmentActivity)!!
        }
    }


    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        val textView: TextView = root.findViewById(R.id.text_home)
        homeViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        canvas_container = root.findViewById(R.id.canvas_container)
        val myFragment = PFragment(myCanvas)
//        val canvas_container = root.findViewById<FrameLayout>(R.id.canvas_container)
//
        myFragment.setView(canvas_container,listener)

        return root
    }


}