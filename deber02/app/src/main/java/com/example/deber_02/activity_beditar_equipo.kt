package com.example.deber_02

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class activity_beditar_equipo : AppCompatActivity() {

    var id : Int = 1
    var jugadores : MutableList<Int> ? = arrayListOf<Int>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_beditar_equipo)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        var equipo = intent.getParcelableExtra<EquipoEntity>("equipo")

        if(equipo != null) {
            findViewById<EditText>(R.id.input_nombre_equipo).setText(equipo.nombre)
            findViewById<EditText>(R.id.input_fecha_creacion).setText(equipo.fechaCreacion)
            findViewById<EditText>(R.id.input_ciudad).setText(equipo.ciudad)
            id = equipo.id
            jugadores = equipo.jugadores
        }

        val guardarBoton = findViewById<Button>(R.id.btn_save)
        guardarBoton.setOnClickListener{
            if(equipo != null) {
                responseEditar()
            }else {
                responseCrear()
            }
        }
    }

    private fun responseEditar(){
        val response = Intent()

        val nombre = findViewById<EditText>(R.id.input_nombre_equipo).text.toString()
        val fechaCreacion = findViewById<EditText>(R.id.input_fecha_creacion).text.toString()
        val ciudad = findViewById<EditText>(R.id.input_ciudad).text.toString()

        val equipoModificado = EquipoEntity(id, nombre, fechaCreacion, ciudad, jugadores)

        response.putExtra("EquipoModificado", equipoModificado)

        setResult(RESULT_OK, response)
        finish()
    }
    private fun responseCrear(){
        val response = Intent()

        val nombre = findViewById<EditText>(R.id.input_nombre_equipo).text.toString()
        val fechaCreacion = findViewById<EditText>(R.id.input_fecha_creacion).text.toString()
        val ciudad = findViewById<EditText>(R.id.input_ciudad).text.toString()

        val equipoModificado = EquipoEntity(Memoria.idNuevoEquipo(), nombre, fechaCreacion, ciudad, jugadores)

        response.putExtra("EquipoModificado", equipoModificado)

        setResult(RESULT_OK, response)
        finish()
    }
}