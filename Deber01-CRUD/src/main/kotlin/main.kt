import java.text.SimpleDateFormat

fun main () {
    val formatoFecha = SimpleDateFormat("dd/MM/yyyy")

    val equipo = Equipo(1, "Barcelona", formatoFecha.parse("01/01/1900"), "Barcelona", true)
    val equipo2 = Equipo(2, "Real Madrid", formatoFecha.parse("01/01/1900"), "Madrid", true)
    val equipo3 = Equipo(3, "Real Sociedad", formatoFecha.parse("01/01/2000"), "Sociedad", false)
    Equipo.crearEquipo(equipo)
    Equipo.crearEquipo(equipo2)
    Equipo.crearEquipo(equipo3)
    Equipo.leerEquipos()
    Equipo.actualizarEquipo(1, nombre = "Barcelona FC", tieneEstadio = false)
    Equipo.leerEquipos()
    Equipo.eliminarEquipo("Real Sociedad")
    Equipo.leerEquipos()

    val jugador = Jugador(1, "Lionel Messi", 33, 1.70, true, "Barcelona")
    val jugador2 = Jugador(2, "Sergio Ramos", 34, 1.80, true, "Real Madrid")
    Jugador.crearJugador(jugador)
    Jugador.crearJugador(jugador2)
    Jugador.leerJugadoress()
    Jugador.actualizarJugador(1, nombre = "Lionel Messi", nombreEquipo = "Barcelona", edad= 34)
    Jugador.leerJugadoress()
    Jugador.eliminarJugador("Sergio Ramos")
    Jugador.leerJugadoress()



}