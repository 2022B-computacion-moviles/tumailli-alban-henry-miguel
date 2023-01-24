package com.example.movcomphmta

class BBaseDatosMemoria {
    companion object{
        val arregloBEntrenador = arrayListOf<BEntrenador>()
        init {
            arregloBEntrenador
                .add(
                    BEntrenador(1,"Henry","h@h.com")
                )
            arregloBEntrenador
                .add(
                    BEntrenador(2,"Miguel","m@m.com")
                )
            arregloBEntrenador
                .add(
                    BEntrenador(3,"Carolina","k@k.com")
                )
        }
    }
}