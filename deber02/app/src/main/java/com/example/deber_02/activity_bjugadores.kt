package com.example.deber_02

import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class activity_bjugadores : AppCompatActivity() {

    private var jugadores = arrayListOf<JugadorEntity>()
    private var idEquipo = 1
    private var index = -1

    private val callbackFormularioJugador = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            result.data?.let {
                val jugadorModificado = it.getParcelableExtra<JugadorEntity>("jugadorModificado")
                val jugadorNuevo = it.getParcelableExtra<JugadorEntity>("jugadorNuevo")

                jugadorModificado?.let { jugador ->
                    jugadores[index] = jugador
                    Memoria.actualizarJugador(jugador)
                } ?: jugadorNuevo?.let { nuevoJugador ->
                    jugadores.add(nuevoJugador)
                    Memoria.jugadores.add(nuevoJugador)
                    Memoria.agregarJugadorEquipo(idEquipo, nuevoJugador)
                }

                actualizarListaJugadores()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bjugadores)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.cl_jugadores)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val equipo = intent.getParcelableExtra<EquipoEntity>("equipo")
        Log.d("JugadoresActivity", "Equipo recibido: $equipo")

        equipo?.let {
            findViewById<TextView>(R.id.id_equipo_nom).text = it.nombre
            val jugadoresEquipo = it.jugadores ?: mutableListOf()
            jugadores = Memoria.jugadores.filter { jugador -> jugadoresEquipo.contains(jugador.id) } as ArrayList<JugadorEntity>
            idEquipo = it.id
        } ?: run {
            Log.d("JugadoresActivity", "No se recibió ningún equipo.")
        }

        actualizarListaJugadores()

        findViewById<Button>(R.id.id_btn_crear_jugador).setOnClickListener {
            crearJugador()
        }

        findViewById<Button>(R.id.btn_back).setOnClickListener {
            startActivity(Intent(this, Equipos::class.java))
        }

        registerForContextMenu(findViewById(R.id.list_jugadores))
    }

    private fun actualizarListaJugadores() {
        val listView = findViewById<ListView>(R.id.list_jugadores)
        val adaptador = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            jugadores
        )
        listView.adapter = adaptador
        adaptador.notifyDataSetChanged()
    }

    private fun crearJugador() {
        val intentCrear = Intent(this, activity_deditar_jugadores::class.java)
        callbackFormularioJugador.launch(intentCrear)
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menuInflater.inflate(R.menu.menu_jugador, menu)
        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        index = info.position
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.id_mi_editar_jugador -> {
                val intentEditar = Intent(this, activity_deditar_jugadores::class.java)
                intentEditar.putExtra("jugador", jugadores[index])
                callbackFormularioJugador.launch(intentEditar)
                true
            }
            R.id.id_mi_eliminar_jugador -> {
                abrirDialogoEliminar(jugadores[index])
                true
            }
            else -> super.onContextItemSelected(item)
        }
    }

    private fun abrirDialogoEliminar(jugador: JugadorEntity) {
        AlertDialog.Builder(this)
            .setTitle("¿Desea eliminar al jugador?")
            .setPositiveButton("Aceptar") { _, _ ->
                eliminarJugador(jugador)
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }

    private fun eliminarJugador(jugador: JugadorEntity) {
        jugadores.remove(jugador)
        Memoria.jugadores.remove(jugador)
        Memoria.equipos.find { it.id == idEquipo }?.jugadores?.remove(jugador.id)
        actualizarListaJugadores()
    }
}
