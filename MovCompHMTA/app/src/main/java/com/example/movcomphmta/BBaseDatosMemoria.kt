package com.example.movcomphmta

class BBaseDatosMemoria {
    companion object{
        val arregloBEntrenador = arrayListOf<BEntrenador>()
        init {
            arregloBEntrenador
                .add(
                    BEntrenador("Henry","h@h.com")
                )
            arregloBEntrenador
                .add(
                    BEntrenador("Miguel","m@m.com")
                )
            arregloBEntrenador
                .add(
                    BEntrenador("Carolina","k@k.com")
                )
        }
    }
}