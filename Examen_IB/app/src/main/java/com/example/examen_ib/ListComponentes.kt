package com.example.examen_ib

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class ListComponentes : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_componentes)
        val computadorId =intent.getStringExtra("computadorId")
    }
}