package com.example.deber_02

import android.util.Log

class Memoria {
    companion object {
        val equipos = arrayListOf<EquipoEntity>()
        val jugadores = arrayListOf<JugadorEntity>()

        init {
            initJugadores()
            initEquipos()
        }

        fun initJugadores() {
            jugadores.add(JugadorEntity(1, "Lionel Messi", 34, 1.69))
            jugadores.add(JugadorEntity(2, "Cristiano Ronaldo", 36, 1.87))
            jugadores.add(JugadorEntity(3, "Neymar Jr.", 29, 1.75))
            jugadores.add(JugadorEntity(4, "Kylian Mbappé", 24, 1.78))
            jugadores.add(JugadorEntity(5, "Kevin De Bruyne", 32, 1.81))
            jugadores.add(JugadorEntity(6, "Luka Modrić", 38, 1.72))
            jugadores.add(JugadorEntity(7, "Antoine Griezmann", 33, 1.76))
            jugadores.add(JugadorEntity(8, "Sergio Ramos", 38, 1.84))
            jugadores.add(JugadorEntity(9, "Robert Lewandowski", 35, 1.85))
            jugadores.add(JugadorEntity(10, "Harry Kane", 30, 1.88))
            jugadores.add(JugadorEntity(11, "Eden Hazard", 33, 1.75))
            jugadores.add(JugadorEntity(12, "Paulo Dybala", 30, 1.77))
            jugadores.add(JugadorEntity(13, "Thomas Müller", 34, 1.86))
            jugadores.add(JugadorEntity(14, "David Alaba", 32, 1.80))
            jugadores.add(JugadorEntity(15, "Joshua Kimmich", 29, 1.77))
            jugadores.add(JugadorEntity(16, "Gianluigi Donnarumma", 25, 1.96))
            jugadores.add(JugadorEntity(17, "Leonardo Bonucci", 37, 1.90))
            jugadores.add(JugadorEntity(18, "Federico Chiesa", 26, 1.75))
            Log.d("Memoria", "Jugadores iniciales: $jugadores")
        }

        fun initEquipos() {
            equipos.add(EquipoEntity(1, "FC Barcelona", "1899-11-29", "Barcelona", mutableListOf(1, 2, 3)))
            equipos.add(EquipoEntity(2, "Real Madrid", "1902-03-06", "Madrid", mutableListOf(4, 5, 6)))
            equipos.add(EquipoEntity(3, "Paris Saint-Germain", "1970-08-12", "París", mutableListOf(7, 8, 9)))
            equipos.add(EquipoEntity(4, "Manchester City", "1880-11-13", "Manchester", mutableListOf(10, 11, 12)))
            equipos.add(EquipoEntity(5, "Bayern Munich", "1900-02-27", "Múnich", mutableListOf(13, 14, 15)))
            equipos.add(EquipoEntity(6, "Juventus", "1897-11-01", "Turín", mutableListOf(16, 17, 18)))
            Log.d("Memoria", "Equipos iniciales: $equipos")
        }

        fun agregarJugadorEquipo(id: Int, jugador: JugadorEntity) {
            equipos.forEach { equipo ->
                if (equipo.id == id) {
                    equipo.jugadores?.add(jugador.id)
                }
            }
        }

        fun idNuevoEquipo(): Int {
            return equipos.lastOrNull()?.id?.plus(1) ?: 1
        }

        fun idNuevoJugador(): Int {
            return jugadores.lastOrNull()?.id?.plus(1) ?: 1
        }

        fun actualizarJugador(jugador: JugadorEntity) {
            val jugadorIndex = jugadores.indexOfFirst { it.id == jugador.id }
            if (jugadorIndex != -1) {
                jugadores[jugadorIndex] = jugador
            }
        }

        fun eliminarJugadorEquipo(idEquipo: Int, idJugador: Int) {
            val equipoIndex = equipos.indexOfFirst { it.id == idEquipo }
            val jugadores = equipos[equipoIndex].jugadores
            jugadores?.let {
                val jugadorIndex = it.indexOfFirst { it == idJugador }
                if (jugadorIndex != -1) {
                    it.removeAt(jugadorIndex)
                }
            }
        }
    }
}
