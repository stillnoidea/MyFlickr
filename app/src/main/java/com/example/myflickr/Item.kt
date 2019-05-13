package com.example.myflickr

import android.content.Intent
import android.graphics.Bitmap
import android.util.Log
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import java.io.Serializable
import java.text.SimpleDateFormat
import java.util.*

class Item() : Serializable {
    private var name: String = ""
    private var date: Date = Calendar.getInstance().time
    private var url: String = ""
    private var tags: MutableList<String> = mutableListOf()

    constructor(name: String, url: String) : this() {
        this.name = name
        this.url = url
    }

    fun getName(): String {
        return name
    }

    fun getDate(): Date {
        return date
    }

    fun getUrl(): String {
        return url
    }

    fun getTags(): MutableList<String> {
        return tags
    }

    fun getTagsToDisplay(): MutableList<String> {
        return tags.take(3).toMutableList()
    }

    fun setName(_name: String) {
        name = _name
    }

    fun setDate(_date: Date) {
        date = _date
    }

    fun setUrl(_url: String) {
        url = _url
    }

    fun setTags(list: MutableList<String>) {
        tags = list
    }

    fun getDateToDisplay(): String {
        val myFormat = "dd/MM/yyyy" // mention the format you need
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        return sdf.format(getDate()).toString()
    }

    fun generateTags(bitmap: Bitmap) {
        bitmap.apply {
            val vision = FirebaseVisionImage.fromBitmap(this)
            val labeler = FirebaseVision.getInstance().onDeviceImageLabeler
            labeler.processImage(vision)
                .addOnSuccessListener {
                    val list = it.map { it.text }
                    Log.d("LAB", list.joinToString(" "))
                    setTags(list.toMutableList())
                }
                .addOnFailureListener {
                    Log.wtf("LAB", it.message)
                }
        }
    }

    fun tagExists(otherTags: MutableList<String>): Boolean {
        for (t in tags) {
            if (otherTags.contains(t)) {
                return true
            }
        }
        return false
    }


}