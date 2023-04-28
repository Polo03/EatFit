package com.example.eatfit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class Cuestionario extends AppCompatActivity {

    static Logica logica;

    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuestionario);

        //Creamos arrays de longitud 1 para guardarnos lo seleccionado en las opciones.
        String[] respuesta1 = new String[1];
        String[] respuesta2 = new String[1];
        String[] respuesta3 = new String[1];
        String[] respuesta4 = new String[1];
        String[] respuesta5 = new String[1];
        Double[] respuesta6 = new Double[1];

        EatFit eatFit=new EatFit(this);

        //Recogemos los preferences si ha decidido no cerrar sesión
        preferences=getSharedPreferences("Preferences",MODE_PRIVATE);

        logica=new Logica(eatFit);


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


        Button botonSiguiente=(Button) findViewById(R.id.buttonSiguiente);

        botonSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EditText pesoAConseguir=(EditText) findViewById(R.id.editTextPesoAConseguir);
                String pesoAConseguirString=pesoAConseguir.getText().toString();
                if(!pesoAConseguirString.equals("")) {
                    double pesoAConseguirDouble = Double.parseDouble(pesoAConseguirString);
                    respuesta6[0] = pesoAConseguirDouble;
                }

                actualizarBD(respuesta1[0],respuesta2[0],respuesta3[0],respuesta4[0],respuesta5[0],respuesta6[0]);

                Intent intent = new Intent(Cuestionario.this, MenuPrincipal.class);
                startActivity(intent);
            }
        });
    }

    //Método para actualizar la base de datos según las opciones seleccionadas
    private void actualizarBD(String respuesta1, String respuesta2, String respuesta3, String respuesta4, String respuesta5, double respuesta6) {
        if(noCierraSesion()) {
            if (respuesta6 < 10) {
                logica.actualizaARutina1(preferences.getString("nick", null));
            } else {
                logica.actualizaARutina2(preferences.getString("nick", null));
            }
        }else{
            Login login=new Login();
            if (respuesta6 < 10) {
                logica.actualizaARutina1(login.ultimoUsuarioLogeado());
            } else {
                logica.actualizaARutina2(login.ultimoUsuarioLogeado());
            }
        }
    }

    private boolean noCierraSesion(){
        if(preferences.getString("nick",null)!=null){
            return true;
        }
        return false;
    }

}