package com.recyclerit.sureshkansujiya.hindicalendar

import android.graphics.Color
import android.support.v4.view.PagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import java.util.*
import android.view.ScaleGestureDetector
import kotlinx.android.synthetic.main.activity_splash.*
import uk.co.senab.photoview.PhotoViewAttacher


class CustomPageAdaptor(private val list: List<Int>) : PagerAdapter() {

    var attacher: PhotoViewAttacher? = null

    init {}

    override fun isViewFromObject(v: View, `object`: Any): Boolean {
        // Return the current view
        return v === `object` as View
    }


    override fun getCount(): Int {
        // Count the items and return it
        return list.size
    }


    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        // Get the view from pager page layout
        val view = LayoutInflater.from(container?.context)
                .inflate(R.layout.fragment_main,container,false)

        // Get the widgets reference from layout
        val image: ImageView = view.findViewById(R.id.imageView)
        image.setImageResource(list.get(position))
        attacher = PhotoViewAttacher(image)
        attacher?.update()
        // Add the view to the parent
        container?.addView(view)

        // Return the view
        return view
    }


    override fun destroyItem(parent: ViewGroup, position: Int, `object`: Any) {
        // Remove the view from view group specified position
        parent.removeView(`object` as View)
        attacher = null
    }
}