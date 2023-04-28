package com.example.eatfit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class OlvidoDeContrasena extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_olvido_de_contrasena);

        EatFit eatFit =new EatFit(this);
        Logica logica=new Logica(eatFit);

        Button cambiarPassword=(Button) findViewById(R.id.buttonConfirmarCambioPassword);
        EditText usuario=(EditText) findViewById(R.id.editTextUsuarioCambiarContraseña);
        //EditText nuevaPwd=(EditText) findViewById(R.id.editTextCambiarContraseña);

        cambiarPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String[] numTelefono={""};

                if(usuario.getText().toString().contains("@gmail.com")){

                    if(logica.existeUsuarioPorEmail(usuario.getText().toString())){
                        SQLiteDatabase db = eatFit.getReadableDatabase();

                        String[] projection = {
                                Usuarios.Table.COLUMN_NAME_NumTelefono
                        };

                        String selection = Usuarios.Table.COLUMN_NAME_Email + " = ? ";
                        String[] selectionArgs = { usuario.getText().toString() };


                        Cursor cursor = db.query(
                                Usuarios.Table.TABLE_NAME,   // The table to query
                                projection,             // The array of columns to return (pass null to get all)
                                selection,              // The columns for the WHERE clause
                                selectionArgs,          // The values for the WHERE clause
                                null,                   // don't group the rows
                                null,                   // don't filter by row groups
                                null                    // The sort order
                        );

                        if(cursor.moveToNext()) {
                            numTelefono[0]=cursor.getString(cursor.getColumnIndexOrThrow(Usuarios.Table.COLUMN_NAME_NumTelefono));
                        }

                        SmsManager smsmanager=SmsManager.getDefault();
                        smsmanager.sendTextMessage(numTelefono[0],null,"Mensaje de prueba",null, null);
                    }else{
                        Toast.makeText(OlvidoDeContrasena.this,"Usuario No Existente",Toast.LENGTH_LONG).show();
                    }

                    //Toast.makeText(OlvidoDeContrasena.this,"Es correo",Toast.LENGTH_LONG).show();

                }else{

                    if(logica.existeUsuarioPorNick(usuario.getText().toString())){


                        SQLiteDatabase db = eatFit.getReadableDatabase();

                        String[] projection = {
                                Usuarios.Table.COLUMN_NAME_NumTelefono
                        };

                        String selection = Usuarios.Table.COLUMN_NAME_Nick + " = ? ";
                        String[] selectionArgs = { usuario.getText().toString() };


                        Cursor cursor = db.query(
                                Usuarios.Table.TABLE_NAME,   // The table to query
                                projection,             // The array of columns to return (pass null to get all)
                                selection,              // The columns for the WHERE clause
                                selectionArgs,          // The values for the WHERE clause
                                null,                   // don't group the rows
                                null,                   // don't filter by row groups
                                null                    // The sort order
                        );

                        if(cursor.moveToNext()) {
                            numTelefono[0]=cursor.getString(cursor.getColumnIndexOrThrow(Usuarios.Table.COLUMN_NAME_NumTelefono));
                        }

                        SmsManager smsmanager=SmsManager.getDefault();
                        smsmanager.sendTextMessage(numTelefono[0],null,"Mensaje de prueba",null, null);

                        /*Intent intent = new Intent(Intent.ACTION_SEND);

                        // Always use string resources for UI text.
                        // Create intent to show chooser
                        Intent chooser = Intent.createChooser(intent, "Escoga aplicacion");

                        // Verify the intent will resolve to at least one activity
                        if (intent.resolveActivity(getPackageManager()) != null) {
                            startActivity(chooser);
                        }*/

                        //Toast.makeText(OlvidoDeContrasena.this,numTelefono[0],Toast.LENGTH_LONG).show();

                    }else{
                        Toast.makeText(OlvidoDeContrasena.this,"Usuario No Existente",Toast.LENGTH_LONG).show();
                    }

                }

            }
        });
    }

}