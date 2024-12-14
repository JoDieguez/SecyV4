package com.example.login;

import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;

public class CnfgAdicional extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.config);

        // Referenciar el TextView para redirigir a MapComisariaActivity
        TextView myTextViewMapComisaria = findViewById(R.id.comisarias);
        myTextViewMapComisaria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Crear el Intent para redirigir a la actividad MapComisariaActivity
                Intent intent = new Intent(CnfgAdicional.this, MapComisaria.class);
                startActivity(intent);  // Iniciar la nueva actividad para mostrar el mapa
            }
        });
    }

    // El método 'dispositivos()' relacionado con la base de datos no se modifica
    public void dispositivos(View view) {
        Intent intent = new Intent(this, BaseDatos.class);
        startActivity(intent);
    }
    public void abrirContactos(View view) {
        Intent intent = new Intent(this, NumeroEmergencia.class);
        startActivity(intent);
    }

    public void abrirBBDD(View view) {
        Intent intent = new Intent(this, RegistroVecinal.class);
        startActivity(intent);
    }
    public void verVideo(View view){
        Intent intent = new Intent(this, VideoSecy.class);
        startActivity(intent);
    }
    public void llamarEmergencia (View view){
        Intent intemt = new Intent(this, LlamEmergencia.class);
        startActivity(intemt);
    }
}




