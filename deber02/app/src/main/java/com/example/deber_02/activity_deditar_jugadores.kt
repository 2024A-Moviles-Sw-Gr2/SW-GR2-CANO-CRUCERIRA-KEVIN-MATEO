package com.example.deber_02

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class activity_deditar_jugadores : AppCompatActivity() {

    var id: Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_deditar_jugadores)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val jugador = intent.getParcelableExtra<JugadorEntity>("jugador")

        if (jugador != null) {
            findViewById<EditText>(R.id.input_nombre_jugador).setText(jugador.nombre)
            findViewById<EditText>(R.id.input_edad_jugador).setText(jugador.edad.toString())
            findViewById<EditText>(R.id.input_altura_jugador).setText(jugador.altura.toString())
            id = jugador.id
        }

        val guardarBtn = findViewById<Button>(R.id.btn_save_mat)
        guardarBtn.setOnClickListener {
            if (jugador != null) {
                responseEditar()
            } else {
                responseCrear()
            }
        }
    }

    private fun responseEditar() {
        val response = Intent()
        val nombre = findViewById<EditText>(R.id.input_nombre_jugador).text.toString()
        val edadStr = findViewById<EditText>(R.id.input_edad_jugador).text.toString()
        val alturaStr = findViewById<EditText>(R.id.input_altura_jugador).text.toString()

        if (nombre.isNotEmpty() && edadStr.isNotEmpty() && alturaStr.isNotEmpty()) {
            val edad = edadStr.toIntOrNull() ?: 0
            val altura = alturaStr.toDoubleOrNull() ?: 0.0

            val jugadorModificado = JugadorEntity(id, nombre, edad, altura)
            Memoria.actualizarJugador(jugadorModificado)

            response.putExtra("jugadorModificado", jugadorModificado)
            setResult(RESULT_OK, response)
            finish()
        } else {
            // Mostrar mensaje de error o validación
        }
    }

    private fun responseCrear() {
        val response = Intent()

        val nombre = findViewById<EditText>(R.id.input_nombre_jugador).text.toString()
        val edadStr = findViewById<EditText>(R.id.input_edad_jugador).text.toString()
        val alturaStr = findViewById<EditText>(R.id.input_altura_jugador).text.toString()

        if (nombre.isNotEmpty() && edadStr.isNotEmpty() && alturaStr.isNotEmpty()) {
            val edad = edadStr.toIntOrNull() ?: 0
            val altura = alturaStr.toDoubleOrNull() ?: 0.0

            val jugadorNuevo = JugadorEntity(Memoria.idNuevoJugador(), nombre, edad, altura)

            response.putExtra("jugadorNuevo", jugadorNuevo)

            setResult(RESULT_OK, response)
            finish()
}
    }
}
