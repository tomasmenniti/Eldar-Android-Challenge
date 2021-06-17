package com.example.eldarandroidchallenge.INICIO.NOTAS.LISTADO_NOTAS

import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.eldarandroidchallenge.INICIO.NOTAS.AGREGAR_NOTA.fragment_agregar_nota
import com.example.eldarandroidchallenge.INICIO.NOTAS.DETALLES_NOTAS.fragment_detalles_notas
import com.example.eldarandroidchallenge.INICIO.NOTAS.SQLiteHelper
import com.example.eldarandroidchallenge.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class fragment_listado_notas : Fragment(){

    lateinit var notas_db : SQLiteHelper

    /*val modelRecyclerNotas = listOf(
        model_recycler_notas(1, "Hola", "Saludar por cumpleaños"),
        model_recycler_notas(2, "Chau", "Despedir a cinthia"),
        model_recycler_notas(3, "Caminar", "Caminata de 20km. por Palermo")
    )*/

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view : View = inflater.inflate(R.layout.fragment_listado_notas, container, false)

        //FINDVIEWS---------------------------------------------------------------------------------
        val recycler_notas : RecyclerView = view.findViewById(R.id.recycler_notas)
        val fab_agregar_nota : FloatingActionButton = view.findViewById(R.id.fab_agregar_nota)

        //INICIALIZACIONES--------------------------------------------------------------------------
        notas_db = SQLiteHelper(context)
        var adaptador_recycler_notas = NotasAdapter(listOf(model_recycler_notas(0,"","")))
        val db : SQLiteDatabase = notas_db.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM notas",null)

        if(cursor.moveToFirst()){
            val array_notas : ArrayList<model_recycler_notas> = ArrayList()

            do{
                array_notas.add(model_recycler_notas(cursor.getInt(0), cursor.getString(1), cursor.getString(2)))
            }
            while(cursor.moveToNext())

            adaptador_recycler_notas = NotasAdapter(array_notas)

            recycler_notas.layoutManager = LinearLayoutManager(context)
            recycler_notas.adapter = adaptador_recycler_notas
        }
        else{

        }

        //EVENTSLISTENER----------------------------------------------------------------------------
        adaptador_recycler_notas.setOnItemClickListener(object : NotasAdapter.onItemClickListener{
            override fun onItemClick(id_nota : Int, titulo : String, descripcion : String){
                var fragment_detalles_notas : Fragment = fragment_detalles_notas()

                val bundle = Bundle()
                bundle.putInt("id_nota", id_nota)
                bundle.putString("titulo",titulo)
                bundle.putString("descripcion",descripcion)

                fragment_detalles_notas.arguments = bundle

                requireActivity().supportFragmentManager.beginTransaction().replace(R.id.fragment_notas, fragment_detalles_notas).commit()
            }
        })

        fab_agregar_nota.setOnClickListener{
            var fragment_agregar_nota : Fragment = fragment_agregar_nota()
            requireActivity().supportFragmentManager.beginTransaction().replace(R.id.fragment_notas, fragment_agregar_nota).commit()
        }

        return view
    }
}