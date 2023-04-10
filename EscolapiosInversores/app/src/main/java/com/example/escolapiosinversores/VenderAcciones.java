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

public class VenderAcciones extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vender_acciones);


        ListView listado=findViewById(R.id.listadoVender);
        Button botonVolver=findViewById(R.id.buttonVolverVender);

        ArrayList<String> values=new ArrayList<>();

        FeedReaderDbHelper finalDbHelper=new FeedReaderDbHelper(this);

        SQLiteDatabase db = finalDbHelper.getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                BaseColumns._ID,
                FeedReaderContract.FeedEntry.COLUMN_NAME_Nombre,
                FeedReaderContract.FeedEntry.COLUMN_NAME_Domicilio,
                FeedReaderContract.FeedEntry.COLUMN_NAME_Telefono,
                FeedReaderContract.FeedEntry.COLUMN_NAME_Saldo,
                FeedReaderContract.FeedEntry.COLUMN_NAME_NombreAccion
        };

        // Filter results WHERE "title" = 'My Title'
        String selection = FeedReaderContract.FeedEntry.COLUMN_NAME_AccionRealizada + " LIKE ?";
        String[] selectionArgs = { "Comprado" };




        Cursor cursor = db.query(
                FeedReaderContract.FeedEntry.TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                selectionArgs,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                null              // The sort order
        );
        //List itemIds = new ArrayList<>();
        while(cursor.moveToNext()) {
            //long id=cursor.getLong(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry._ID));
            //String nombre=cursor.getString(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_Nombre));
            //String domicilio=cursor.getString(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_Domicilio));
            //String telefono=cursor.getString(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_Telefono));
            //Double saldo=cursor.getDouble(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_Saldo));
            String nombreAccion=cursor.getString(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_NombreAccion));
            //Integer dato2=cursor.getInt(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_AccionRealizada));
            //itemIds.add("nombre:" + nombre+"\n"+"domicilio:" + domicilio+"\n"+"telefono:" + telefono+"\n"+"saldo:" + saldo+"€");

                values.add(nombreAccion);
        }
        cursor.close();


        //final String[] values=new String[] {"Microsoft","PepePhone","Adios"};
        ArrayAdapter<String> adaptador=new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,values);
        listado.setAdapter(adaptador);


        ArrayList<String> nombres=new ArrayList<>();
        ArrayList<String> domicilios=new ArrayList<>();
        ArrayList<String> telefonos=new ArrayList<>();
        ArrayList<Double> saldos=new ArrayList<>();
        ArrayList<String> nombresAccion=new ArrayList<>();
        ArrayList<Integer> cantidadAcciones=new ArrayList<>();
        ArrayList<String> accionesRealizadas=new ArrayList<>();

        Random ale=new Random();
        int[] precioAcciones=new int[3];

        for(int i=0;i<3;i++){
            precioAcciones[i]=ale.nextInt(50)+50;
        }


        listado.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String temp = (String) adapterView.getAdapter().getItem(i);
                //accion[0] =temp;



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


                // Filter results WHERE "title" = 'My Title'
                //String selection = FeedReaderContract.FeedEntry.COLUMN_NAME_AccionRealizada + " = ? AND "+FeedReaderContract.FeedEntry.COLUMN_NAME_NombreAccion+" = ?";
                //String[] selectionArgs = { "Vendido" , temp };



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
                    String accionRealizada=cursor.getString(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_AccionRealizada));
                    //Integer dato2=cursor.getInt(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_AccionRealizada));
                    //itemIds.add("nombre:" + nombre+"\n"+"domicilio:" + domicilio+"\n"+"telefono:" + telefono+"\n"+"saldo:" + saldo+"€");
                    if(cont==0){
                        nombres.add(nombre);
                        domicilios.add(domicilio);
                        telefonos.add(telefono);
                        saldos.add(saldo);
                        nombresAccion.add(nombreAccion);
                        accionesRealizadas.add(accionRealizada);
                    }


                }
                cursor.close();

                boolean existeAccionVendida=false;

                // Define a projection that specifies which columns from the database
                // you will actually use after this query.
                String[] projection2 = {
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
                String selection = FeedReaderContract.FeedEntry.COLUMN_NAME_AccionRealizada + " = ? AND "+FeedReaderContract.FeedEntry.COLUMN_NAME_NombreAccion+" = ?";
                String[] selectionArgs = { "Vendido" , temp };



                Cursor cursor2 = db.query(
                        FeedReaderContract.FeedEntry.TABLE_NAME,   // The table to query
                        projection2,             // The array of columns to return (pass null to get all)
                        selection,              // The columns for the WHERE clause
                        selectionArgs,          // The values for the WHERE clause
                        null,                   // don't group the rows
                        null,                   // don't filter by row groups
                        null              // The sort order
                );
                //List itemIds = new ArrayList<>();

                while(cursor2.moveToNext()) {
                    //long id=cursor.getLong(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry._ID));
                    //String nombre=cursor2.getString(cursor2.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_Nombre));
                    //String domicilio=cursor2.getString(cursor2.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_Domicilio));
                    //String telefono=cursor2.getString(cursor2.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_Telefono));
                    //Double saldo=cursor2.getDouble(cursor2.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_Saldo));
                    //String nombreAccion=cursor2.getString(cursor2.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_NombreAccion));
                    Integer cantidadAccion=cursor2.getInt(cursor2.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_CantidadAcciones));
                    //String accionRealizada=cursor2.getString(cursor2.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_AccionRealizada));
                    //Integer dato2=cursor.getInt(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_AccionRealizada));
                    //itemIds.add("nombre:" + nombre+"\n"+"domicilio:" + domicilio+"\n"+"telefono:" + telefono+"\n"+"saldo:" + saldo+"€");
                    //nombres.add(nombre);
                    //domicilios.add(domicilio);
                    //telefonos.add(telefono);
                    //saldos.add(saldo);
                    //nombresAccion.add(nombreAccion);
                    cantidadAcciones.add(cantidadAccion);
                    //accionesRealizadas.add(accionRealizada);
                    existeAccionVendida=true;

                }
                cursor2.close();

                boolean puedeVender=true;

                if(dameCantidadCompradas(temp,finalDbHelper)<dameCantidadVendidas(temp,finalDbHelper)+1){
                    puedeVender=false;
                }

                if(existeAccionVendida){


                    if(puedeVender){
                        SQLiteDatabase dbUpdate = finalDbHelper.getWritableDatabase();

                        // New value for one column

                        ContentValues values = new ContentValues();
                        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_CantidadAcciones, cantidadAcciones.get(0)+1);
                        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_Domicilio,domicilios.get(0));
                        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_Telefono,telefonos.get(0));
                        // Which row to update, based on the title
                        String selection3 = FeedReaderContract.FeedEntry.COLUMN_NAME_NombreAccion + " LIKE ? AND "+FeedReaderContract.FeedEntry.COLUMN_NAME_AccionRealizada+" LIKE ?";
                        String[] selectionArgs3 = { temp , "Vendido"};

                        int count = dbUpdate.update(
                                FeedReaderContract.FeedEntry.TABLE_NAME,
                                values,
                                selection3,
                                selectionArgs3);

                        // New value for one column
                        ContentValues values3 = new ContentValues();
                        values3.put(FeedReaderContract.FeedEntry.COLUMN_NAME_Saldo,  saldos.get(0)+precioAcciones[i]);
                        // Which row to update, based on the title
                        String selection4 = FeedReaderContract.FeedEntry._ID + " LIKE ?";
                        String[] selectionArgs4 = {"1"};

                        int count2 = db.update(
                                FeedReaderContract.FeedEntry.TABLE_NAME,
                                values3,
                                selection4,
                                selectionArgs4);

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
                        String selection2 = FeedReaderContract.FeedEntry.COLUMN_NAME_AccionRealizada + " = ? AND "+FeedReaderContract.FeedEntry.COLUMN_NAME_NombreAccion+" = ?";
                        String[] selectionArgs2 = { "En Actualidad" , temp };



                        Cursor cursor3 = db.query(
                                FeedReaderContract.FeedEntry.TABLE_NAME,   // The table to query
                                projection3,             // The array of columns to return (pass null to get all)
                                selection2,              // The columns for the WHERE clause
                                selectionArgs2,          // The values for the WHERE clause
                                null,                   // don't group the rows
                                null,                   // don't filter by row groups
                                null              // The sort order
                        );

                        ArrayList<Integer> cantidadAcciones2=new ArrayList<>();

                        while (cursor3.moveToNext()){
                            Integer cantidadAccion=cursor3.getInt(cursor3.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_CantidadAcciones));
                            cantidadAcciones2.add(cantidadAccion);
                        }
                        cursor3.close();



                        SQLiteDatabase dbUpdate2 = finalDbHelper.getWritableDatabase();

                        // New value for one column

                        ContentValues values2 = new ContentValues();
                        values2.put(FeedReaderContract.FeedEntry.COLUMN_NAME_CantidadAcciones, cantidadAcciones2.get(0)-1);
                        values2.put(FeedReaderContract.FeedEntry.COLUMN_NAME_Domicilio,domicilios.get(0));
                        values2.put(FeedReaderContract.FeedEntry.COLUMN_NAME_Telefono,telefonos.get(0));
                        // Which row to update, based on the title
                        String selection5 = FeedReaderContract.FeedEntry.COLUMN_NAME_NombreAccion + " LIKE ? AND "+FeedReaderContract.FeedEntry.COLUMN_NAME_AccionRealizada+" LIKE ?";
                        String[] selectionArgs5 = { temp , "En Actualidad"};

                        int count3 = dbUpdate2.update(
                                FeedReaderContract.FeedEntry.TABLE_NAME,
                                values2,
                                selection5,
                                selectionArgs5);



                    }else{
                        Context context = getApplicationContext();
                        CharSequence text = "No puede vender mas acciones de las que tiene compradas";
                        int duration = Toast.LENGTH_LONG;

                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                    }




                }else{

                    SQLiteDatabase dbInsert = finalDbHelper.getWritableDatabase();
                    //finalDbHelper.onUpgrade(db,0,1);
                    // Create a new map of values, where column names are the keys
                    ContentValues values = new ContentValues();
                    values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_Nombre, nombres.get(0));
                    values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_Domicilio, domicilios.get(0));
                    values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_Telefono, telefonos.get(0));
                    values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_NombreAccion, temp);
                    values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_AccionRealizada,"Vendido");
                    values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_CantidadAcciones, 1);

                    // Insert the new row, returning the primary key value of the new row
                    long newRowId = dbInsert.insert(FeedReaderContract.FeedEntry.TABLE_NAME, null, values);

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
                    String selection2 = FeedReaderContract.FeedEntry.COLUMN_NAME_AccionRealizada + " = ? AND "+FeedReaderContract.FeedEntry.COLUMN_NAME_NombreAccion+" = ?";
                    String[] selectionArgs2 = { "En Actualidad" , temp };



                    Cursor cursor3 = db.query(
                            FeedReaderContract.FeedEntry.TABLE_NAME,   // The table to query
                            projection3,             // The array of columns to return (pass null to get all)
                            selection2,              // The columns for the WHERE clause
                            selectionArgs2,          // The values for the WHERE clause
                            null,                   // don't group the rows
                            null,                   // don't filter by row groups
                            null              // The sort order
                    );

                    ArrayList<Integer> cantidadAcciones2=new ArrayList<>();

                    while (cursor3.moveToNext()){
                        Integer cantidadAccion=cursor3.getInt(cursor3.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_CantidadAcciones));
                        cantidadAcciones2.add(cantidadAccion);
                    }
                    cursor3.close();



                    SQLiteDatabase dbUpdate2 = finalDbHelper.getWritableDatabase();

                    // New value for one column

                    ContentValues values2 = new ContentValues();
                    values2.put(FeedReaderContract.FeedEntry.COLUMN_NAME_CantidadAcciones, cantidadAcciones2.get(0)-1);
                    values2.put(FeedReaderContract.FeedEntry.COLUMN_NAME_Domicilio,domicilios.get(0));
                    values2.put(FeedReaderContract.FeedEntry.COLUMN_NAME_Telefono,telefonos.get(0));
                    // Which row to update, based on the title
                    String selection5 = FeedReaderContract.FeedEntry.COLUMN_NAME_NombreAccion + " LIKE ? AND "+FeedReaderContract.FeedEntry.COLUMN_NAME_AccionRealizada+" LIKE ?";
                    String[] selectionArgs5 = { temp , "En Actualidad"};

                    int count3 = dbUpdate2.update(
                            FeedReaderContract.FeedEntry.TABLE_NAME,
                            values2,
                            selection5,
                            selectionArgs5);

                }


                Context context = getApplicationContext();
                CharSequence text = "Ha vendido una accion de "+temp+" por "+precioAcciones[i]+"€";
                int duration = Toast.LENGTH_LONG;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();



            }
        });


        botonVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(VenderAcciones.this, MenuAccionesCliente.class);
                startActivity(intent2);
            }
        });



    }
    public static int dameCantidadCompradas(String temp, FeedReaderDbHelper a){


        SQLiteDatabase db = a.getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection2 = {
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
        String selection = FeedReaderContract.FeedEntry.COLUMN_NAME_AccionRealizada + " = ? AND "+FeedReaderContract.FeedEntry.COLUMN_NAME_NombreAccion+" = ?";
        String[] selectionArgs = { "Comprado" , temp };



        Cursor cursor2 = db.query(
                FeedReaderContract.FeedEntry.TABLE_NAME,   // The table to query
                projection2,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                selectionArgs,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                null              // The sort order
        );
        //List itemIds = new ArrayList<>();

        while(cursor2.moveToNext()) {
            //long id=cursor.getLong(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry._ID));
            //String nombre=cursor2.getString(cursor2.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_Nombre));
            //String domicilio=cursor2.getString(cursor2.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_Domicilio));
            //String telefono=cursor2.getString(cursor2.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_Telefono));
            //Double saldo=cursor2.getDouble(cursor2.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_Saldo));
            //String nombreAccion=cursor2.getString(cursor2.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_NombreAccion));
            Integer cantidadAccion=cursor2.getInt(cursor2.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_CantidadAcciones));
            //String accionRealizada=cursor2.getString(cursor2.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_AccionRealizada));
            //Integer dato2=cursor.getInt(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_AccionRealizada));
            //itemIds.add("nombre:" + nombre+"\n"+"domicilio:" + domicilio+"\n"+"telefono:" + telefono+"\n"+"saldo:" + saldo+"€");
            //nombres.add(nombre);
            //domicilios.add(domicilio);
            //telefonos.add(telefono);
            //saldos.add(saldo);
            //nombresAccion.add(nombreAccion);
            return cantidadAccion;
            //accionesRealizadas.add(accionRealizada);

        }
        cursor2.close();

        return -1;
    }

    public static int dameCantidadVendidas(String temp, FeedReaderDbHelper a){


        SQLiteDatabase db = a.getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection2 = {
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
        String selection = FeedReaderContract.FeedEntry.COLUMN_NAME_AccionRealizada + " = ? AND "+FeedReaderContract.FeedEntry.COLUMN_NAME_NombreAccion+" = ?";
        String[] selectionArgs = { "Vendido" , temp };



        Cursor cursor2 = db.query(
                FeedReaderContract.FeedEntry.TABLE_NAME,   // The table to query
                projection2,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                selectionArgs,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                null              // The sort order
        );
        //List itemIds = new ArrayList<>();

        while(cursor2.moveToNext()) {
            //long id=cursor.getLong(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry._ID));
            //String nombre=cursor2.getString(cursor2.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_Nombre));
            //String domicilio=cursor2.getString(cursor2.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_Domicilio));
            //String telefono=cursor2.getString(cursor2.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_Telefono));
            //Double saldo=cursor2.getDouble(cursor2.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_Saldo));
            //String nombreAccion=cursor2.getString(cursor2.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_NombreAccion));
            Integer cantidadAccion=cursor2.getInt(cursor2.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_CantidadAcciones));
            //String accionRealizada=cursor2.getString(cursor2.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_AccionRealizada));
            //Integer dato2=cursor.getInt(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_AccionRealizada));
            //itemIds.add("nombre:" + nombre+"\n"+"domicilio:" + domicilio+"\n"+"telefono:" + telefono+"\n"+"saldo:" + saldo+"€");
            //nombres.add(nombre);
            //domicilios.add(domicilio);
            //telefonos.add(telefono);
            //saldos.add(saldo);
            //nombresAccion.add(nombreAccion);
            return cantidadAccion;
            //accionesRealizadas.add(accionRealizada);

        }
        cursor2.close();

        return -1;
    }
}

