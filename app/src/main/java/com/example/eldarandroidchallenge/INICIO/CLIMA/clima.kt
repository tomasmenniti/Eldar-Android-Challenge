package com.example.eldarandroidchallenge.INICIO.CLIMA

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.example.eldarandroidchallenge.R
import org.json.JSONObject
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*

class clima : AppCompatActivity(){

    val CITY: String = "buenos aires,ar"
    val API: String = "8118ed6ee68db2debfaaa5a44c832918"

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_clima)

        //FINDVIEWS---------------------------------------------------------------------------------
        val imgbt_back : ImageButton = findViewById(R.id.imgbt_back)
        val txt_temperatura : TextView = findViewById(R.id.txt_temperatura)

        //INICIALIZACIONES
        weatherTask().execute()

        //EVENTS LISTENER---------------------------------------------------------------------------
        imgbt_back.setOnClickListener{
            onBackPressed()
        }
    }

    inner class weatherTask() : AsyncTask<String, Void, String>(){
        override fun onPreExecute(){
            super.onPreExecute()
        }

        override fun doInBackground(vararg params: String?): String?{
            var response:String?
            try{
                response = URL("https://api.openweathermap.org/data/2.5/weather?q=$CITY&units=metric&appid=$API").readText(
                    Charsets.UTF_8
                )
            }catch (e: Exception){
                response = null
            }
            return response
        }

        override fun onPostExecute(result: String?){
            super.onPostExecute(result)
            try {
                /* Extracting JSON returns from the API */
                val jsonObj = JSONObject(result)
                val main = jsonObj.getJSONObject("main")

                val updatedAt:Long = jsonObj.getLong("dt")
                val updatedAtText = "Updated at: "+ SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.ENGLISH).format(
                    Date(updatedAt*1000)
                )
                val temp = main.getString("temp")+"°C"
                val tempMin = main.getString("temp_min")+"°C"
                val tempMax = main.getString("temp_max")+"°C"


                findViewById<TextView>(R.id.txt_temperatura1).text = temp
                findViewById<TextView>(R.id.txt_temperatura_minima1).text = tempMin
                findViewById<TextView>(R.id.txt_temperatura_maxima1).text = tempMax


            } catch (e: Exception){
                Toast.makeText(this@clima, resources.getString(R.string.error), Toast.LENGTH_SHORT).show()
            }

        }
    }
}