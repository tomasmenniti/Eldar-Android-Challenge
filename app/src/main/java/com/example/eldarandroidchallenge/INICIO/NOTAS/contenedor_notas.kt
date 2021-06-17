package com.example.eldarandroidchallenge.INICIO.NOTAS

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.eldarandroidchallenge.INICIO.NOTAS.LISTADO_NOTAS.fragment_listado_notas
import com.example.eldarandroidchallenge.R

class contenedor_notas : AppCompatActivity()/*, fab_click_listener*/{
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contenedor_notas)

        //FINDVIEWS---------------------------------------------------------------------------------
        var imgbt_back : ImageButton = findViewById(R.id.imgbt_back)
        var txt_titulo : TextView = findViewById(R.id.txt_titulo)

        //INICIALIZACIONES--------------------------------------------------------------------------
        var fragment_listado_notas : Fragment? = fragment_listado_notas()

        if(fragment_listado_notas != null){
            txt_titulo.setText(resources.getString(R.string.mis_notas))
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_notas, fragment_listado_notas)
            transaction.commit()
        }

        //EVENTS LISTENER---------------------------------------------------------------------------
        imgbt_back.setOnClickListener{
            onBackPressed()
        }
    }

    /*override fun onFabClicked(id_nota: Int){
        var fragment_agregar_nota : Fragment? = fragment_agregar_nota()
        var bundle : Bundle = Bundle()
        bundle.putInt("id_nota", 99)

        if(fragment_agregar_nota != null){
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_notas, fragment_agregar_nota)
            transaction.commit()
        }
    }*/
}