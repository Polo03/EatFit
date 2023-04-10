package com.example.escolapiosinversores;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class ComprarAcciones extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comprar_acciones);

        Button botonVolver=findViewById(R.id.buttonVolver3);


        FeedReaderDbHelper finalDbHelper=new FeedReaderDbHelper(this);

        final ListView listado=(ListView) findViewById(R.id.listView);
        final String[] values=new String[] {"Microsoft","PepePhone","Movistar"};
        ArrayAdapter<String> adaptador=new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,values);
        listado.setAdapter(adaptador);
        String[] accion=new String[1];
        ArrayList<String> nombres=new ArrayList<>();
        ArrayList<String> domicilios=new ArrayList<>();
        ArrayList<String> telefonos=new ArrayList<>();
        ArrayList<Double> saldos=new ArrayList<>();
        ArrayList<String> nombresAccion=new ArrayList<>();
        Random ale=new Random();
        int[] precioAcciones=new int[3];

        for(int i=0;i<3;i++){
            precioAcciones[i]=ale.nextInt(50)+50;
        }

        listado.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String temp = (String) adapterView.getAdapter().getItem(i);
                //accion[0] =temp;


                SQLiteDatabase db = finalDbHelper.getReadableDatabase();

                // Define a projection that specifies which columns from the database
                // you will actually use after this query.
                String[] projection = {
                        BaseColumns._ID,
                        FeedReaderContract.FeedEntry.COLUMN_NAME_Nombre,
                        FeedReaderContract.FeedEntry.COLUMN_NAME_Domicilio,
                        FeedReaderContract.FeedEntry.COLUMN_NAME_Telefono,
                        FeedReaderContract.FeedEntry.COLUMN_NAME_Saldo,
                        FeedReaderContract.FeedEntry.COLUMN_NAME_NombreAccion,
                        FeedReaderContract.FeedEntry.COLUMN_NAME_AccionRealizada
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
                //List itemIds = new ArrayList<>();
                int cont=0;
                while(cursor.moveToNext()) {
                    //long id=cursor.getLong(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry._ID));
                    String nombre=cursor.getString(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_Nombre));
                    String domicilio=cursor.getString(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_Domicilio));
                    String telefono=cursor.getString(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_Telefono));
                    Double saldo=cursor.getDouble(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_Saldo));
                    String nombreAccion=cursor.getString(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_NombreAccion));
                    //Integer dato2=cursor.getInt(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_AccionRealizada));
                    //itemIds.add("nombre:" + nombre+"\n"+"domicilio:" + domicilio+"\n"+"telefono:" + telefono+"\n"+"saldo:" + saldo+"€");
                    if(cont==0){
                        nombres.add(nombre);
                        domicilios.add(domicilio);
                        telefonos.add(telefono);
                        saldos.add(saldo);
                        nombresAccion.add(nombreAccion);
                    }

                }
                cursor.close();

                boolean existe=false;
                for(int j=0;j<nombresAccion.size();j++){
                    if(nombresAccion.get(j).equals(temp)){
                        existe=true;
                    }
                }
                if((saldos.get(0)-precioAcciones[i])>=0){
                    if(existe){

                        SQLiteDatabase dbSelect = finalDbHelper.getReadableDatabase();

                        // Define a projection that specifies which columns from the database
                        // you will actually use after this query.
                        String[] projection2 = {
                                BaseColumns._ID,
                                FeedReaderContract.FeedEntry.COLUMN_NAME_NombreAccion,
                                FeedReaderContract.FeedEntry.COLUMN_NAME_AccionRealizada,
                                FeedReaderContract.FeedEntry.COLUMN_NAME_CantidadAcciones
                        };

                        // Filter results WHERE "title" = 'My Title'
                        String selection = FeedReaderContract.FeedEntry.COLUMN_NAME_NombreAccion + " LIKE ? AND "+FeedReaderContract.FeedEntry.COLUMN_NAME_AccionRealizada+" LIKE ?";
                        String[] selectionArgs = { temp , "Comprado"};


                        Cursor cursor2 = db.query(
                                FeedReaderContract.FeedEntry.TABLE_NAME,   // The table to query
                                projection2,             // The array of columns to return (pass null to get all)
                                selection,              // The columns for the WHERE clause
                                selectionArgs,          // The values for the WHERE clause
                                null,                   // don't group the rows
                                null,                   // don't filter by row groups
                                null               // The sort order
                        );



                        ArrayList<Integer> cantidadAcciones=new ArrayList<>();

                        while(cursor2.moveToNext()) {
                            cantidadAcciones.add(cursor2.getInt(cursor2.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_CantidadAcciones)));
                        }
                        cursor2.close();

                        //////////////////////////////////////////////////////////////////////
                        SQLiteDatabase dbSelect2 = finalDbHelper.getReadableDatabase();

                        // Define a projection that specifies which columns from the database
                        // you will actually use after this query.
                        String[] projection3 = {
                                BaseColumns._ID,
                                FeedReaderContract.FeedEntry.COLUMN_NAME_NombreAccion,
                                FeedReaderContract.FeedEntry.COLUMN_NAME_AccionRealizada,
                                FeedReaderContract.FeedEntry.COLUMN_NAME_CantidadAcciones
                        };

                        // Filter results WHERE "title" = 'My Title'
                        String selection2 = FeedReaderContract.FeedEntry.COLUMN_NAME_NombreAccion + " LIKE ? AND "+FeedReaderContract.FeedEntry.COLUMN_NAME_AccionRealizada+" LIKE ?";
                        String[] selectionArgs2 = { temp , "En Actualidad"};


                        Cursor cursor4 = db.query(
                                FeedReaderContract.FeedEntry.TABLE_NAME,   // The table to query
                                projection3,             // The array of columns to return (pass null to get all)
                                selection2,              // The columns for the WHERE clause
                                selectionArgs2,          // The values for the WHERE clause
                                null,                   // don't group the rows
                                null,                   // don't filter by row groups
                                null               // The sort order
                        );

                        ArrayList<Integer> cantidadAcciones2=new ArrayList<>();

                        while(cursor4.moveToNext()) {
                            cantidadAcciones2.add(cursor4.getInt(cursor4.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_CantidadAcciones)));
                        }
                        cursor4.close();

                        SQLiteDatabase dbUpdate = finalDbHelper.getWritableDatabase();

                        // New value for one column

                        ContentValues values = new ContentValues();
                        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_CantidadAcciones, cantidadAcciones.get(0)+1);
                        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_Domicilio,domicilios.get(0));
                        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_Telefono,telefonos.get(0));
                        // Which row to update, based on the title
                        String selection3 = FeedReaderContract.FeedEntry.COLUMN_NAME_NombreAccion + " LIKE ? AND "+FeedReaderContract.FeedEntry.COLUMN_NAME_AccionRealizada+" LIKE ?";
                        String[] selectionArgs3 = { temp , "Comprado"};

                        int count = dbUpdate.update(
                                FeedReaderContract.FeedEntry.TABLE_NAME,
                                values,
                                selection3,
                                selectionArgs3);




                        SQLiteDatabase dbUpdate2 = finalDbHelper.getWritableDatabase();

                        // New value for one column

                        ContentValues values2 = new ContentValues();
                        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_CantidadAcciones, cantidadAcciones2.get(0)+1);
                        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_Domicilio,domicilios.get(0));
                        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_Telefono,telefonos.get(0));
                        // Which row to update, based on the title
                        String selection4 = FeedReaderContract.FeedEntry.COLUMN_NAME_NombreAccion + " LIKE ? AND "+FeedReaderContract.FeedEntry.COLUMN_NAME_AccionRealizada+" LIKE ?";
                        String[] selectionArgs4 = { temp , "En Actualidad"};

                        int count2 = dbUpdate2.update(
                                FeedReaderContract.FeedEntry.TABLE_NAME,
                                values,
                                selection4,
                                selectionArgs4);

                    }else{
                        SQLiteDatabase dbInsert = finalDbHelper.getWritableDatabase();
                        //finalDbHelper.onUpgrade(db,0,1);
                        // Create a new map of values, where column names are the keys
                        ContentValues values = new ContentValues();
                        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_Nombre, nombres.get(0));
                        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_Domicilio, domicilios.get(0));
                        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_Telefono, telefonos.get(0));
                        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_NombreAccion, temp);
                        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_AccionRealizada,"Comprado");
                        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_CantidadAcciones, 1);

                        // Insert the new row, returning the primary key value of the new row
                        long newRowId = dbInsert.insert(FeedReaderContract.FeedEntry.TABLE_NAME, null, values);

                        SQLiteDatabase dbInsert2 = finalDbHelper.getWritableDatabase();
                        //finalDbHelper.onUpgrade(db,0,1);
                        // Create a new map of values, where column names are the keys
                        ContentValues values2 = new ContentValues();
                        values2.put(FeedReaderContract.FeedEntry.COLUMN_NAME_Nombre, nombres.get(0));
                        values2.put(FeedReaderContract.FeedEntry.COLUMN_NAME_Domicilio, domicilios.get(0));
                        values2.put(FeedReaderContract.FeedEntry.COLUMN_NAME_Telefono, telefonos.get(0));
                        values2.put(FeedReaderContract.FeedEntry.COLUMN_NAME_NombreAccion, temp);
                        values2.put(FeedReaderContract.FeedEntry.COLUMN_NAME_AccionRealizada,"En Actualidad");
                        values2.put(FeedReaderContract.FeedEntry.COLUMN_NAME_CantidadAcciones, 1);

                        // Insert the new row, returning the primary key value of the new row
                        long newRowId2 = dbInsert.insert(FeedReaderContract.FeedEntry.TABLE_NAME, null, values2);


                    }

                    // New value for one column
                    ContentValues values = new ContentValues();
                    values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_Saldo,  saldos.get(0)-precioAcciones[i]);
                    // Which row to update, based on the title
                    String selection = FeedReaderContract.FeedEntry._ID + " LIKE ?";
                    String[] selectionArgs = {"1"};

                    int count = db.update(
                            FeedReaderContract.FeedEntry.TABLE_NAME,
                            values,
                            selection,
                            selectionArgs);


                    SQLiteDatabase dbSelect = finalDbHelper.getReadableDatabase();

                    // Define a projection that specifies which columns from the database
                    // you will actually use after this query.
                    String[] projection2 = {
                            BaseColumns._ID,
                            FeedReaderContract.FeedEntry.COLUMN_NAME_NombreAccion,
                            FeedReaderContract.FeedEntry.COLUMN_NAME_AccionRealizada,
                            FeedReaderContract.FeedEntry.COLUMN_NAME_CantidadAcciones
                    };

                    Context context = getApplicationContext();
                    CharSequence text = "Ha comprado una accion de "+temp+" por "+precioAcciones[i]+"€";
                    int duration = Toast.LENGTH_LONG;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();






                }else{
                    Context context = getApplicationContext();
                    CharSequence text = "El saldo se queda en negativo";
                    int duration = Toast.LENGTH_LONG;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }




            }
        });



        botonVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ComprarAcciones.this, MenuAccionesCliente.class);
                startActivity(intent);
            }
        });


    }
}