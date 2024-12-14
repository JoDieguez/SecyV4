package com.example.login;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import org.jetbrains.annotations.Nullable;
public class DataHelper extends SQLiteOpenHelper{


    public DataHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory cursorFactory, int version){
        super(context,name,cursorFactory,version);
    }
    // metodo onCreate para crear lña base de datos
    @Override
    public void onCreate(SQLiteDatabase db){
        // ejecuto una consulta para crear la tbla
        db.execSQL("CREATE TABLE vecinos(rut INTEGER PRIMARY KEY, nombre TEXT, direccion TEXT, Teléfono NUM, Correo TEXT, comuna TEXT)");
    }

    //metodo  unUpgrade para actualizar la tabla de BD

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS vecinos");
        db.execSQL("CREATE TABLE vecinos(rut INTEGER PRIMARY KEY, nombre TEXT, direccion TEXT, Teléfono NUM, Correo TEXT, comuna TEXT)");
    }
}


