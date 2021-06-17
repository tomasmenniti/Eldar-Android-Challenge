package com.example.eldarandroidchallenge.INICIO.NOTAS.LISTADO_NOTAS

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.eldarandroidchallenge.R

class NotasAdapter(val titulos: List<model_recycler_notas>): RecyclerView.Adapter<NotasAdapter.ViewHolderDatos>(){

    private lateinit var mListener : onItemClickListener

    interface onItemClickListener{
        fun onItemClick(id_nota : Int, titulo : String, descripcion : String)
    }

    fun setOnItemClickListener(listener: onItemClickListener){
        mListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderDatos{
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_row_recycler_notas, parent, false)

        return ViewHolderDatos(v, mListener)
    }

    override fun onBindViewHolder(holder: ViewHolderDatos, position: Int){
        holder.txt_titulo_nota.text = titulos[position].titulo
    }

    override fun getItemCount(): Int{
        return titulos.size
    }

    inner class ViewHolderDatos(itemView: View, listener: onItemClickListener): RecyclerView.ViewHolder(itemView){
        var txt_titulo_nota : TextView
        var constraint_notas : ConstraintLayout

        init{
            itemView.setOnClickListener{
                listener.onItemClick(titulos[adapterPosition].id_nota, titulos[adapterPosition].titulo, titulos[adapterPosition].descripcion)
            }

            constraint_notas = itemView.findViewById(R.id.constraint_notas)
            txt_titulo_nota = itemView.findViewById(R.id.txt_titulo_nota)
        }
    }
}