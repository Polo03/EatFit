package com.example.escolapiosinversores;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

public class Login extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        TextView user = findViewById(R.id.editTextTextEmailAddress);
        TextView pass = findViewById(R.id.editTextTextPassword);



        FeedReaderDbHelper finalDbHelper=new FeedReaderDbHelper(this);
        Button boton = (Button) findViewById(R.id.button);
        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String usuario = user.getText().toString();
                String contra = pass.getText().toString();
                Intent intent2 = new Intent(Login.this, MenuAccionesCliente.class);



                if(usuario.equals("admin") && contra.equals("admin") ) {
                    if(new File("/data/data/com.example.escolapiosinversores/databases/datos").exists()==false) {
                        FeedReaderDbHelper finalDbHelper1 = finalDbHelper;
                        // Gets the data repository in write mode
                        SQLiteDatabase db = finalDbHelper1.getWritableDatabase();
                        //finalDbHelper.onUpgrade(db,0,1);
                        // Create a new map of values, where column names are the keys
                        ContentValues values = new ContentValues();
                        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_Nombre, ("Admin"));
                        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_Domicilio, ("C/ Me cagon el Android"));
                        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_Telefono, ("619 27 66 45"));
                        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_NombreAccion,("Ninguna"));
                        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_Saldo, 500.0);
                        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_AccionRealizada, ("Ninguna"));

                        // Insert the new row, returning the primary key value of the new row
                        long newRowId = db.insert(FeedReaderContract.FeedEntry.TABLE_NAME, null, values);
                    }
                    startActivity(intent2);


                }else{
                    Context context = getApplicationContext();
                    CharSequence text = "TE HAS EQUIVOCADO DE USUARIO O CONTRASEÑA";
                    int duration = Toast.LENGTH_LONG;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();

                }


            }
        });

    }


}