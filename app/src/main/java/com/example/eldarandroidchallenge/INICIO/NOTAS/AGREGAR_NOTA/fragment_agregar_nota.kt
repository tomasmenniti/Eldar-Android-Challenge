package com.example.eldarandroidchallenge.INICIO.NOTAS.AGREGAR_NOTA

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import com.example.eldarandroidchallenge.INICIO.NOTAS.LISTADO_NOTAS.fragment_listado_notas
import com.example.eldarandroidchallenge.INICIO.NOTAS.SQLiteHelper
import com.example.eldarandroidchallenge.R
import com.google.android.material.snackbar.Snackbar

class fragment_agregar_nota : Fragment(){

    lateinit var notas_db : SQLiteHelper

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?{
        val view : View = inflater.inflate(R.layout.fragment_agregar_nota, container, false)

        //FINDVIEWS---------------------------------------------------------------------------------
        val constraint_agregar_nota : ConstraintLayout = view.findViewById(R.id.constraint_agregar_nota)
        val et_titulo : EditText = view.findViewById(R.id.et_titulo)
        val et_descripcion : EditText = view.findViewById(R.id.et_descripcion)
        val bt_guardar : Button = view.findViewById(R.id.bt_guardar)
        val txt_volver_atras : TextView = view.findViewById(R.id.txt_volver_atras)

        //INICIALIZACIONES--------------------------------------------------------------------------
        notas_db = SQLiteHelper(context)

        //EVENTSLISTENER----------------------------------------------------------------------------
        bt_guardar.setOnClickListener{
            val titulo : String = et_titulo.text.toString()
            val descripcion : String = et_descripcion.text.toString()

            if(titulo.length == 0 || descripcion.length == 0){
                val snack = Snackbar.make(constraint_agregar_nota, resources.getString(R.string.campos_vacios), Snackbar.LENGTH_LONG)
                snack.setBackgroundTint(Color.parseColor("#616161"))
                snack.setTextColor(Color.parseColor("#ffffff"))
                snack.setAction(resources.getString(R.string.ok)){
                    snack.dismiss()
                }
                snack.setActionTextColor(Color.parseColor("#ffffff"))
                snack.show()
            }
            else{
                notas_db.agregarNota(titulo, descripcion)
                Toast.makeText(context, resources.getString(R.string.nota_agregada), Toast.LENGTH_LONG).show()
                volverAtras()
            }
        }

        txt_volver_atras.setOnClickListener{
            volverAtras()
        }

        return view
    }

    fun volverAtras(){
        var fragment_listado_notas : Fragment = fragment_listado_notas()
        requireActivity().supportFragmentManager.beginTransaction().replace(R.id.fragment_notas, fragment_listado_notas).commit()
    }
}