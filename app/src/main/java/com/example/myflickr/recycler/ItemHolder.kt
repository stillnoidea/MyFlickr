package com.example.myflickr.recycler

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myflickr.Item
import com.example.myflickr.R
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import com.squareup.picasso.Picasso


class ItemHolder(inflater: LayoutInflater, parent: ViewGroup) :
    RecyclerView.ViewHolder(inflater.inflate(R.layout.recycler_view_item, parent, false)) {

    private var date: TextView? = null
    private var name: TextView? = null
    private var picture: ImageView? = null
    private var tags: TextView? = null
    private var _item: Item? = null

    init {
        date = itemView.findViewById(R.id.main_text_date)
        name = itemView.findViewById(R.id.main_text_name)
        tags = itemView.findViewById(R.id.main_text_tags)
        picture = itemView.findViewById(R.id.main_image_primary)
//        itemView.setOnLongClickListener(this)
    }

    inner class PicassoTarget : com.squareup.picasso.Target {
        override fun onBitmapLoaded(bitmap: Bitmap, from: Picasso.LoadedFrom) {
            picture!!.setImageBitmap(bitmap)
            tagImage(bitmap, _item!!)
        }
        override fun onPrepareLoad(placeHolderDrawable: Drawable?) {
            //not implemented bcs not needed
        }
        override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {
            //not implemented bcs not needed
        }
    }

     private var target: PicassoTarget = PicassoTarget()

    fun bind(item: Item) {
        _item = item
        name!!.text = item.getName()
        Picasso.get().isLoggingEnabled = true
        Picasso.get().load(item.getUrl()).into(target)
        date!!.text = item.getDateToDisplay()
    }


    private fun tagImage(image: Bitmap, item: Item) {
        image.apply {
            val vision = FirebaseVisionImage.fromBitmap(this)
            val labeler = FirebaseVision.getInstance().onDeviceImageLabeler
            labeler.processImage(vision)
                .addOnSuccessListener {
                    val list = it.map { it.text }
                    Log.d("LAB", list.joinToString(" "))
                    setTagsToDisplay(list, item)
                }
                .addOnFailureListener {
                    Log.wtf("LAB", it.message)
                }
        }
    }

    private fun setTagsToDisplay(values: List<String>, item: Item) {
        val list = values.take(3)
        tags!!.text = list.joinToString(", ")
        item.setTags(values.toMutableList())
    }
}