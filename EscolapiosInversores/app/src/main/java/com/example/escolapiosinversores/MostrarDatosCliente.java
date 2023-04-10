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

public class MostrarDatosCliente extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_datos_cliente);

        TextView textViewDatos=findViewById(R.id.textViewDatos);
        Button botonVolver=findViewById(R.id.buttonVolver);

        FeedReaderDbHelper finalDbHelper=new FeedReaderDbHelper(this);

        int contCursor=0;
        SQLiteDatabase db = finalDbHelper.getReadableDatabase();

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
        while(cursor.moveToNext()) {
            //long id=cursor.getLong(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry._ID));
            String nombre=cursor.getString(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_Nombre));
            String domicilio=cursor.getString(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_Domicilio));
            String telefono=cursor.getString(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_Telefono));
            Double saldo=cursor.getDouble(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_Saldo));
            //Integer dato2=cursor.getInt(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_AccionRealizada));
            itemIds.add("nombre:" + nombre+"\n"+"domicilio:" + domicilio+"\n"+"telefono:" + telefono+"\n"+"saldo:" + saldo+"€");
            contCursor++;
        }
        cursor.close();
        textViewDatos.setText("");
        textViewDatos.setText(textViewDatos.getText()+""+itemIds.get(0)+"\n");

        botonVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MostrarDatosCliente.this, MenuAccionesCliente.class);
                startActivity(intent);
            }
        });
    }
}