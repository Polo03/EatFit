package com.example.eatfit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class Cuestionario extends AppCompatActivity {

    static EatFit eatFit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuestionario);

        String[] respuesta1 = new String[1];
        String[] respuesta2 = new String[1];
        String[] respuesta3 = new String[1];
        String[] respuesta4 = new String[1];
        String[] respuesta5 = new String[1];
        Double[] respuesta6 = new Double[1];
        eatFit =new EatFit(this);
        Button botonSiguiente=(Button) findViewById(R.id.buttonSiguiente);
        //Primer Spinner
        Spinner primerDesplegable=findViewById(R.id.spinnerPrimeraPregunta);

        ArrayAdapter<CharSequence> adapterPrimeraPregunta=ArrayAdapter.createFromResource(getApplicationContext(), R.array.primerDesplegable, android.R.layout.simple_spinner_item);
        adapterPrimeraPregunta.setDropDownViewResource(android.R.layout.simple_spinner_item);

        primerDesplegable.setAdapter(adapterPrimeraPregunta);

        primerDesplegable.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                respuesta1[0] =primerDesplegable.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //Segundo spinner
        Spinner segundoDesplegable=findViewById(R.id.spinnerSegundaPregunta);

        ArrayAdapter<CharSequence> adapterSegundaPregunta=ArrayAdapter.createFromResource(getApplicationContext(), R.array.segundoDesplegable, android.R.layout.simple_spinner_item);
        adapterSegundaPregunta.setDropDownViewResource(android.R.layout.simple_spinner_item);

        segundoDesplegable.setAdapter(adapterSegundaPregunta);

        segundoDesplegable.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                respuesta2[0] =segundoDesplegable.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //Tercer Spinner
        Spinner tercerDesplegable=findViewById(R.id.spinnerTerceraPregunta);

        ArrayAdapter<CharSequence> adapterTerceraPregunta=ArrayAdapter.createFromResource(getApplicationContext(), R.array.tercerDesplegable, android.R.layout.simple_spinner_item);
        adapterSegundaPregunta.setDropDownViewResource(android.R.layout.simple_spinner_item);

        tercerDesplegable.setAdapter(adapterTerceraPregunta);

        tercerDesplegable.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                respuesta3[0] =tercerDesplegable.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //Cuarto Spinner
        Spinner cuartoDesplegable=findViewById(R.id.spinnerCuartaPregunta);

        ArrayAdapter<CharSequence> adapterCuartaPregunta=ArrayAdapter.createFromResource(getApplicationContext(), R.array.cuartoDesplegable, android.R.layout.simple_spinner_item);
        adapterSegundaPregunta.setDropDownViewResource(android.R.layout.simple_spinner_item);

        cuartoDesplegable.setAdapter(adapterCuartaPregunta);

        cuartoDesplegable.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                respuesta4[0] =tercerDesplegable.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //Quinto Spinner
        Spinner quintoDesplegable=findViewById(R.id.spinnerQuintaPregunta);

        ArrayAdapter<CharSequence> adapterQuintaPregunta=ArrayAdapter.createFromResource(getApplicationContext(), R.array.quintaDesplegable, android.R.layout.simple_spinner_item);
        adapterSegundaPregunta.setDropDownViewResource(android.R.layout.simple_spinner_item);

        quintoDesplegable.setAdapter(adapterQuintaPregunta);

        quintoDesplegable.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                respuesta5[0] =tercerDesplegable.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        EditText pesoAConseguir=(EditText) findViewById(R.id.editTextPesoAConseguir);
        String pesoAConseguirString=pesoAConseguir.getText().toString();
        if(!pesoAConseguirString.equals("")) {
            double pesoAConseguirDouble = Double.parseDouble(pesoAConseguirString);
            respuesta6[0] = pesoAConseguirDouble;
                    /*Context context = getApplicationContext();
                    CharSequence text = "Hola";
                    int duration = Toast.LENGTH_LONG;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();*/
        }

        botonSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                actualizarBD(respuesta1[0],respuesta2[0],respuesta3[0],respuesta4[0],respuesta5[0],respuesta6[0]);

                Intent intent = new Intent(Cuestionario.this, MenuPrincipal.class);
                startActivity(intent);
            }
        });
    }

    public void actualizarBD(String respuesta1, String respuesta2, String respuesta3, String respuesta4, String respuesta5, double respuesta6) {
        if (respuesta6<10) {
            introduceRutina1();
        } else {
            introduceRutina2();
        }
    }

    public static void introduceRutina1(){
        SQLiteDatabase db = eatFit.getWritableDatabase();
        // New value for one column
        ContentValues values = new ContentValues();
        values.put(Usuarios.Table.COLUMN_NAME_seHaLogeado, true);
        values.put(Usuarios.Table.COLUMN_NAME_NumRutina, 1);
        Login l = new Login();

        // Which row to update, based on the title
        String selection = Usuarios.Table.COLUMN_NAME_Nick + " LIKE ?";
        String[] selectionArgs = {l.dameNickLogeado()};
        int count = db.update(
                Usuarios.Table.TABLE_NAME,
                values,
                selection,
                selectionArgs);
    }
    public static void introduceRutina2(){
        SQLiteDatabase db = eatFit.getWritableDatabase();

        // New value for one column
        ContentValues values = new ContentValues();
        values.put(Usuarios.Table.COLUMN_NAME_seHaLogeado, true);
        values.put(Usuarios.Table.COLUMN_NAME_NumRutina, 2);
        Login l = new Login();

        // Which row to update, based on the title
        String selection = Usuarios.Table.COLUMN_NAME_Nick + " LIKE ?";
        String[] selectionArgs = {l.dameNickLogeado()};
        int count = db.update(
                Usuarios.Table.TABLE_NAME,
                values,
                selection,
                selectionArgs);

    }

    public static void introduceRutina3(){
        SQLiteDatabase db = eatFit.getWritableDatabase();
        // New value for one column
        ContentValues values = new ContentValues();
        values.put(Usuarios.Table.COLUMN_NAME_seHaLogeado, true);
        values.put(Usuarios.Table.COLUMN_NAME_NumRutina, 3);
        Login l = new Login();

        // Which row to update, based on the title
        String selection = Usuarios.Table.COLUMN_NAME_Nick + " LIKE ?";
        String[] selectionArgs = {l.dameNickLogeado()};
        int count = db.update(
                Usuarios.Table.TABLE_NAME,
                values,
                selection,
                selectionArgs);
    }

    public static void introduceRutina4(){
        SQLiteDatabase db = eatFit.getWritableDatabase();
        // New value for one column
        ContentValues values = new ContentValues();
        values.put(Usuarios.Table.COLUMN_NAME_seHaLogeado, true);
        values.put(Usuarios.Table.COLUMN_NAME_NumRutina, 4);
        Login l = new Login();

        // Which row to update, based on the title
        String selection = Usuarios.Table.COLUMN_NAME_Nick + " LIKE ?";
        String[] selectionArgs = {l.dameNickLogeado()};
        int count = db.update(
                Usuarios.Table.TABLE_NAME,
                values,
                selection,
                selectionArgs);
    }

    public static void introduceRutina5(){
        SQLiteDatabase db = eatFit.getWritableDatabase();
        // New value for one column
        ContentValues values = new ContentValues();
        values.put(Usuarios.Table.COLUMN_NAME_seHaLogeado, true);
        values.put(Usuarios.Table.COLUMN_NAME_NumRutina, 5);
        Login l = new Login();

        // Which row to update, based on the title
        String selection = Usuarios.Table.COLUMN_NAME_Nick + " LIKE ?";
        String[] selectionArgs = {l.dameNickLogeado()};
        int count = db.update(
                Usuarios.Table.TABLE_NAME,
                values,
                selection,
                selectionArgs);
    }


}