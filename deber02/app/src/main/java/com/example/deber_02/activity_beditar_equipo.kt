package com.example.deber_02

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class activity_beditar_equipo : AppCompatActivity() {

    var id: Int = 1
    var ubicacionSpinner = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_beditar_equipo)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Obtener el Spinner
        val spinner: Spinner = findViewById(R.id.sp_ubiaciones)
        ArrayAdapter.createFromResource(
            this,
            R.array.ubicaciones,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }

        // Configurar el listener para cuando se seleccione un elemento en el Spinner
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                ubicacionSpinner = parent.getItemAtPosition(position).toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // No hace nada
            }
        }

        // Obtener los datos del equipo desde el Intent
        val equipo = intent.getParcelableExtra<EquipoEntity>("equipo")

        if (equipo != null) {
            findViewById<EditText>(R.id.input_nombre_equipo).setText(equipo.nombre)
            findViewById<EditText>(R.id.input_fecha_creacion).setText(equipo.fechaCreacion)
            findViewById<EditText>(R.id.input_ciudad).setText(equipo.ciudad)
            id = equipo.id

            // Configurar el Spinner para que seleccione la ubicación actual del equipo
            val ubicacionesArray = resources.getStringArray(R.array.ubicaciones)
            val posicionInicial = ubicacionesArray.indexOf(equipo.ubicacionEstadio)
            spinner.setSelection(posicionInicial)
            ubicacionSpinner = equipo.ubicacionEstadio
        }


    }

    private fun responseEditar() {
        val response = Intent()

        Database.tables?.actualizarEquipo(
            id,
            findViewById<EditText>(R.id.input_nombre_equipo).text.toString(),
            findViewById<EditText>(R.id.input_fecha_creacion).text.toString(),
            findViewById<EditText>(R.id.input_ciudad).text.toString(),
            ubicacionSpinner
        )

        setResult(RESULT_OK, response)
        finish()
    }

    private fun responseCrear() {
        val response = Intent()

        Database.tables?.crearEquipo(
            findViewById<EditText>(R.id.input_nombre_equipo).text.toString(),
            findViewById<EditText>(R.id.input_fecha_creacion).text.toString(),
            findViewById<EditText>(R.id.input_ciudad).text.toString(),
            ubicacionSpinner
        )

        setResult(RESULT_OK, response)
        finish()
    }
}
