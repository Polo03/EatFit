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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MostrarAcciones extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_acciones);

        Button buttonVolver= (Button) findViewById(R.id.buttonVuelve);


        TextView textViewDatos=(TextView) findViewById(R.id.textViewAcciones);

        FeedReaderDbHelper finalDbHelper=new FeedReaderDbHelper(this);

        SQLiteDatabase db = finalDbHelper.getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                BaseColumns._ID,
                FeedReaderContract.FeedEntry.COLUMN_NAME_Nombre,
                FeedReaderContract.FeedEntry.COLUMN_NAME_Domicilio,
                FeedReaderContract.FeedEntry.COLUMN_NAME_Telefono,
                FeedReaderContract.FeedEntry.COLUMN_NAME_NombreAccion,
                FeedReaderContract.FeedEntry.COLUMN_NAME_Saldo,
                //FeedReaderContract.FeedEntry.COLUMN_NAME_AccionRealizada
                FeedReaderContract.FeedEntry.COLUMN_NAME_CantidadAcciones
        };

        // Filter results WHERE "title" = 'My Title'
        String selection = FeedReaderContract.FeedEntry.COLUMN_NAME_AccionRealizada + " = ?";
        String[] selectionArgs = { "En Actualidad" };


        Cursor cursor = db.query(
                FeedReaderContract.FeedEntry.TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                selectionArgs,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                null              // The sort order
        );

        List itemIds = new ArrayList<>();
        //HashSet<String> itemIds=new HashSet<>();
        while(cursor.moveToNext()) {
            //long id=cursor.getLong(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry._ID));
            //String nombre=cursor.getString(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_Nombre));
            //String domicilio=cursor.getString(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_Domicilio));
            //String telefono=cursor.getString(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_Telefono));
            //Double saldo=cursor.getDouble(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_Saldo));
            //Integer dato2=cursor.getInt(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_AccionRealizada));
            String nombreAccion=cursor.getString(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_NombreAccion));
            Integer numAcciones=cursor.getInt(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_CantidadAcciones));
            itemIds.add(nombreAccion+"   "+numAcciones);

        }
        cursor.close();
        textViewDatos.setText("");
        for(int i=0;i<itemIds.size();i++){
            textViewDatos.setText(textViewDatos.getText()+""+itemIds.get(i)+"\n");
        }

        buttonVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MostrarAcciones.this, MenuAccionesCliente.class);
                startActivity(intent);
            }
        });
    }
}