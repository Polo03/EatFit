package com.example.escolapiosinversores;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class historialMovimientos extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial_movimientos);

        Button botonVolver=findViewById(R.id.buttonVolverHistorial);
        TextView textViewCompradas=findViewById(R.id.textViewCompradas);
        TextView textViewVendidas=findViewById(R.id.textViewVendidas);

        FeedReaderDbHelper finalDbHelper=new FeedReaderDbHelper(this);

        SQLiteDatabase dbCompradas = finalDbHelper.getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection3 = {
                BaseColumns._ID,
                FeedReaderContract.FeedEntry.COLUMN_NAME_Nombre,
                FeedReaderContract.FeedEntry.COLUMN_NAME_Domicilio,
                FeedReaderContract.FeedEntry.COLUMN_NAME_Telefono,
                FeedReaderContract.FeedEntry.COLUMN_NAME_Saldo,
                FeedReaderContract.FeedEntry.COLUMN_NAME_NombreAccion,
                FeedReaderContract.FeedEntry.COLUMN_NAME_AccionRealizada,
                FeedReaderContract.FeedEntry.COLUMN_NAME_CantidadAcciones
        };


        // Filter results WHERE "title" = 'My Title'
        String selection2 = FeedReaderContract.FeedEntry.COLUMN_NAME_AccionRealizada + " = ? ";
        String[] selectionArgs2 = { "Comprado"};



        Cursor cursor3 = dbCompradas.query(
                FeedReaderContract.FeedEntry.TABLE_NAME,   // The table to query
                projection3,             // The array of columns to return (pass null to get all)
                selection2,              // The columns for the WHERE clause
                selectionArgs2,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                null              // The sort order
        );

        ArrayList<String> accionesCompradas=new ArrayList<>();

        while (cursor3.moveToNext()){
           String nombreAccion=cursor3.getString(cursor3.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_NombreAccion));
           Integer cantidadAccion=cursor3.getInt(cursor3.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_CantidadAcciones));
           accionesCompradas.add(nombreAccion+" "+cantidadAccion);
        }
        cursor3.close();

        //////////////////////////////////////////////////////////////////

        SQLiteDatabase dbVendidas = finalDbHelper.getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                BaseColumns._ID,
                FeedReaderContract.FeedEntry.COLUMN_NAME_Nombre,
                FeedReaderContract.FeedEntry.COLUMN_NAME_Domicilio,
                FeedReaderContract.FeedEntry.COLUMN_NAME_Telefono,
                FeedReaderContract.FeedEntry.COLUMN_NAME_Saldo,
                FeedReaderContract.FeedEntry.COLUMN_NAME_NombreAccion,
                FeedReaderContract.FeedEntry.COLUMN_NAME_AccionRealizada,
                FeedReaderContract.FeedEntry.COLUMN_NAME_CantidadAcciones
        };


        // Filter results WHERE "title" = 'My Title'
        String selection = FeedReaderContract.FeedEntry.COLUMN_NAME_AccionRealizada + " = ? ";
        String[] selectionArgs = { "Vendido"};



        Cursor cursor2 = dbVendidas.query(
                FeedReaderContract.FeedEntry.TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                selectionArgs,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                null              // The sort order
        );

        ArrayList<String> accionesVendidas=new ArrayList<>();

        while (cursor2.moveToNext()){
            String nombreAccion=cursor2.getString(cursor2.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_NombreAccion));
            Integer cantidadAccion=cursor2.getInt(cursor2.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_CantidadAcciones));
            accionesVendidas.add(nombreAccion+" "+cantidadAccion);
        }
        cursor2.close();
        textViewCompradas.setText("");
        textViewVendidas.setText("");
        for(String accion: accionesCompradas){
            textViewCompradas.setText(textViewCompradas.getText().toString()+""+accion+"\n");
        }
        for(String accion: accionesVendidas){
            textViewVendidas.setText(textViewVendidas.getText().toString()+""+accion+"\n");
        }


        botonVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(historialMovimientos.this, MenuAccionesCliente.class);
                startActivity(intent);
            }
        });


    }
}