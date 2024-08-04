package com.example.deber03

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var mainRecyclerView: RecyclerView
    private lateinit var mainItemAdapter: ItemAdapter

    private val items = listOf(
        Item(R.drawable.imagenej, R.drawable.user, "juansdw21"),
        Item(R.drawable.descarga2, R.drawable.user, "kevinmateo"),
        Item(R.drawable.descarga3, R.drawable.user, "dsjw333"),
        Item(R.drawable.descarga4, R.drawable.user, "sadjj53d"),
        Item(R.drawable.descarga5, R.drawable.user, "anival"),
        Item(R.drawable.descarga6, R.drawable.user, "imagenespro"),
        Item(R.drawable.descarga7, R.drawable.user, "Elrey"),
        Item(R.drawable.descarga8, R.drawable.user, "IMAGENES"),
        Item(R.drawable.descarga9, R.drawable.user, "Pintawesome"),
        Item(R.drawable.descarga10, R.drawable.user, "beajtsa"),
        Item(R.drawable.descarga11, R.drawable.user, "123fasd"),
        Item(R.drawable.descarga12, R.drawable.user, "Xxxsadwe"),
        Item(R.drawable.descarga13, R.drawable.user, "eqqwe"),
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Configurar RecyclerView
        mainRecyclerView = findViewById(R.id.list_recycler_view)
        mainRecyclerView.layoutManager = GridLayoutManager(this, 2)
        mainItemAdapter = ItemAdapter(items)
        mainRecyclerView.adapter = mainItemAdapter

        // Configurar la navegacion del boton
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_search -> {
                    // Iniciar SearchActivity
                    startActivity(Intent(this, SearchActivity::class.java))
                    true
                }
                else -> false
            }
        }
    }
}
