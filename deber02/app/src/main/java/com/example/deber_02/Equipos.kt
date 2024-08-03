package com.example.deber_02

import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.Adapter
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Equipos : AppCompatActivity() {

    val callbackFormularioEquipo=registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ){
        result ->
        if (result.resultCode == Activity.RESULT_OK) {
            if (result.data != null) {
                val equipoModificado = result.data!!.getParcelableExtra<EquipoEntity>("equipoModificado")
                val equipoNuevo = result.data!!.getParcelableExtra<EquipoEntity>("eequipoNuevo")

                if (equipoModificado != null) {
                    Memoria.equipos.removeAt(index)
                    Memoria.equipos.add(index, equipoModificado)
                }else if(equipoNuevo != null ){
                    Memoria.equipos.add(equipoNuevo)
                }

                val listView = findViewById<ListView>(R.id.list_equipo)
                val adaptador = ArrayAdapter(
                    this,
                    android.R.layout.simple_list_item_1,
                    Memoria.equipos
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
        Log.d("EquiposActivity", "Equipos iniciales: ${Memoria.equipos}")
        //colocar daots en la lista
        val listView = findViewById<ListView>(R.id.list_equipo)
        val adaptador = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            Memoria.equipos
        )
        listView.adapter = adaptador
        adaptador.notifyDataSetChanged()

        //Botones
        val botonCrearEquipo = findViewById<Button>(
            R.id.id_btn_crear_equipo
        )
        botonCrearEquipo.setOnClickListener{
            crearEquipo()
        }
        registerForContextMenu(listView)
    }

    private fun crearEquipo(){
        val intentCrear = Intent(
            this,
            activity_beditar_equipo::class.java
        )

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
        return when (item.itemId){
            R.id.id_mi_editar_equipo -> {
                val intentEditar = Intent(
                    this,
                    activity_beditar_equipo::class.java
                )
                intentEditar.putExtra("equipo", Memoria.equipos.get(index))
                callbackFormularioEquipo.launch(intentEditar)

                return true
            }
            R.id.id_mi_eliminar_equipo->{

                val listView = findViewById<ListView>(R.id.list_equipo)
                val adaptador = ArrayAdapter(
                    this,
                    android.R.layout.simple_list_item_1,
                    Memoria.equipos
                )
                listView.adapter = adaptador
                abrirDialogo(index, adaptador)
                return true
            }
            R.id.id_mi_ver_jugadores->{
                val equipoSeleccionado = Memoria.equipos.get(index)
                Log.d("EquiposActivity", "Equipo seleccionado: $equipoSeleccionado")
                Log.d("EquiposActivity", "Jugadores en el equipo: ${equipoSeleccionado.jugadores}")

                val intent = Intent(this, activity_bjugadores::class.java)
                intent.putExtra("equipo", equipoSeleccionado)
                startActivity(intent)
                return true
            }
            else -> super.onContextItemSelected(item)
        }
    }

    private fun abrirDialogo(index:Int, adapter: ArrayAdapter<EquipoEntity>){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Desea Eliminar?")
        builder.setPositiveButton(
            "Aceptar",
            DialogInterface.OnClickListener{
                    dialog, which ->
                Memoria.equipos.removeAt(index)
                adapter.notifyDataSetChanged()
            }
        )
        builder.setNegativeButton("Cancelar", null)
        builder.create().show()
    }
}