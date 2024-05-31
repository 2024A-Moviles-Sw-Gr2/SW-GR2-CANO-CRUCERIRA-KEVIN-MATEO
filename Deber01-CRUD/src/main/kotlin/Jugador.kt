import java.util.*
import java.io.File

class Jugador(
    val id: Int,
    var nombre: String,
    var edad: Int,
    var altura: Double,
    var esTitular: Boolean,
    var nombreEquipo: String
) {
    init {
        this.id
        this.nombre
        this.edad
        this.altura
        this.esTitular
        this.nombreEquipo
    }

    companion object {
        val archivoJugador = "C:\\Users\\kcano\\Documents\\AplicacionesMoviles\\SW-GR2-CANO-CRUCERIRA-KEVIN-MATEO\\Deber01-CRUD\\src\\main\\kotlin\\jugadaores.txt"

        fun crearJugador (jugador: Jugador) {
            val equipos = File(Equipo.archivoEquipo).readLines()
            val equipoExistente = equipos.find { it.split(",")[1] == jugador.nombreEquipo}
            if (equipoExistente != null) {
                val propJugador =
                    "${jugador.id},${jugador.nombre},${jugador.edad},${jugador.altura},${jugador.esTitular},${jugador.nombreEquipo}"
                File(archivoJugador).appendText("$propJugador\n")
                print("Jugador creado correctamente:\n ${propJugador} \n")

            } else {
                print("El equipo del jugador no existe. Por favor, crea el equipo primero.\n")
            }
        }

        fun leerJugadoress(){
            print("Jugadores existentes: \n")
            print(File(archivoJugador).readText())
        }

        fun eliminarJugador(nombre: String){
            val jugadores = File(archivoJugador).readLines()
            val jugadorAEliminar = jugadores.find { it.contains(nombre) }
            if (jugadorAEliminar != null) {
                File(archivoJugador).writeText(jugadores.filter { !it.contains(nombre) }.joinToString("\n"))
                print("Jugador eliminado correctamente \n")
            } else {
                print("Jugador no encontrado \n")
            }
        }

        fun actualizarJugador(id: Int, nombre: String? = null, edad: Int? = null, altura: Double? = null, esTitular: Boolean? = null, nombreEquipo: String? = null) {
            val equipos = File(Equipo.archivoEquipo).readLines()
            val equipoExistente = equipos.find { it.split(",")[1] == nombreEquipo }
            if (equipoExistente != null) {
                val jugadores = File(archivoJugador).readLines()
                val jugadorAActualizar = jugadores.find { it.contains(id.toString()) }
                if (jugadorAActualizar != null) {
                    val jugadorActualizado = jugadorAActualizar.split(",")
                    val jugadorNuevo =
                        "${id},${nombre ?: jugadorActualizado[1]},${edad ?: jugadorActualizado[2]},${altura ?: jugadorActualizado[3]},${esTitular ?: jugadorActualizado[4]},${nombreEquipo ?: jugadorActualizado[5]}"
                    File(archivoJugador).writeText(jugadores.map { if (it == jugadorAActualizar) jugadorNuevo else it }
                        .joinToString("\n"))
                    print("Jugador actualizado correctamente \n")
                } else {
                    print("Jugador no encontrado \n")
                }
            } else {
                print("El equipo del jugador no existe. Por favor, crea el equipo primero.\n")
            }
        }
    }
}