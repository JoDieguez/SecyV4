package com.example.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.snackbar.Snackbar;

public class BloqueoAcceso extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bloqueo);
    }

    public void bloqueoAccesos(View view) {
        Snackbar.make(view, "¡Accesos Bloqueados!", Snackbar.LENGTH_LONG)
                .setAction("Entendido", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                }).show();
    }

    public void desbloqueoAcceso(View view) {
        Snackbar.make(view, "¡Accesos Desbloqueados!", Snackbar.LENGTH_LONG)
                .setAction("Entendido", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                }).show();
    }

    public void configAdicional(View view) {
        Intent intent = new Intent(this, CnfgAdicional.class);
        startActivity(intent);
    }


}
