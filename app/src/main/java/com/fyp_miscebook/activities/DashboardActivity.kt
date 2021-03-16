package com.fyp_miscebook.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.fyp_miscebook.ExampleAdapter
import com.fyp_miscebook.ExampleItem
import com.fyp_miscebook.R
import kotlinx.android.synthetic.main.activity_dashboard_.*

class DashboardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard_)
        setSupportActionBar(findViewById(R.id.my_toolbar))

        val exampleList = generateDummyList(500)
        RecyclerView.adapter = ExampleAdapter(exampleList)
        RecyclerView.layoutManager = LinearLayoutManager(this)
        RecyclerView.setHasFixedSize(true)
    }

    private fun generateDummyList(size: Int): List<ExampleItem> {
        val list = ArrayList<ExampleItem>()

        for (i in 0 until size) {
            val drawable = when (i % 3) {
                0 -> R.mipmap.ic_launcher
                1 -> R.drawable.ic_menu
                else -> R.drawable.ic_back_home
            }
            val item = ExampleItem(drawable, "Item $i", "Line 2")
            list += item
        }
        return list
    }
}