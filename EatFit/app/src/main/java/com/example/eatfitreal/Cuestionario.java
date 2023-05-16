package com.example.eatfitreal;

import androidx.annotation.NonNull;
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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class Cuestionario extends AppCompatActivity {
    private SharedPreferences preferences;

    private DatabaseReference myRef;

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

        //Recogemos los preferences si ha decidido no cerrar sesión
        preferences=getSharedPreferences("Preferences",MODE_PRIVATE);
        myRef = FirebaseDatabase.getInstance().getReference();

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
                respuesta4[0] =cuartoDesplegable.getItemAtPosition(i).toString();
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
                respuesta5[0] =quintoDesplegable.getItemAtPosition(i).toString();
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

        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();

        myRef.child("Usuarios").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(noCierraSesion()) {
                    String nick="";
                    String pwd="";
                    String email="";
                    String dni="";
                    String peso="";
                    String altura="";
                    String fechaNac="";
                    String numTelefono="";
                    String nickPreferences=preferences.getString("nick",null);
                    if (respuesta4.equals("Adelgazar")) {
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                            if(dataSnapshot.child("nick").getValue().toString().equals(nickPreferences)){
                                nick=dataSnapshot.child("nick").getValue().toString();
                                pwd=dataSnapshot.child("password").getValue().toString();
                                email=dataSnapshot.child("email").getValue().toString();
                                dni=dataSnapshot.child("DNI").getValue().toString();
                                peso=dataSnapshot.child("peso").getValue().toString();
                                altura=dataSnapshot.child("altura").getValue().toString();
                                fechaNac=dataSnapshot.child("fechaNac").getValue().toString();
                                numTelefono=dataSnapshot.child("phone").getValue().toString();
                            }
                        }

                        Map<String, Object> datosUser = new HashMap<>();
                        datosUser.put("nick",nick);
                        datosUser.put("password",pwd);
                        datosUser.put("email",email);
                        datosUser.put("DNI",dni);
                        datosUser.put("peso",peso);
                        datosUser.put("altura",altura);
                        datosUser.put("fechaNac",fechaNac);
                        datosUser.put("phone",numTelefono);
                        datosUser.put("vecesLogeado",1);
                        datosUser.put("numRutina",1);
                        myRef.child("Usuarios").child(nick).setValue(datosUser);



                    } else {
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                            if(dataSnapshot.child("nick").getValue().toString().equals(nickPreferences)){
                                nick=dataSnapshot.child("nick").getValue().toString();
                                pwd=dataSnapshot.child("password").getValue().toString();
                                email=dataSnapshot.child("email").getValue().toString();
                                dni=dataSnapshot.child("DNI").getValue().toString();
                                peso=dataSnapshot.child("peso").getValue().toString();
                                altura=dataSnapshot.child("altura").getValue().toString();
                                fechaNac=dataSnapshot.child("fechaNac").getValue().toString();
                                numTelefono=dataSnapshot.child("phone").getValue().toString();
                            }
                        }

                        Map<String, Object> datosUser = new HashMap<>();
                        datosUser.put("nick",nick);
                        datosUser.put("password",pwd);
                        datosUser.put("email",email);
                        datosUser.put("DNI",dni);
                        datosUser.put("peso",peso);
                        datosUser.put("altura",altura);
                        datosUser.put("fechaNac",fechaNac);
                        datosUser.put("phone",numTelefono);
                        datosUser.put("vecesLogeado",1);
                        datosUser.put("numRutina",2);
                        myRef.child("Usuarios").child(nick).setValue(datosUser);

                    }
                    Map<String, Object> datosUserCuest = new HashMap<>();
                    datosUserCuest.put("nick",nick);
                    datosUserCuest.put("objetivo1",respuesta1);
                    datosUserCuest.put("objetivo2",respuesta2);
                    datosUserCuest.put("objetivo3",respuesta3);
                    datosUserCuest.put("objetivo4",respuesta4);
                    datosUserCuest.put("objetivo5",respuesta5);
                    datosUserCuest.put("objetivo6",respuesta6);
                    myRef.child("Cuestionario").child(nick).setValue(datosUserCuest);
                }else{
                    Login login=new Login();
                    String nickString=login.ultimoUsuarioLogeado();
                    String nick="";
                    String pwd="";
                    String email="";
                    String dni="";
                    String peso="";
                    String altura="";
                    String fechaNac="";
                    String numTelefono="";
                    if (respuesta4.equals("Adelgazar")) {
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                            if(dataSnapshot.child("nick").getValue().toString().equals(nickString)){
                                nick=dataSnapshot.child("nick").getValue().toString();
                                pwd=dataSnapshot.child("password").getValue().toString();
                                email=dataSnapshot.child("email").getValue().toString();
                                dni=dataSnapshot.child("DNI").getValue().toString();
                                peso=dataSnapshot.child("peso").getValue().toString();
                                altura=dataSnapshot.child("altura").getValue().toString();
                                fechaNac=dataSnapshot.child("fechaNac").getValue().toString();
                                numTelefono=dataSnapshot.child("phone").getValue().toString();
                            }
                        }

                        Map<String, Object> datosUser = new HashMap<>();
                        datosUser.put("nick",nick);
                        datosUser.put("password",pwd);
                        datosUser.put("email",email);
                        datosUser.put("DNI",dni);
                        datosUser.put("peso",peso);
                        datosUser.put("altura",altura);
                        datosUser.put("fechaNac",fechaNac);
                        datosUser.put("phone",numTelefono);
                        datosUser.put("vecesLogeado",1);
                        datosUser.put("numRutina",1);
                        myRef.child("Usuarios").child(nick).setValue(datosUser);

                    } else {
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                            if(dataSnapshot.child("nick").getValue().toString().equals(nickString)){
                                nick=dataSnapshot.child("nick").getValue().toString();
                                pwd=dataSnapshot.child("password").getValue().toString();
                                email=dataSnapshot.child("email").getValue().toString();
                                dni=dataSnapshot.child("DNI").getValue().toString();
                                peso=dataSnapshot.child("peso").getValue().toString();
                                altura=dataSnapshot.child("altura").getValue().toString();
                                fechaNac=dataSnapshot.child("fechaNac").getValue().toString();
                                numTelefono=dataSnapshot.child("phone").getValue().toString();
                            }
                        }

                        Map<String, Object> datosUser = new HashMap<>();
                        datosUser.put("nick",nick);
                        datosUser.put("password",pwd);
                        datosUser.put("email",email);
                        datosUser.put("DNI",dni);
                        datosUser.put("peso",peso);
                        datosUser.put("altura",altura);
                        datosUser.put("fechaNac",fechaNac);
                        datosUser.put("phone",numTelefono);
                        datosUser.put("vecesLogeado",1);
                        datosUser.put("numRutina",1);
                        myRef.child("Usuarios").child(nick).setValue(datosUser);


                    }
                    Map<String, Object> datosUserCuest = new HashMap<>();
                    datosUserCuest.put("nick",nick);
                    datosUserCuest.put("objetivo1",respuesta1);
                    datosUserCuest.put("objetivo2",respuesta2);
                    datosUserCuest.put("objetivo3",respuesta3);
                    datosUserCuest.put("objetivo4",respuesta4);
                    datosUserCuest.put("objetivo5",respuesta5);
                    datosUserCuest.put("objetivo6",respuesta6);
                    myRef.child("Cuestionario").child(nick).setValue(datosUserCuest);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private boolean noCierraSesion(){
        if(preferences.getString("nick",null)!=null){
            return true;
        }
        return false;
    }

}