package com.example.myflickr

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myflickr.MainActivity.Companion.INTENT_NAME_ITEMS_TO_ADD
import com.example.myflickr.MainActivity.Companion.INTENT_NAME_ITEMS_TO_MAIN
import kotlinx.android.synthetic.main.activity_add.*
import java.text.SimpleDateFormat
import java.util.*


class AddActivity : AppCompatActivity() {
    var cal = Calendar.getInstance()
    var items: Array<Item> = arrayOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)
        setSupportActionBar(findViewById(R.id.add_toolbar))

        items = intent.getSerializableExtra(INTENT_NAME_ITEMS_TO_ADD) as Array<Item>
        // Get a support ActionBar corresponding to this toolbar and enable the Up button
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val dateSetListener = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            cal.set(Calendar.YEAR, year)
            cal.set(Calendar.MONTH, monthOfYear)
            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            updateDateInView()
        }

        add_button_select_date.setOnClickListener {
            DatePickerDialog(
                this@AddActivity,
                dateSetListener,                   // set DatePickerDialog to point to today's date when it loads up
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            R.id.toolbar_action_add -> {
                if (isValid()) {
                    saveItem()
                    val intent = Intent()
                    intent.putExtra(INTENT_NAME_ITEMS_TO_MAIN, items)
                    setResult(Activity.RESULT_OK, intent)
                    onBackPressed()
                }

            }
            android.R.id.home -> {
                val intent = Intent()
                intent.putExtra(INTENT_NAME_ITEMS_TO_MAIN, items)
                setResult(Activity.RESULT_OK, intent)
                onBackPressed()
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
        return true
    }

    private fun updateDateInView() {
        val myFormat = getString(R.string.date_format) // mention the format you need
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        add_text_date_value.text = sdf.format(getDate())
    }

    private fun saveItem() {
        val item = Item()
        item.setDate(getDate())
        item.setName(getName())
        item.setUrl(getURL())
        items = items.plus(item)
    }


    private fun getName(): String {
        return add_edit_text_name.text.toString()
    }


    private fun getURL(): String {
        return add_edit_text_url.text.toString()
    }

    private fun getDate(): Date {
        return cal.time
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun isValid(): Boolean {
        if (getName() == "" || getURL() == "" || add_text_date_value.text == "") {
            Toast.makeText(this, "Invalid input", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }


}
