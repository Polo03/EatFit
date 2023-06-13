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
import android.widget.Toast;

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


        //Tercer Spinner
        Spinner tercerDesplegable=findViewById(R.id.spinnerTerceraPregunta);

        ArrayAdapter<CharSequence> adapterTerceraPregunta=ArrayAdapter.createFromResource(getApplicationContext(), R.array.tercerDesplegable, android.R.layout.simple_spinner_item);
        adapterTerceraPregunta.setDropDownViewResource(android.R.layout.simple_spinner_item);

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
        adapterCuartaPregunta.setDropDownViewResource(android.R.layout.simple_spinner_item);

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




        Button botonSiguiente=(Button) findViewById(R.id.buttonSiguiente);

        botonSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EditText pesoAConseguir=(EditText) findViewById(R.id.editTextRespuesta6);
                EditText primerapregunta=(EditText) findViewById(R.id.editextprimerapregunta);
                EditText segundapregunta=(EditText) findViewById(R.id.editTextsegunda);
                EditText quintapregunta=(EditText) findViewById(R.id.editTextQuitapregunta);
                //String pesoAConseguirString=pesoAConseguir.getText().toString();
                if(!pesoAConseguir.getText().toString().equals("")) {
                    double pesoAConseguirDouble = Double.parseDouble(pesoAConseguir.getText().toString());
                    respuesta6[0] = pesoAConseguirDouble;
                }
                respuesta1[0]=primerapregunta.getText().toString();
                respuesta2[0]=segundapregunta.getText().toString();
                respuesta5[0]=quintapregunta.getText().toString();
                if(respuesta1[0].equals("") || respuesta2[0].equals("") || respuesta3[0].equals("") || respuesta4[0].equals("") || respuesta5[0].equals("") || pesoAConseguir.getText().toString().equals("")){
                    Toast.makeText(Cuestionario.this, "Todos los campso deben estar rellenados", Toast.LENGTH_SHORT).show();
                }else{
                    actualizarBD(respuesta1[0],respuesta2[0],respuesta3[0],respuesta4[0],respuesta5[0],respuesta6[0]);

                    Intent intent = new Intent(Cuestionario.this, MenuPrincipal.class);
                    startActivity(intent);
                }

            }
        });
    }

    //Método para actualizar la base de datos según las opciones seleccionadas
    private void actualizarBD(String respuesta1, String respuesta2, String respuesta3, String respuesta4, String respuesta5, double respuesta6) {

        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();

        myRef.child("Usuarios").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Login l=new Login();
                String nick="";
                String pwd="";
                String email="";
                String dni="";
                String peso="";
                String altura="";
                String fechaNac="";
                String numTelefono="";
                int version=0;
                String nickStr=l.ultimoUsuarioLogeado();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    if(dataSnapshot.child("nick").getValue().toString().equals(nickStr)){
                        nick=dataSnapshot.child("nick").getValue().toString();
                        pwd=dataSnapshot.child("password").getValue().toString();
                        email=dataSnapshot.child("email").getValue().toString();
                        dni=dataSnapshot.child("DNI").getValue().toString();
                        peso=dataSnapshot.child("peso").getValue().toString();
                        altura=dataSnapshot.child("altura").getValue().toString();
                        fechaNac=dataSnapshot.child("fechaNac").getValue().toString();
                        numTelefono=dataSnapshot.child("phone").getValue().toString();
                        version=Integer.parseInt(dataSnapshot.child("version").getValue().toString());
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
                datosUser.put("version",version);
                myRef.child("Usuarios").child(nick).setValue(datosUser);

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

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

}