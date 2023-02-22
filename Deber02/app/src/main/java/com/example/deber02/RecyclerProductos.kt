package com.example.deber02

import android.content.Context
import android.text.Html
import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecyclerProductos (
    private val contexto:Context,
    private val lista: ArrayList<Producto>): RecyclerView.Adapter<RecyclerProductos.MyViewHolder>(){
    inner class MyViewHolder(view : View): RecyclerView.ViewHolder(view){
        val titulo: TextView
        val precio: TextView
        val entrega: TextView
        val opciones: TextView
       //val imagen: ImageView

        init{
            titulo = view.findViewById(R.id.tv_titulo)
            precio = view.findViewById(R.id.tv_precio)
            entrega = view.findViewById(R.id.tv_entrega)
            opciones = view.findViewById(R.id.tv_opc)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater
            .from(contexto)
            .inflate(
                R.layout.item_producto,
                parent,
                false
            )
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        var producto = lista[position]
        holder.titulo.text = producto.titulo
        holder.precio.text = producto.precio
        holder.entrega.text = Html.fromHtml(producto.entrega)

        val mitextoU = SpannableString(producto.opciones)
        mitextoU.setSpan(UnderlineSpan(), 0, mitextoU.length, 0)
        holder.opciones.text = mitextoU
    }

    override fun getItemCount(): Int {
        return lista.size
    }
}