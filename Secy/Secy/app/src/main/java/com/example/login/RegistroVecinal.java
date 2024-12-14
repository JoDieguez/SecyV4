package com.example.login;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.TextView;
import android.graphics.Color;
import java.util.ArrayList;
import android.util.Patterns;

public class RegistroVecinal extends AppCompatActivity {

    Spinner spSpinner;
    String[] comunas = new String[]{"Quilicura", "Maipu", "Puente Alto", "Renca"};
    EditText edtRut, edtDir, edtNom, edtTel, edtCorreo;
    ListView lista;
    ArrayAdapter<String> listAdapter; // Adaptador de la lista
    ArrayList<String> arr; // Cambia de String[] a ArrayList para poder modificarlo

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bbddusuarios);

        // Definir los campos del formulario
        edtRut = findViewById(R.id.edtRut);
        edtNom = findViewById(R.id.edtNom);
        edtDir = findViewById(R.id.edtDir);
        edtTel = findViewById(R.id.edtTel);
        edtCorreo = findViewById(R.id.edtCorreo);
        spSpinner = findViewById(R.id.spSpinner);
        lista = findViewById(R.id.lstLista);

        // Configurar el adaptador del Spinner con el color personalizado
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, comunas) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView textView = (TextView) view.findViewById(android.R.id.text1);
                textView.setTextColor(Color.parseColor("#FFC107")); // Cambia el color aquí
                return view;
            }

            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView textView = (TextView) view.findViewById(android.R.id.text1);
                textView.setTextColor(Color.parseColor("#FFC107")); // Cambia el color aquí también para la lista desplegable
                return view;
            }
        };
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spSpinner.setAdapter(spinnerAdapter);

        // Inicializar el array y el adaptador de la lista
        arr = new ArrayList<>();
        listAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arr) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView textView = view.findViewById(android.R.id.text1);
                textView.setTextColor(Color.parseColor("#FFC107")); // Cambia el color aquí
                return view;
            }
        };
        lista.setAdapter(listAdapter);

        // Cargar la lista de datos
        cargarLista();
    }

    // Método para cargar la lista
    public void cargarLista() {
        DataHelper dh = new DataHelper(this, "vecinos.db", null, 1);
        SQLiteDatabase bd = dh.getWritableDatabase();

        Cursor c = bd.rawQuery("SELECT rut, nombre, direccion, Teléfono, Correo, comuna FROM vecinos", null);

        // Limpiar el array antes de agregar nuevos datos
        arr.clear();

        if (c.moveToFirst()) {
            do {
                String linea =
                        " | " + c.getInt(0) +
                        " | " + c.getString(1) +
                        " | " + c.getString(2) +
                        " | " + c.getString(3) +
                        " | " + c.getString(4) +
                        " | " + c.getString(5) + " | ";
                arr.add(linea);
            } while (c.moveToNext());
        }

        // Cerrar el cursor y la base de datos
        c.close();
        bd.close();

        // Notificar al adaptador que los datos han cambiado
        listAdapter.notifyDataSetChanged();
    }

    // Método para eliminar un registro
    public void onClickEliminar(View view) {
        DataHelper dh = new DataHelper(this, "vecinos.db", null, 1);
        SQLiteDatabase bd = dh.getWritableDatabase();

        String rut = edtRut.getText().toString();

        if (!rut.isEmpty()) {
            int deletedRows = bd.delete("vecinos", "rut=?", new String[]{rut});
            bd.close();

            if (deletedRows > 0) {
                Toast.makeText(this, "Registro eliminado", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "No se encontró el registro", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(this, "Ingrese un RUT válido para eliminar", Toast.LENGTH_LONG).show();
        }

        // Recargar la lista después de eliminar y actualizar el adaptador
        cargarLista();
    }
    private void limpiarCampos() {
        edtRut.setText("");
        edtNom.setText("");
        edtDir.setText("");
        edtTel.setText("");
        edtCorreo.setText("");
        spSpinner.setSelection(0); // Reinicia el Spinner al primer elemento
    }
    // Método para agregar un registro (mismo que antes)
    public void onClickAgregar(View view) {
        String correo = edtCorreo.getText().toString();
        if (!esCorreoValido(correo)) {
            Toast.makeText(this, "Ingrese un correo electrónico válido", Toast.LENGTH_SHORT).show();
            return; // Detener el proceso si el correo no es válido
        }

        DataHelper dh = new DataHelper(this, "vecinos.db", null, 1);
        SQLiteDatabase bd = dh.getWritableDatabase();

        ContentValues reg = new ContentValues();
        reg.put("rut", edtRut.getText().toString());
        reg.put("nombre", edtNom.getText().toString());
        reg.put("direccion", edtDir.getText().toString());
        reg.put("Teléfono", edtTel.getText().toString());
        reg.put("Correo", edtCorreo.getText().toString());
        reg.put("comuna", spSpinner.getSelectedItem().toString());

        long respuesta = bd.insert("vecinos", null, reg);
        bd.close();

        if (respuesta == -1) {
            Toast.makeText(this, "No Agregado", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Agregado", Toast.LENGTH_LONG).show();
            limpiarCampos();
        }

        cargarLista();
    }

    public void onClickModificar(View view) {
        // Crear o abrir la base de datos
        DataHelper dh = new DataHelper(this, "vecinos.db", null, 1);
        SQLiteDatabase bd = dh.getWritableDatabase();

        // Obtener el RUT ingresado en el campo de texto para identificar el registro a modificar
        String rut = edtRut.getText().toString();

        // Verificar que el campo de RUT no esté vacío
        if (!rut.isEmpty()) {
            // Crear un contenedor para los nuevos valores
            ContentValues valores = new ContentValues();
            valores.put("nombre", edtNom.getText().toString());
            valores.put("direccion", edtDir.getText().toString());
            valores.put("Teléfono", edtTel.getText().toString());
            valores.put("Correo", edtCorreo.getText().toString());
            valores.put("comuna", spSpinner.getSelectedItem().toString());

            // Ejecutar la actualización del registro en la tabla
            int updatedRows = bd.update("vecinos", valores, "rut=?", new String[]{rut});
            bd.close();

            // Verificar si la actualización fue exitosa
            if (updatedRows > 0) {
                Toast.makeText(this, "Registro actualizado", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "No se encontró el registro para actualizar", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(this, "Ingrese un RUT válido para modificar", Toast.LENGTH_LONG).show();
        }

        // Recargar la lista después de modificar
        cargarLista();
    }

    public void onClickLimpiar(View view) {
        limpiarCampos(); // Llamar al método de limpieza
        Toast.makeText(this, "Datos limpiados", Toast.LENGTH_SHORT).show();
    }
    private boolean esCorreoValido(String correo) {
        return !correo.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(correo).matches();
    }

}
