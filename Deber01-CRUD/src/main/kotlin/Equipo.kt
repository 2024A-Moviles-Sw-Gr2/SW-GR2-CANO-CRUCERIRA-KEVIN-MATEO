import java.util.*
import java.io.File

class Equipo (
    val id: Int,
    var nombre: String,
    var fechaCreacion: Date,
    var ciudad: String,
    var tieneEstadio: Boolean,
) {
    init {
        this.id
        this.nombre
        this.fechaCreacion
        this.ciudad
        this.tieneEstadio
    }
    companion object {

        val archivoEquipo = "C:\\Users\\kcano\\Documents\\AplicacionesMoviles\\SW-GR2-CANO-CRUCERIRA-KEVIN-MATEO\\Deber01-CRUD\\src\\main\\kotlin\\equipos.txt"

        fun crearEquipo(equipo: Equipo) {
            val propEquipo = "${equipo.id},${equipo.nombre},${equipo.fechaCreacion},${equipo.ciudad},${equipo.tieneEstadio}"
            File(archivoEquipo).appendText("$propEquipo\n")
            print("Equipo creado correctamente:\n ${propEquipo} \n")
        }

        fun leerEquipos(){
            print("Equipos existentes: \n")
            print(File(archivoEquipo).readText())
        }

        fun eliminarEquipo(nombre: String){
            val equipos = File(archivoEquipo).readLines()
            val equipooAEliminar = equipos.find { it.contains(nombre) }
            if (equipooAEliminar != null) {
                File(archivoEquipo).writeText(equipos.filter { !it.contains(nombre) }.joinToString("\n"))
                print("Equipo eliminado correctamente \n")
            } else {
                print("Equipo no encontrado \n")
            }
        }

        fun actualizarEquipo(id: Int, nombre: String? = null, fechaCreacion: Date? = null, ciudad: String? = null, tieneEstadio: Boolean? = null) {
            val equipos = File(archivoEquipo).readLines()
            val equipoAActualizar = equipos.find { it.contains(id.toString()) }
            if (equipoAActualizar != null) {
                val equipoActualizado = equipoAActualizar.split(",")
                val equipoNuevo = "${id},${nombre ?: equipoActualizado[1]},${fechaCreacion ?: equipoActualizado[2]},${ciudad ?: equipoActualizado[3]},${tieneEstadio ?: equipoActualizado[4]}"
                File(archivoEquipo).writeText(equipos.map { if (it == equipoAActualizar) equipoNuevo else it }.joinToString("\n"))
                print("Equipo actualizado correctamente \n")
            } else {
                print("Equipo no encontrado \n")
            }
        }

    }
}