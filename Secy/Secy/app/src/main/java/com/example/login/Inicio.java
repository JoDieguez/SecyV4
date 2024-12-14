package com.example.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import androidx.appcompat.app.AppCompatActivity;

//heredamos elementos de una clase llamada AppCompatActivity
public class Inicio extends AppCompatActivity {
    //Programamos un metodo para iniciar la ventana de registro
    //declaracion variable
    public Spinner spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inicio);
        //enlazar id de spinner
       // spinner = findViewById(R.id.spinnerAlerta);
        //definir arreglo
      //  String[] alertas = {"Alerta Luminica","Alerta Sonora"};
        //creacion de ArrayAdapter para poblar spinner
      //  ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, alertas);

        //spinner = findViewById(R.id.spinnerBloqueo);
        //definir arreglo
        //String[] puertas = {"Puerta Entrada", "Puerta Cuarto","Puerta Studio"};
        //creacion de ArrayAdapter para poblar spinner
        //ArrayAdapter<String> puerta = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, puertas);
        //puerta.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //asigno el ArrayAdapter al Spinner
        //spinner.setAdapter(puerta);

        spinner = findViewById(R.id.spinnerEmergencias);
        //definir arreglo
        String[] emergencias = {"Carabineros", "Ambulancia","Bommberos"};
        //creacion de ArrayAdapter para poblar spinner
        ArrayAdapter<String> emergencia = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, emergencias);
        emergencia.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //asigno el ArrayAdapter al Spinner
        spinner.setAdapter(emergencia);
    }

    public void abrirAlertas(View view) {
        Intent intent = new Intent(this, AlertasActivity.class);
        startActivity(intent);
    }

    public void configAdicional(View view) {
        Intent intent = new Intent(this, CnfgAdicional.class);
        startActivity(intent);
    }



    public void bloqueos(View view) {
        Intent intent = new Intent(this, BloqueoAcceso.class);
        startActivity(intent);
    }

}
