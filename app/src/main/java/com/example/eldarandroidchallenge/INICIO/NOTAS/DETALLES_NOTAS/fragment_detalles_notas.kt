package com.example.eldarandroidchallenge.INICIO.NOTAS.DETALLES_NOTAS

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.eldarandroidchallenge.INICIO.NOTAS.LISTADO_NOTAS.fragment_listado_notas
import com.example.eldarandroidchallenge.INICIO.NOTAS.SQLiteHelper
import com.example.eldarandroidchallenge.R
import com.google.android.material.snackbar.Snackbar

class fragment_detalles_notas : Fragment(){

    lateinit var notas_db : SQLiteHelper

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?{
        val view : View = inflater.inflate(R.layout.fragment_detalles_notas, container, false)

        //FINDVIEWS---------------------------------------------------------------------------------
        val constraint_detalles_notas : ConstraintLayout = view.findViewById(R.id.constraint_detalles_notas)
        val et_titulo : EditText = view.findViewById(R.id.et_titulo)
        val et_descripcion : EditText = view.findViewById(R.id.et_descripcion)
        val bt_realizar_modificacion : Button = view.findViewById(R.id.bt_realizar_modificacion)
        val bt_eliminar : Button = view.findViewById(R.id.bt_eliminar)
        val txt_volver_atras : TextView = view.findViewById(R.id.txt_volver_atras)

        //INICIALIZACIONES--------------------------------------------------------------------------
        notas_db = SQLiteHelper(context)
        val id_nota = arguments?.getInt("id_nota")
        et_titulo.setText(arguments?.getString("titulo"))
        et_descripcion.setText(arguments?.getString("descripcion"))

        //EVENTS LISTENER---------------------------------------------------------------------------
        bt_realizar_modificacion.setOnClickListener{
            val titulo : String = et_titulo.text.toString()
            val descripcion : String = et_descripcion.text.toString()

            if(titulo.length == 0 || descripcion.length == 0){
                val snack = Snackbar.make(constraint_detalles_notas, resources.getString(R.string.campos_vacios), Snackbar.LENGTH_LONG)
                snack.setBackgroundTint(Color.parseColor("#616161"))
                snack.setTextColor(Color.parseColor("#ffffff"))
                snack.setAction(resources.getString(R.string.ok)){
                    snack.dismiss()
                }
                snack.setActionTextColor(Color.parseColor("#ffffff"))
                snack.show()
            }
            else{
                notas_db.modificarNota(id_nota, titulo, descripcion)
                Toast.makeText(context, resources.getString(R.string.nota_modificada), Toast.LENGTH_LONG).show()
                volverAtras()
            }
        }

        bt_eliminar.setOnClickListener{
            notas_db.eliminarNota(id_nota)
            Toast.makeText(context, resources.getString(R.string.nota_eliminada), Toast.LENGTH_LONG).show()
            volverAtras()
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