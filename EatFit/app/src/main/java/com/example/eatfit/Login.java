package com.example.eatfit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;

public class Login extends AppCompatActivity {

    static EditText nick;
    static EditText pwd;
    static EatFit eatFit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        nick=(EditText) findViewById(R.id.editTextUsuario);
        pwd=(EditText) findViewById(R.id.editTextPwd);

        eatFit =new EatFit(this);

        if(!new File("/data/data/com.example.eatfit/databases/eatfit").exists()){
            introduceRutinas();
        }

        Button botonInicioSesion=(Button)findViewById(R.id.buttonInicioSesion);
        botonInicioSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nickString=nick.getText().toString();
                String pwdString=pwd.getText().toString();
                SQLiteDatabase db = eatFit.getReadableDatabase();

                // Define a projection that specifies which columns from the database
                // you will actually use after this query.
                String[] projection = {
                        Usuarios.Table.COLUMN_NAME_NumUsuario,
                        Usuarios.Table.COLUMN_NAME_Nick,
                        Usuarios.Table.COLUMN_NAME_Password,
                        Usuarios.Table.COLUMN_NAME_seHaLogeado
                };

                // Filter results WHERE "title" = 'My Title'
                String selection = Usuarios.Table.COLUMN_NAME_Nick + " = ? AND "+ Usuarios.Table.COLUMN_NAME_Password + " = ? ";
                String[] selectionArgs = { nickString , pwdString };


                Cursor cursor = db.query(
                        Usuarios.Table.TABLE_NAME,   // The table to query
                        projection,             // The array of columns to return (pass null to get all)
                        selection,              // The columns for the WHERE clause
                        selectionArgs,          // The values for the WHERE clause
                        null,                   // don't group the rows
                        null,                   // don't filter by row groups
                        null                    // The sort order
                );

                boolean existe=false;
                int[] seHaLogeado={0};
                if(cursor.moveToNext()) {
                    existe=true;
                    seHaLogeado[0]=cursor.getInt(cursor.getColumnIndexOrThrow(Usuarios.Table.COLUMN_NAME_seHaLogeado));
                }

                if (existe){
                    if(seHaLogeado[0]==0){
                        Intent intent = new Intent(Login.this, Cuestionario.class);
                        startActivity(intent);
                    }else{
                        Intent intent = new Intent(Login.this, MenuPrincipal.class);
                        startActivity(intent);
                    }
                }else{
                    Context context = getApplicationContext();
                    CharSequence text = "Usuario No Existente";
                    int duration = Toast.LENGTH_LONG;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }

            }
        });

        Button botonCreateAccount=(Button) findViewById(R.id.buttonCreateAccount);
        botonCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, CreateAccount.class);
                startActivity(intent);
            }
        });





    }

    public String dameNickLogeado(){
        String nickString=nick.getText().toString();
        String pwdString=pwd.getText().toString();
        SQLiteDatabase db = eatFit.getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                Usuarios.Table.COLUMN_NAME_NumUsuario,
                Usuarios.Table.COLUMN_NAME_Nick,
                Usuarios.Table.COLUMN_NAME_Password,
                Usuarios.Table.COLUMN_NAME_seHaLogeado
        };

        // Filter results WHERE "title" = 'My Title'
        String selection = Usuarios.Table.COLUMN_NAME_Nick + " = ? AND "+ Usuarios.Table.COLUMN_NAME_Password + " = ? ";
        String[] selectionArgs = { nickString , pwdString };


        Cursor cursor = db.query(
                Usuarios.Table.TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                selectionArgs,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                null                    // The sort order
        );

        boolean existe=false;
        int[] seHaLogeado={0};
        if(cursor.moveToNext()) {
            existe=true;
            seHaLogeado[0]=cursor.getInt(cursor.getColumnIndexOrThrow(Usuarios.Table.COLUMN_NAME_seHaLogeado));
        }
        if(existe){
            return nickString;
        }
        return null;
    }

    public static void introduceRutinas(){
        SQLiteDatabase db = eatFit.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values1 = new ContentValues();
        values1.put(Rutinas.Table.COLUMN_NAME_NumRutina, "1");
        values1.put(Rutinas.Table.COLUMN_NAME_DescripcionRutina, "Rutina 1");
        // Insert the new row, returning the primary key value of the new row
        long newRowId1 = db.insert(Rutinas.Table.TABLE_NAME, null, values1);

        // Create a new map of values, where column names are the keys
        ContentValues values2 = new ContentValues();
        values2.put(Rutinas.Table.COLUMN_NAME_NumRutina, "2");
        values2.put(Rutinas.Table.COLUMN_NAME_DescripcionRutina, "Rutina 2");
        // Insert the new row, returning the primary key value of the new row
        long newRowId2 = db.insert(Rutinas.Table.TABLE_NAME, null, values2);

        // Create a new map of values, where column names are the keys
        ContentValues values3 = new ContentValues();
        values3.put(Rutinas.Table.COLUMN_NAME_NumRutina, "3");
        values3.put(Rutinas.Table.COLUMN_NAME_DescripcionRutina, "Rutina 3");
        // Insert the new row, returning the primary key value of the new row
        long newRowId3 = db.insert(Rutinas.Table.TABLE_NAME, null, values3);

        // Create a new map of values, where column names are the keys
        ContentValues values4 = new ContentValues();
        values4.put(Rutinas.Table.COLUMN_NAME_NumRutina, "4");
        values4.put(Rutinas.Table.COLUMN_NAME_DescripcionRutina, "Rutina 4");
        // Insert the new row, returning the primary key value of the new row
        long newRowId4 = db.insert(Rutinas.Table.TABLE_NAME, null, values4);

        // Create a new map of values, where column names are the keys
        ContentValues values5 = new ContentValues();
        values5.put(Rutinas.Table.COLUMN_NAME_NumRutina, "5");
        values5.put(Rutinas.Table.COLUMN_NAME_DescripcionRutina, "Rutina 5");
        // Insert the new row, returning the primary key value of the new row
        long newRowId5 = db.insert(Rutinas.Table.TABLE_NAME, null, values5);
    }


}