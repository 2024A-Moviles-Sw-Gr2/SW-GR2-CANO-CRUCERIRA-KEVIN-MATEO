package com.example.deber_02

import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract.Data
import android.util.Log
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Equipos : AppCompatActivity() {

    var id_jugador = 0

    val callbackFormularioEquipo=registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ){
        result ->
        if (result.resultCode == Activity.RESULT_OK) {
            if (result.data != null) {
                val listView = findViewById<ListView>(R.id.list_equipo)
                val adaptador = ArrayAdapter(
                    this,
                    android.R.layout.simple_list_item_1,
                    Database.tables!!.getEquipos()
                )
                listView.adapter = adaptador
                adaptador.notifyDataSetChanged()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.cl_equipos)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val jugador = intent.getStringExtra("materia")
        id_jugador = intent.getIntExtra("id", 0)

        if (jugador != null) {
            findViewById<TextView>(R.id.id_nombre_jugador).setText(jugador)
        }


        //Colocar datos en Lista
        val listView = findViewById<ListView>(R.id.list_equipo)
        val adaptador = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            Database.tables!!.getEquipos()
        )
        listView.adapter = adaptador
        adaptador.notifyDataSetChanged()

        //Uso de Botones
        val btnCrearEstudiante = findViewById<Button>(
            R.id.id_btn_crear_equipo
        )
        btnCrearEstudiante.setOnClickListener {
            crearEquipo()
        }
        registerForContextMenu(listView)
    }

    private fun crearEquipo() {
        val intentCrear = Intent(
            this,
            activity_beditar_equipo::class.java
        )

        intentCrear.putExtra("id_jugador", id_jugador)  // Usa intentCrear aquí
        callbackFormularioEquipo.launch(intentCrear)
    }

    var index = 1

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        //opciones
        menuInflater.inflate(R.menu.menu_equipo, menu)

        //opcion seleccionada
        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        index = info.position
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.id_mi_editar_equipo -> {
                val intentEditar = Intent(
                    this,
                    activity_beditar_equipo::class.java
                )
                val equipos = Database.tables!!.getEquipos()
                intentEditar.putExtra("equipo", equipos[index])
                callbackFormularioEquipo.launch(intentEditar)

                true
            }
            R.id.id_mi_eliminar_equipo -> {
                abrirDialogo(index)
                true
            }
            R.id.id_mi_ver_jugadores -> {
                val equipoSeleccionado = Database.tables!!.getJugadoresPorEquipo(index)

                val intent = Intent(this, activity_bjugadores::class.java)
                intent.putExtra("equipo", equipoSeleccionado)
                startActivity(intent)
                return true
            }
            R.id.id_mi_ver_ubicacionEstadio -> {
                val ubicacion = Database.tables!!.getEquipos()[index].ubicacionEstadio

                val intent = Intent(this, FMapActivity::class.java)
                intent.putExtra("ubicacio", ubicacion)
                startActivity(intent)

                true
            }
            else -> super.onContextItemSelected(item)
        }
    }

    private fun abrirDialogo(index: Int) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Desea Eliminar?")
        builder.setPositiveButton(
            "Aceptar",
            DialogInterface.OnClickListener { dialog, which ->
                Database.tables!!.eliminarEquipo(index + 1)
                val listView = findViewById<ListView>(R.id.list_equipo)
                val adaptador = ArrayAdapter(
                    this,
                    android.R.layout.simple_list_item_1,
                    Database.tables!!.getEquipos()
                )
                listView.adapter = adaptador
                adaptador.notifyDataSetChanged()
            }
        )
        builder.setNegativeButton("Cancelar", null)
        builder.create().show()
    }

}