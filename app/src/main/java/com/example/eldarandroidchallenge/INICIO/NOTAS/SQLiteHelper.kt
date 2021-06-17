package com.example.eldarandroidchallenge.INICIO.NOTAS

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class SQLiteHelper(context : Context?) : SQLiteOpenHelper(
    context, "db_notas", null, 1){

    override fun onCreate(db: SQLiteDatabase?){
        val orden_creacion = "CREATE TABLE notas (id_nota INTEGER PRIMARY KEY AUTOINCREMENT, titulo TEXT, descripcion TEXT)"
        db!!.execSQL(orden_creacion)
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int){
        val orden_borrado = "DROP TABLE IF EXISTS notas"
        db!!.execSQL(orden_borrado)
        onCreate(db)
    }

    fun agregarNota(titulo : String, descripcion : String){
        val datos = ContentValues()
        datos.put("titulo", titulo)
        datos.put("descripcion", descripcion)

        val db = this.writableDatabase
        db.insert("notas", null, datos)
        db.close()
    }

    fun modificarNota(id_nota : Int?, titulo : String, descripcion : String){
        val args = arrayOf(id_nota.toString())

        val datos = ContentValues()
        datos.put("titulo", titulo)
        datos.put("descripcion", descripcion)

        val db = this.writableDatabase
        db.update("notas", datos, "id_nota = ?", args)
        db.close()
    }

    fun eliminarNota(id_nota : Int?){
        val args = arrayOf(id_nota.toString())

        val db = this.writableDatabase
        db.delete("notas", "id_nota = ?", args)
        db.close()
    }
}