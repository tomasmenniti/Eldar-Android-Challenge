package com.example.eldarandroidchallenge.INICIO

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.bumptech.glide.Glide
import com.example.eldarandroidchallenge.INICIO.CLIMA.clima
import com.example.eldarandroidchallenge.INICIO.NOTAS.contenedor_notas
import com.example.eldarandroidchallenge.R

class inicio : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inicio)

        //FINDVIEWS------------------------------------------------------------------------------
        val card_notas : CardView = findViewById(R.id.card_notas)
        val card_clima : CardView = findViewById(R.id.card_clima)
        val img_notas : ImageView = findViewById(R.id.img_notas)
        val img_clima : ImageView = findViewById(R.id.img_clima)
        val bt_cerrar_sesion : Button = findViewById(R.id.bt_cerrar_sesion)

        //INICIALIZACIONES-----------------------------------------------------------------------
        Glide.with(this).load("https://image.freepik.com/free-photo/front-blank-calendar-desk-with-green-bokeh-background_33768-1.jpg")
            .placeholder(R.drawable.img_loading).dontAnimate().into(img_notas)
        Glide.with(this).load("https://media.apnarm.net.au/media/images/2020/08/07/v3imagesbina76005dedfda6535ff832055ec0c5527-pcbcbniwrx8555v0tu2.jpg")
            .placeholder(R.drawable.img_loading).dontAnimate().into(img_clima)

        //EVENTS-LISTENER------------------------------------------------------------------------
        card_notas.setOnClickListener{
            val intent = Intent(this, contenedor_notas::class.java)
            startActivity(intent)
        }

        card_clima.setOnClickListener{
            val intent = Intent(this, clima::class.java)
            startActivity(intent)
        }

        bt_cerrar_sesion.setOnClickListener{
            val builder = AlertDialog.Builder(this)
            builder.setTitle(resources.getString(R.string.estas_seguro))
            builder.setMessage(resources.getString(R.string.deseas_salir))
            builder.setPositiveButton(resources.getString(R.string.ok),{dialogInterface: DialogInterface, i: Int -> cerrarSesion()})
            builder.setNegativeButton("No",{dialogInterface: DialogInterface, i: Int -> })
            builder.show()
        }
    }

    fun cerrarSesion(){
        val prefs_usuario = getSharedPreferences("prefs_usuario", MODE_PRIVATE)
        val editor_usuario = prefs_usuario.edit()

        editor_usuario.putInt("log", 0)
        editor_usuario.commit()

        finish()
    }

    override fun onBackPressed(){
    }
}