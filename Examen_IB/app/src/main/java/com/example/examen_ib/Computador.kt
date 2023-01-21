package com.example.examen_ib

class Computador(
    var nombre:String,
    var precio:Float,
    var stock: Int,
    var marca:String,
    var nuevo:Boolean

) {
    override fun toString(): String {
        return "Nombre: ${nombre}\n" +
                "Precio: ${precio}\n" +
                "Stock: ${stock}\n" +
                "Marca: ${marca}\n" +
                "Nuevo: ${nuevo}\n"
    }
}