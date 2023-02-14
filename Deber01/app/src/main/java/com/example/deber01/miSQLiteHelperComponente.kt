package com.example.deber01

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class miSQLiteHelperComponente(context: Context) : SQLiteOpenHelper(
    context, "deberComponente", null, 4) {

    override fun onCreate(db: SQLiteDatabase?) {
        val ordenCreacion =
            """
                CREATE TABLE COMPONENTE(
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    idComputador INTEGER,
                    nombre TEXT,
                    precio REAL,
                    fecha TEXT,
                    marca TEXT,
                    nuevo INTEGER
                )
            """
        db!!.execSQL(ordenCreacion)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
    }

    fun guardarComponente(
        idComputador: Int,
        nombre:String,
        precio: Double,
        fecha:String,
        marca:String,
        nuevo: Int
    ):Boolean{
        val basedatosEscritura = writableDatabase
        val valoresAGuardar = ContentValues()
        valoresAGuardar.put("idComputador",idComputador)
        valoresAGuardar.put("nombre",nombre)
        valoresAGuardar.put("precio",precio)
        valoresAGuardar.put("fecha",fecha)
        valoresAGuardar.put("marca",marca)
        valoresAGuardar.put("nuevo",nuevo)

        val resultadoGuardar = basedatosEscritura
            .insert(
                "COMPONENTE", // Tabla
                null, //
                valoresAGuardar // valores
            )
        basedatosEscritura.close()
        return  if(resultadoGuardar.toInt() == -1) false else true
    }

    fun consultarComponentes(idComputador:Int):ArrayList<Componente>{
        val baseDatosLectura = readableDatabase
        val scriptConsultarUsuario = "SELECT * FROM  COMPONENTE WHERE idComputador = ?"
        val resultadoConsultaLectura = baseDatosLectura.rawQuery(
            scriptConsultarUsuario,
            arrayOf(
                idComputador.toString()
            )
        )
        val existeComponentes = resultadoConsultaLectura.moveToFirst()
        val listaComponentes = arrayListOf<Componente>()
        if(existeComponentes){
            do{
                listaComponentes
                    .add(
                        Componente(resultadoConsultaLectura.getInt(0),
                            resultadoConsultaLectura.getInt(1),
                            resultadoConsultaLectura.getString(2),
                            resultadoConsultaLectura.getDouble(3),
                            resultadoConsultaLectura.getString(4),
                            resultadoConsultaLectura.getString(5),
                            resultadoConsultaLectura.getInt(6)
                        ))
            }while (resultadoConsultaLectura.moveToNext())
        }
        resultadoConsultaLectura.close()
        baseDatosLectura.close()
        return listaComponentes
    }

    fun actualizarComponente(
        nombre:String,
        precio: Double,
        fecha:String,
        marca:String,
        nuevo: Int,
        idActualizar:Int
    ): Boolean{
        val conexionEscritura = writableDatabase
        val valoresAActualizar = ContentValues()
        valoresAActualizar.put("nombre",nombre)
        valoresAActualizar.put("precio",precio)
        valoresAActualizar.put("fecha",fecha)
        valoresAActualizar.put("marca",marca)
        valoresAActualizar.put("nuevo",nuevo)
        val resultadoActualizacion = conexionEscritura
            .update(
                "COMPONENTE", //  tabla
                valoresAActualizar, // valores a actualizar
                "id=?", //clausula where
                arrayOf(
                    idActualizar.toString()
                ) // Parametros de la clausula where
            )
        conexionEscritura.close()
        return if (resultadoActualizacion == -1) false else true
    }

    fun consultarComponentePorId(id:Int):Componente{
        val baseDatosLectura = readableDatabase
        val scriptConsultarUsuario = "SELECT * FROM COMPONENTE WHERE ID = ?"
        val resultadoConsultaLectura = baseDatosLectura.rawQuery(
            scriptConsultarUsuario,
            arrayOf(
                id.toString()
            )
        )
        val existeComputador = resultadoConsultaLectura.moveToFirst()
        val componenteEncontrado = Componente(0,0,"",0.0,"","",0)
        //Logica obtener usuario
        do{
            val id = resultadoConsultaLectura.getInt(0)
            val idComputador = resultadoConsultaLectura.getInt(1)
            val nombre = resultadoConsultaLectura.getString(2)
            val precio = resultadoConsultaLectura.getDouble(3)
            val fecha = resultadoConsultaLectura.getString(4)
            val marca = resultadoConsultaLectura.getString(5)
            val nuevo = resultadoConsultaLectura.getInt(6)

            if(id != null){
                componenteEncontrado.id=id
                componenteEncontrado.nombre= nombre
                componenteEncontrado.precio = precio
                componenteEncontrado.fechaFabricacion = fecha
                componenteEncontrado.marca = marca
                componenteEncontrado.nuevo = nuevo
            }
        }while (resultadoConsultaLectura.moveToNext())
        resultadoConsultaLectura.close()
        baseDatosLectura.close()
        return componenteEncontrado
    }

    fun eliminarComponente(id:Int):Boolean{
        val conexionEscritura = writableDatabase
        val resultadoEliminado = conexionEscritura
            .delete(
                "COMPONENTE", //tabla
                "id=?", // id=? and nombre=? Where (podemos amdnar parametros en orden)
                arrayOf(
                    id.toString()
                )
            )
        conexionEscritura.close()
        return  if(resultadoEliminado.toInt() == -1) false else true
    }


}