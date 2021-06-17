package com.example.eldarandroidchallenge;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.eldarandroidchallenge.INICIO.inicio;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class iniciar_sesion extends AppCompatActivity{

    private int log = 0;

    private ConstraintLayout constraint_inicio_sesion;
    private EditText et_usuario, et_contrasena;
    private Button bt_iniciar_sesion;

    private ProgressDialog progress;
    private JsonObjectRequest jsonObjectRequest;
    private SharedPreferences getPrefs_usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.iniciar_sesion);

        findViews();
        inicializaciones();
        eventsListener();
    }

    private void findViews(){
        constraint_inicio_sesion = findViewById(R.id.constraint_inicio_sesion);
        et_usuario = findViewById(R.id.et_usuario);
        et_contrasena = findViewById(R.id.et_contrasena);
        bt_iniciar_sesion = findViewById(R.id.bt_iniciar_sesion);
    }

    private void inicializaciones(){
        getPrefs_usuario = getSharedPreferences("prefs_usuario",0);
        log = getPrefs_usuario.getInt("log",0);

        if(log == 1){//SI LA SESION ESTÁ INICIADA
            haciaInicio();
        }

        progress = new ProgressDialog(iniciar_sesion.this);
        progress.setMessage(getResources().getString(R.string.cargando));
    }

    private void eventsListener(){
        bt_iniciar_sesion.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                String usuario = et_usuario.getText().toString()
                        , contrasena = et_contrasena.getText().toString();

                if(usuario.length() == 0 || contrasena.length() == 0){
                    final Snackbar snack = Snackbar.make(constraint_inicio_sesion, getResources().getString(R.string.campos_vacios), Snackbar.LENGTH_LONG);
                    snack.setBackgroundTint(Color.parseColor("#616161"));
                    snack.setTextColor(Color.parseColor("#ffffff"));
                    snack.setAction(getResources().getString(R.string.ok),new View.OnClickListener(){
                        @Override
                        public void onClick(View v){
                            snack.dismiss();
                        }
                    });
                    snack.setActionTextColor(Color.parseColor("#ffffff"));
                    snack.show();
                }
                else{
                    progress.show();
                    String url = getResources().getString(R.string.ip) + "/ws_doggy/consulta_login_eldar.php?usuario=" + et_usuario.getText().toString() + "&contrasena=" + et_contrasena.getText().toString();

                    jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>(){
                        @Override
                        public void onResponse(JSONObject response){
                            progress.hide();

                            JSONArray json = response.optJSONArray("rta");
                            JSONObject jsonObject = null;

                            try{
                                jsonObject = json.getJSONObject(0);

                                //SI LOS DATOS EXISTEN
                                if((jsonObject.optString("respuesta")).equalsIgnoreCase("1")){
                                    //GUARDO EL LOG PARA INGRESAR SIN PONER LOS DATOS
                                    SharedPreferences prefs_usuario = getSharedPreferences("prefs_usuario", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor_usuario = prefs_usuario.edit();

                                    editor_usuario.putInt("log", 1);
                                    editor_usuario.commit();

                                    Toast.makeText(iniciar_sesion.this, getResources().getString(R.string.bienvenido), Toast.LENGTH_SHORT).show();
                                    haciaInicio();
                                }
                                //SI NINGUNO DE LOS DOS DATOS ESTA REGISTRADO
                                else if((jsonObject.optString("respuesta")).equalsIgnoreCase("0")){
                                    final Snackbar snack = Snackbar.make(constraint_inicio_sesion, getResources().getString(R.string.datos_incorrectos), Snackbar.LENGTH_LONG);
                                    snack.setBackgroundTint(Color.parseColor("#616161"));
                                    snack.setTextColor(Color.parseColor("#ffffff"));
                                    snack.setAction(getResources().getString(R.string.ok),new View.OnClickListener(){
                                        @Override
                                        public void onClick(View v){
                                            snack.dismiss();
                                        }
                                    });
                                    snack.setActionTextColor(Color.parseColor("#ffffff"));
                                    snack.show();
                                }
                            }
                            catch(JSONException e){
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener(){
                        @Override
                        public void onErrorResponse(VolleyError error){
                            progress.hide();

                            final Snackbar snack = Snackbar.make(constraint_inicio_sesion, getResources().getString(R.string.error_servidor), Snackbar.LENGTH_LONG);
                            snack.setBackgroundTint(Color.parseColor("#616161"));
                            snack.setTextColor(Color.parseColor("#ffffff"));
                            snack.setAction(getResources().getString(R.string.ok),new View.OnClickListener(){
                                @Override
                                public void onClick(View v){
                                    snack.dismiss();
                                }
                            });
                            snack.setActionTextColor(Color.parseColor("#ffffff"));
                            snack.show();
                        }
                    });
                    VolleySingleton.getInstanciaVolley(iniciar_sesion.this).addToRequestQueue(jsonObjectRequest);
                }
            }
        });
    }

    private void haciaInicio(){
        Intent intent = new Intent(iniciar_sesion.this, inicio.class);
        startActivity(intent);
    }
}