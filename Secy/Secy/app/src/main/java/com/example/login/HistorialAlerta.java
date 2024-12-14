package com.example.login;

import android.media.SoundPool;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import com.google.*;
import com.google.firebase.firestore.FirebaseFirestore;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.widget.ListView;
import android.widget.Toast;

public class HistorialAlerta extends AppCompatActivity {
    private EditText vecino, tipAlerta, fechaAlerta;
    private ListView lista;
    //conexión de FireStore
    private FireBaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.historialalerta);

        // llamamos al método que carga la lista
        CargaListaFirestore();

        //Inicializo Firestore
        db = FirebaseFirestore.getInstance();

        // variables con los del XML
        vecino = findViewById(R.id.vecino);
        tipAlerta = findViewById(R.id.tipAlerta);
        fechaAlerta = findViewById(R.id.fechaAlerta);

    }

    public void enviarDatosFirestore(View view){
        String vecin = vecino.getText().toString();
        String tipAlert = tipAlerta.getText().toString();
        String fechaAlert = fechaAlerta.getText().toString();

        Map<String, Object> alerta = new HashMap<>();
        alerta.put("vecino", vecin);
        alerta.put("tipoAlerta",tipAlert);
        alerta.put("fechaAlerta",fechaAlert);

        db.collection("alertas")
                .document(vecin)
                .set(tipAlert)
                .addOnSuccessListener(aVoid->{
                    Toast.makeText(HistorialAlertas.this,"Datos enviados correctamente",Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(HistorialAlertas.this,"Error al enviar datos: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    //metodo de Carga Lista
    public void CargarLista(View view) {
        CargaListaFirestore();
    }
    public void CargaListaFirestore(){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("alertas")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>(){
                    if(task.isSuccessful()){
                        List<String>  listaAlertas = new ArrayList<>();
                        for (QueryDocumentSnapshot document : task.getResult()){
                            String linea = "|| " + document.getString("vecino") + " || " +
                                    document.getString("tipoAlerta") + " || " +
                                    document.getString("fechaAlerta");
                            listaAlertas.add(linea);
                        }
                        ArrayAdapter<String> adaptador = new ArrayAdapter<>(
                                HistorialAlertas.this,
                                android.R.layout.simple_list_item_1,listaAlertas
                        );
                        lista.setAdapter(adaptador);
                    }
                    else {
                        Log.e("TAG","Error al obtener datos del Firestore", task.getException());
                    }

                });

    }
}
