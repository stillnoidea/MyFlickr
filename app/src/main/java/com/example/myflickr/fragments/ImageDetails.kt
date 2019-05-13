package com.example.myflickr.fragments

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.GestureDetector
import android.view.Menu
import android.view.MenuItem
import android.view.MotionEvent
import com.example.myflickr.Item
import com.example.myflickr.MainActivity
import com.example.myflickr.R
import kotlinx.android.synthetic.main.activity_image_details.*

class ImageDetails : AppCompatActivity(), GestureDetector.OnGestureListener,
    FragmentImage.OnFragmentInteractionListener,
    FragmentInfo.OnFragmentInteractionListener, FragmentSimilarImages.OnFragmentInteractionListener {
    var similarImages: ArrayList<String> = arrayListOf()
    var item: Item = Item()
    var myDetector: GestureDetector? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        item = intent.getSerializableExtra(MainActivity.CHOSEN_ITEM) as Item
        similarImages = intent.getStringArrayListExtra(MainActivity.SIMILAR_IMAGES)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_details)
        setSupportActionBar(findViewById(R.id.image_view_toolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        myDetector = GestureDetector(this, this)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.details_toolbar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            android.R.id.home -> {
                onBackPressed()
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
        return true
    }

    override fun onFragmentInteraction(uri: Uri) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        myDetector!!.onTouchEvent(event)
        return super.onTouchEvent(event)
    }

    override fun onFling(e1: MotionEvent?, e2: MotionEvent?, velocityX: Float, velocityY: Float): Boolean {
        if (e1!!.rawY > e2!!.rawY) {
            image_view_switcher.showNext()
        } else {
            image_view_switcher.showPrevious()
        }
        return false
    }

    override fun onShowPress(e: MotionEvent?) {}

    override fun onSingleTapUp(e: MotionEvent?): Boolean {
        return false
    }

    override fun onDown(e: MotionEvent?): Boolean {
        return false
    }

    override fun onScroll(e1: MotionEvent?, e2: MotionEvent?, distanceX: Float, distanceY: Float): Boolean {
        return false
    }

    override fun onLongPress(e: MotionEvent?) {

    }
}
