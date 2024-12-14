package com.example.login;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class NumeroEmergencia extends AppCompatActivity{
    // Declaracion de variables para enlazar los id de objetos en numero de emergencia
    private TextView textView;
    private ImageView imageView;
    private ProgressBar progressBar;

@Override
protected void onCreate(Bundle savedInstanceState){
    super.onCreate(savedInstanceState);
    setContentView(R.layout.numeros);

    //ahora vinculamos los ID de objetos
    textView=findViewById(R.id.textoemer);
    imageView=findViewById(R.id.imagenemer);
    progressBar=findViewById(R.id.barraprogreso);

    //creamos y ejecutamos el Thread para vincular carga de objetos
Thread thread = new Thread(new Runnable() {
    @Override
    public void run() {
    // agregamos pausa de 5 seg.
        try{
            Thread.sleep(5000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }

        // aca actualizamso la interfaz del usuario desde el thread
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //ocultamos la barra de progreso
                progressBar.setVisibility(View.GONE);
                //actualizamos el texto de la imagen
                textView.setText("Contactos Actualizados");
                //hacemos visible nuestra imagen
                imageView.setVisibility(View.VISIBLE);
                imageView.setImageResource(R.drawable.numerosemer);
            }
        });
    }
});
thread.start();


}

}
