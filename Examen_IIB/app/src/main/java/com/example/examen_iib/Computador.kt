package com.example.examen_iib

class Computador(
    var id:Int,
    var nombre:String,
    var precio:Double,
    var stock: Int,
    var marca:String,
    var nuevo:Boolean
) {
    override fun toString(): String {
        return "Nombre: ${nombre}\n" +
                "Precio: ${precio}\n" +
                "Stock: ${stock}\n" +
                "Marca: ${marca}\n" +
                "Nuevo: ${nuevo}\n"+
                "Id: ${id}"
    }
}