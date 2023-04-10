package com.example.eatfit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class MenuPrincipal extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);

        Login l=new Login();

        EatFit eatFit=new EatFit(this);

        SQLiteDatabase db = eatFit.getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                Usuarios.Table.COLUMN_NAME_Nick,
                Usuarios.Table.COLUMN_NAME_NumRutina
        };

        // Filter results WHERE "title" = 'My Title'
        String selection = Usuarios.Table.COLUMN_NAME_Nick + " = ?";
        String[] selectionArgs = { l.dameNickLogeado() };


        Cursor cursor = db.query(
                Usuarios.Table.TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                selectionArgs,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                null               // The sort order
        );

        int[] numRutina={0};
        if(cursor.moveToNext()) {
            numRutina[0]=cursor.getInt(cursor.getColumnIndexOrThrow(Usuarios.Table.COLUMN_NAME_NumRutina));
        }

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection2 = {
                Rutinas.Table.COLUMN_NAME_DescripcionRutina
        };

        // Filter results WHERE "title" = 'My Title'
        String selection2 = Rutinas.Table.COLUMN_NAME_NumRutina + " = ?";
        String[] selectionArgs2 = { numRutina[0]+"" };


        Cursor cursor2 = db.query(
                Rutinas.Table.TABLE_NAME,   // The table to query
                projection2,             // The array of columns to return (pass null to get all)
                selection2,              // The columns for the WHERE clause
                selectionArgs2,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                null               // The sort order
        );

        String[] textoACambiar={""};

        if(cursor2.moveToNext()) {
            textoACambiar[0]=cursor2.getString(cursor2.getColumnIndexOrThrow(Rutinas.Table.COLUMN_NAME_DescripcionRutina));
        }

        TextView texto=(TextView) findViewById(R.id.textViewRutina);

        texto.setText(textoACambiar[0]);

        ImageButton botonSalir=(ImageButton) findViewById(R.id.buttonSalir);

        botonSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuPrincipal.this, Login.class);
                startActivity(intent);
            }
        });
    }
}