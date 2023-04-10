package com.example.escolapiosinversores;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

public class ModificarDatosCliente extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar_datos_cliente);

        Button botonActualizar=findViewById(R.id.buttonModificar);
        EditText textoDomicilio=findViewById(R.id.editTextDomicilio);
        EditText textoTelefono=findViewById(R.id.editTextTelefono);

        FeedReaderDbHelper finalDbHelper=new FeedReaderDbHelper(this);

        botonActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = finalDbHelper.getWritableDatabase();

                // Define a projection that specifies which columns from the database
                // you will actually use after this query.
                String[] projection = {
                        BaseColumns._ID,
                        FeedReaderContract.FeedEntry.COLUMN_NAME_Nombre,
                        FeedReaderContract.FeedEntry.COLUMN_NAME_Domicilio,
                        FeedReaderContract.FeedEntry.COLUMN_NAME_Telefono,
                        FeedReaderContract.FeedEntry.COLUMN_NAME_Saldo,
                        //FeedReaderContract.FeedEntry.COLUMN_NAME_AccionRealizada
                };


                Cursor cursor = db.query(
                        FeedReaderContract.FeedEntry.TABLE_NAME,   // The table to query
                        projection,             // The array of columns to return (pass null to get all)
                        null,              // The columns for the WHERE clause
                        null,          // The values for the WHERE clause
                        null,                   // don't group the rows
                        null,                   // don't filter by row groups
                        null              // The sort order
                );

                List itemIds = new ArrayList<>();
                long id=0;
                while(cursor.moveToNext()) {
                    id=cursor.getLong(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry._ID));
                    String nombre=cursor.getString(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_Nombre));
                    String domicilio=cursor.getString(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_Domicilio));
                    String telefono=cursor.getString(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_Telefono));
                    Double saldo=cursor.getDouble(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_Saldo));
                    //Integer dato2=cursor.getInt(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_AccionRealizada));
                }
                cursor.close();

                // New value for one column
                ContentValues values = new ContentValues();
                values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_Domicilio, textoDomicilio.getText().toString());
                values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_Telefono,textoTelefono.getText().toString());
                // Which row to update, based on the title
                String selection = FeedReaderContract.FeedEntry._ID + " LIKE ?";
                String[] selectionArgs = {"1"};

                int count = db.update(
                        FeedReaderContract.FeedEntry.TABLE_NAME,
                        values,
                        selection,
                        selectionArgs);

                Intent intent = new Intent(ModificarDatosCliente.this, MenuAccionesCliente.class);
                startActivity(intent);
            }
        });


    }
}