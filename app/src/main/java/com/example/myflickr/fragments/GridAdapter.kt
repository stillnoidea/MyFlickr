package com.example.myflickr.fragments

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.example.myflickr.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.grid_view.view.*




class GridAdapter(
    private val context: Context,
    private val imagesURLs:ArrayList<String>): BaseAdapter()
{

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view= LayoutInflater.from(context).inflate(R.layout.grid_view,parent,false)
        loadImage(imagesURLs[position],view)
//        view.grid_view_image.setImageResource(images[position])
        return view
    }

    override fun getItem(position: Int): Any? {
        return null
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return imagesURLs.size
    }
    private fun loadImage(URL:String, view:View){
        Picasso.get().isLoggingEnabled = true
        Picasso.get().load(URL)
            .into(object : com.squareup.picasso.Target {
                override fun onBitmapLoaded(bitmap: Bitmap, from: Picasso.LoadedFrom) {
                    view.grid_view_image!!.setImageBitmap(bitmap)
                }
                override fun onPrepareLoad(placeHolderDrawable: Drawable?) {
                    //not implemented bcs not needed
                }
                override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {
                    //not implemented bcs not needed
                }
            })
    }

}