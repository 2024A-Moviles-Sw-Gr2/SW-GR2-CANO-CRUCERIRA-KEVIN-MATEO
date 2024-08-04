package com.example.deber03

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class SearchActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var horizontalItemAdapter: HorizontalItemAdapter
    private val items = listOf(
        R.drawable.descarga6,
        R.drawable.descarga8,
        R.drawable.descarga13,
        R.drawable.descarga4,
        R.drawable.descarga2
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        recyclerView = findViewById(R.id.horizontal_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        horizontalItemAdapter = HorizontalItemAdapter(items)
        recyclerView.adapter = horizontalItemAdapter
    }
}
