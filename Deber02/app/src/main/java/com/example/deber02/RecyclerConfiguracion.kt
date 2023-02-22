package com.example.deber02

import android.content.Context
import android.text.Html
import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecyclerConfiguracion (
    private val contexto: Context,
    private val lista: ArrayList<Config>): RecyclerView.Adapter<RecyclerConfiguracion.MyViewHolder>() {
    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val titulo: TextView

        init {
            titulo = view.findViewById(R.id.tv_configuracion)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerConfiguracion.MyViewHolder {
        val itemView = LayoutInflater
            .from(contexto)
            .inflate(
                R.layout.item_configuracion,
                parent,
                false
            )
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        var configuracion = lista[position]
        holder.titulo.text = configuracion.titulo
    }

    override fun getItemCount(): Int {
        return lista.size
    }
}
