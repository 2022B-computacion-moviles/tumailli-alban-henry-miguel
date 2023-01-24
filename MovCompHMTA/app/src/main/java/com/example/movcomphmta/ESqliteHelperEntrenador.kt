package com.example.movcomphmta

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class ESqliteHelperEntrenador(
    contexto:Context?
): SQLiteOpenHelper(
    contexto,
    "moviles", //Nombre de la base de datos SQlite (moviles.sqlite)
    null,
    1
){
    override fun onCreate(db: SQLiteDatabase?) {
        val scripSQLCrearTablaEntrenador =
            """
                CREATE TABLE ENTRENADOR(
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    nombre VARCHAR(50),
                    descripcion VARCHAR(50)
                )
            """.trimIndent()
        db?.execSQL(scripSQLCrearTablaEntrenador)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }

    fun crearEntrenador(
        nombre:String,
        descripcion: String
    ):Boolean{
        val basedatosEscritura = writableDatabase
        val valoresAGuardar = ContentValues()
        valoresAGuardar.put("nombre",nombre)
        valoresAGuardar.put("descripcion",descripcion)

        val resultadoGuardar = basedatosEscritura
            .insert(
                "ENTRENADOR", // Tabla
                null, //
                valoresAGuardar // valores
            )
        basedatosEscritura.close()
        return  if(resultadoGuardar.toInt() == -1) false else true
    }

    fun eliminarEntrenadorFormulario(id:Int):Boolean{
        val conexionEscritura = writableDatabase
        val resultadoEliminado = conexionEscritura
            .delete(
                "ENTRENADOR", //tabla
                "id=?", // id=? and nombre=? Where (podemos amdnar parametros en orden)
                arrayOf(
                    id.toString()
                )
            )
        conexionEscritura.close()
        return  if(resultadoEliminado.toInt() == -1) false else true
    }

    fun actualizarEntrenadorFormulario(
        nombre: String,
        descripcion: String,
        idActualizar:Int
    ): Boolean{
        val conexionEscritura = writableDatabase
        val valoresAActualizar = ContentValues()
        valoresAActualizar.put("nombre",nombre)
        valoresAActualizar.put("descripcion",descripcion)
        val resultadoActualizacion = conexionEscritura
            .update(
                "ENTRENADOR", //  tabla
                valoresAActualizar, // valores a actualizar
                "id=?", //clausula where
                arrayOf(
                    idActualizar.toString()
                ) // Parametros de la clausula where
            )
        conexionEscritura.close()
        return if (resultadoActualizacion == -1) false else true
    }

    fun consultarEntrenadorPorId(id:Int):BEntrenador{
        val baseDatosLectura = readableDatabase
        val scriptConsultarUsuario = "SELECT * FROM  ENTRENADOR WHERE ID = ?"
        val resultadoConsultaLectura = baseDatosLectura.rawQuery(
            scriptConsultarUsuario,
            arrayOf(
                id.toString()
            )
        )
        val existeUsuario = resultadoConsultaLectura.moveToFirst()
        val usuarioEncontrado = BEntrenador (0,"","")
        //Logica obtener usuario
        do{
            val id = resultadoConsultaLectura.getInt(0)
            val nombre = resultadoConsultaLectura.getString(1)
            val descripcion = resultadoConsultaLectura.getString(2)
            if(id != null){
                usuarioEncontrado.id=id
                usuarioEncontrado.nombre=nombre
                usuarioEncontrado.descripcion = descripcion
            }
        }while (resultadoConsultaLectura.moveToNext())
        resultadoConsultaLectura.close()
        baseDatosLectura.close()
        return usuarioEncontrado
    }


}