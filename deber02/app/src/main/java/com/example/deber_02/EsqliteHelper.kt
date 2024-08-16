package com.example.deber_02

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class EsqliteHelper (
    contexto: Context?
): SQLiteOpenHelper(
    contexto,
    "app",
    null,
    1
) {
    override fun onCreate(db: SQLiteDatabase?) {
        val crearTablaJugadores = """
            CREATE TABLE JUGADOR(
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nombre VARCHAR(100),
                edad INTEGER,
                altura DOUBLE,
                equipo_id INTEGER,
                FOREIGN KEY (equipo_id) REFERENCES EQUIPO(id) ON DELETE SET NULL
            );
        """.trimIndent()
        val crearTablaEquipos = """
            CREATE TABLE EQUIPO(
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nombre VARCHAR(100),
                fechaCreacion VARCHAR(100),
                ciudad VARCHAR(100),
                ubicacionEstadio VARCHAR(50)
            );
        """.trimIndent()
        db?.execSQL(crearTablaJugadores)
        db?.execSQL(crearTablaEquipos)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {}

    fun crearEquipo(
        nombre: String,
        fechaCreacion: String,
        ciudad: String,
        ubicacionEstadio: String
    ): Boolean {
        val writeDB = writableDatabase

        val parametros = ContentValues()
        parametros.put("nombre", nombre)
        parametros.put("fechaCreacion", fechaCreacion)
        parametros.put("ciudad", ciudad)
        parametros.put("ubicacionEstadio", ubicacionEstadio)

        val resultadoGuardar = writeDB
            .insert(
                "EQUIPO",
                null,
                parametros
            )
        writeDB.close()
        return resultadoGuardar.toInt() != -1
    }

    fun crearJugador(
        nombre: String,
        edad: Int,
        altura: Double,
        equipoId: Int
    ): Boolean {
        val writeDB = writableDatabase

        val parametros = ContentValues()
        parametros.put("nombre", nombre)
        parametros.put("edad", edad)
        parametros.put("altura", altura)
        parametros.put("equipo_id", equipoId)

        val resultadoGuardar = writeDB
            .insert(
                "JUGADOR",
                null,
                parametros
            )
        writeDB.close()
        return resultadoGuardar.toInt() != -1
    }


    fun getEquipos(): ArrayList<EquipoEntity> {
        val lectureDB = readableDatabase

        val queryScript = """
        SELECT * FROM EQUIPO
    """.trimIndent()

        val queryResult = lectureDB.rawQuery(
            queryScript,
            null
        )

        val response = arrayListOf<EquipoEntity>()

        if(queryResult.moveToFirst()) {
            do {
                response.add(
                    EquipoEntity(
                        queryResult.getInt(0),
                        queryResult.getString(1),
                        queryResult.getString(2),
                        queryResult.getString(3),
                        queryResult.getString(4)
                    )
                )
            } while(queryResult.moveToNext())
        }
        queryResult.close()
        lectureDB.close()

        return response
    }

    fun getJugadoresPorEquipo(equipoId: Int): ArrayList<JugadorEntity> {
        val lectureDB = readableDatabase

        val queryScript = """
            SELECT * FROM JUGADOR
            WHERE equipo_id = ?
        """.trimIndent()

        val queryResult = lectureDB.rawQuery(
            queryScript,
            arrayOf(equipoId.toString())
        )
        val response = arrayListOf<JugadorEntity>()

        if(queryResult.moveToFirst()) {
            do {
                response.add(
                    JugadorEntity(
                        queryResult.getInt(0),
                        queryResult.getString(1),
                        queryResult.getInt(2),
                        queryResult.getDouble(3),
                        queryResult.getInt(4)
                    )
                )
            } while(queryResult.moveToNext())
        }
        queryResult.close()
        lectureDB.close()

        return response
    }

    fun eliminarEquipo(id:Int): Boolean{
        val conexionEscritura = writableDatabase

        //Consulta SQL para eliminar
        val parametrosConsultaDelete = arrayOf(id.toString())
        val resultadoEliminar = conexionEscritura.delete(
            "EQUIPO",
            "id=?",
            parametrosConsultaDelete
        )
        conexionEscritura.close()
        return resultadoEliminar.toInt() != -1
    }

    fun eliminarJugador(id:Int): Boolean{
        val conexionEscritura = writableDatabase

        //Consulta SQL para eliminar
        val parametrosConsultaDelete = arrayOf(id.toString())
        val resultadoEliminar = conexionEscritura.delete(
            "JUGADOR",
            "id=?",
            parametrosConsultaDelete
        )
        conexionEscritura.close()
        return resultadoEliminar.toInt() != -1
    }

    fun actualizarJugador(
        id: Int,
        nombre: String,
        edad: Int,
        altura: Double,
        equipoId: Int
    ): Boolean {
        val writeDB = writableDatabase

        val parametros = ContentValues()
        parametros.put("nombre", nombre)
        parametros.put("edad", edad)
        parametros.put("altura", altura)
        parametros.put("equipo_id", equipoId)

        val id_query = arrayOf(id.toString())
        val resultadosAtualizacion = writeDB.update(
            "JUGADOR",
            parametros,
            "id=?",
            id_query
        )
        writeDB.close()
        return resultadosAtualizacion.toInt() != -1
    }

    fun actualizarEquipo(
        id: Int,
        nombre: String,
        fechaCreacion: String,
        ciudad: String,
        ubicacionEstadio: String
    ): Boolean {
        val writeDB = writableDatabase

        val parametros = ContentValues()
        parametros.put("nombre", nombre)
        parametros.put("fechaCreacion", fechaCreacion)
        parametros.put("ciudad", ciudad)
        parametros.put("ubicacionEstadio", ubicacionEstadio)

        val id_query = arrayOf(id.toString())
        val resultadosAtualizacion = writeDB.update(
            "EQUIPO",
            parametros,
            "id=?",
            id_query
        )
        writeDB.close()
        return resultadosAtualizacion.toInt() != -1
    }
}