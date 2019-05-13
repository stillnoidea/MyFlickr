package com.example.myflickr

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myflickr.fragments.ImageDetails
import com.example.myflickr.recycler.ItemAdapter

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: ItemAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.main_toolbar))

        viewManager = LinearLayoutManager(this)
//        viewAdapter = ItemAdapter(mutableListOf())
        viewAdapter = ItemAdapter(getDefaultItemList())

        recyclerView = findViewById<RecyclerView>(R.id.main_recycler_view).apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }

        swipeHelper.attachToRecyclerView(recyclerView)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == 2404) {
            if (data != null) {
                val items = data.getSerializableExtra(INTENT_NAME_ITEMS_TO_MAIN) as Array<Item>
                viewAdapter.replaceList(items.toMutableList())
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            R.id.toolbar_action_add -> {
                val intent = Intent(this, AddActivity::class.java)
                intent.putExtra(INTENT_NAME_ITEMS_TO_ADD, getItemList().toTypedArray())
                startActivityForResult(intent, 2404)
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
        return true
    }

    private fun getSimilarImages(item: Item): ArrayList<String> {
        val list = getItemList()
        val result: ArrayList<String> = arrayListOf()
        for (i in list) {
            if (item.getUrl() != i.getUrl() && item.tagExists(i.getTags()) && result.size < 6) {
                result.add(i.getUrl())
            }
        }
        return result
    }

    private fun getItemList():MutableList<Item>{
        return viewAdapter.getList()
    }

    private fun getDefaultItemList(): MutableList<Item> {
        val list = mutableListOf<Item>()
        list.add(Item("Cat1", "https://http.cat/418"))
        list.add(Item("Cat2", "https://http.cat/404"))
        list.add(Item("Cat3", "https://http.cat/401"))
        list.add(Item("Cat4", "https://http.cat/403"))
        list.add(Item("Cat5", "https://http.cat/405"))
        list.add(Item("Cat6", "https://http.cat/204"))
        list.add(Item("Cat7", "https://http.cat/200"))
        list.add(Item("Cat8", "https://http.cat/202"))
        list.add(Item("Cat9", "https://http.cat/500"))
        list.add(Item("Cat10", "https://http.cat/502"))
        return list
    }

    private val swipeHelper = ItemTouchHelper(
        object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                if (direction == ItemTouchHelper.LEFT) {
                    viewAdapter.removeAt(viewHolder.adapterPosition)
                } else {
                    val item = getItemList()[viewHolder.adapterPosition]
                    val intent =Intent(applicationContext, ImageDetails::class.java)
                    intent.putExtra(CHOSEN_ITEM, item)
                    val tagList = getSimilarImages(item)
                    intent.putExtra(SIMILAR_IMAGES, tagList)
                    startActivity(intent)

                    viewAdapter.notifyItemChanged(viewHolder.adapterPosition)
                }
            }
        }
    )

    companion object {
        const val INTENT_NAME_ITEMS_TO_ADD = "items_array"
        const val INTENT_NAME_ITEMS_TO_MAIN = "items_arrays"
        const val CHOSEN_ITEM = "chosen item"
        const val SIMILAR_IMAGES = "similar images"
        const val PICK_PHOTO = 1800
    }
}
