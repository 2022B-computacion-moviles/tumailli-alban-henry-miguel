package com.example.proyecto_iib

import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.appcompat.app.AppCompatActivity


class Agendar : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agendar)

        val items = listOf("Opcion 1","Opcion 2")
        val autoComplete : AutoCompleteTextView = findViewById(R.id.auto_complete_especialista)
        val adapter = ArrayAdapter(this,R.layout.list_item,items)

        autoComplete.setAdapter(adapter)
        autoComplete.onItemClickListener = AdapterView.OnItemClickListener{
            adapterView, view, i, l ->
            val itemSelected = adapterView.getItemAtPosition(i)
        }

    }
}