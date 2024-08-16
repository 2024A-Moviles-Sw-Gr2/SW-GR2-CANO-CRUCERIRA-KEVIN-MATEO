package com.example.deber_02

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class activity_deditar_jugadores : AppCompatActivity() {

    var id: Int = -1
    var equipoId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_deditar_jugadores)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Configurar el Spinner para seleccionar el equipo
        val spinnerEquipos: Spinner = findViewById(R.id.spiner_equipos)
        val equipos = Database.tables?.getEquipos() ?: listOf()
        val equiposAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, equipos.map { it.nombre })
        equiposAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerEquipos.adapter = equiposAdapter

        val jugador = intent.getParcelableExtra<JugadorEntity>("jugador")

        if (jugador != null) {
            findViewById<EditText>(R.id.input_nombre_jugador).setText(jugador.nombre)
            findViewById<EditText>(R.id.input_edad_jugador).setText(jugador.edad.toString())
            findViewById<EditText>(R.id.input_altura_jugador).setText(jugador.altura.toString())
            id = jugador.id
            equipoId = jugador.equipoId
            // Seleccionar el equipo actual del jugador en el Spinner
            val equipoIndex = equipos.indexOfFirst { it.id == equipoId }
            if (equipoIndex != -1) {
                spinnerEquipos.setSelection(equipoIndex)
            }
        }

        val guardarBtn = findViewById<Button>(R.id.btn_save_mat)
        guardarBtn.setOnClickListener {
            // Obtener el equipo seleccionado
            val equipoSeleccionado = equipos[spinnerEquipos.selectedItemPosition]
            equipoId = equipoSeleccionado.id

            if (id != -1) {
                responseEditar()
            } else {
                responseCrear()
            }
        }
    }

    private fun responseEditar() {
        if (!validarDatos()) return

        val response = Intent()
        try {
            Database.tables?.actualizarJugador(
                id,
                findViewById<EditText>(R.id.input_nombre_jugador).text.toString(),
                findViewById<EditText>(R.id.input_edad_jugador).text.toString().toInt(),
                findViewById<EditText>(R.id.input_altura_jugador).text.toString().toDouble(),
                equipoId
            )
            setResult(RESULT_OK, response)
            finish()
        } catch (e: Exception) {
            Toast.makeText(this, "Error al actualizar el jugador: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }

    private fun responseCrear() {
        val response = Intent()

        Database.tables?.crearJugador(
            findViewById<EditText>(R.id.input_nombre_jugador).text.toString(),
            findViewById<EditText>(R.id.input_edad_jugador).text.toString().toInt(),
            findViewById<EditText>(R.id.input_altura_jugador).text.toString().toDouble(),
            equipoId
        )

        setResult(RESULT_OK, response)
        finish()
    }
    private fun validarDatos(): Boolean {
        val nombre = findViewById<EditText>(R.id.input_nombre_jugador).text.toString()
        val edad = findViewById<EditText>(R.id.input_edad_jugador).text.toString()
        val altura = findViewById<EditText>(R.id.input_altura_jugador).text.toString()

        if (nombre.isEmpty() || edad.isEmpty() || altura.isEmpty()) {
            Toast.makeText(this, "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show()
            return false
        }

        if (edad.toIntOrNull() == null || edad.toInt() <= 0) {
            Toast.makeText(this, "La edad debe ser un número positivo", Toast.LENGTH_SHORT).show()
            return false
        }

        if (altura.toDoubleOrNull() == null || altura.toDouble() <= 0) {
            Toast.makeText(this, "La altura debe ser un número positivo", Toast.LENGTH_SHORT).show()
            return false
        }

        return true
    }
}