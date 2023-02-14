package com.example.deber01

class Componente(
    var id:Int,
    var idComputador: Int,
    var nombre:String,
    var precio:Double,
    var fechaFabricacion: String,
    var marca:String,
    var nuevo:Int

) {
    override fun toString(): String {
        return "Nombre: ${nombre}\n" +
                "Precio: ${precio}\n" +
                "Fecha de fabricación: ${fechaFabricacion}\n" +
                "Marca: ${marca}\n" +
                "Nuevo: ${nuevo}\n"
    }
}