package com.example.eatfitreal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PopUpDatosPersonales extends AppCompatActivity {

    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up_datos_personales);

        Login l=new Login();
        preferences = getSharedPreferences("Preferences", MODE_PRIVATE);


        //Para las medidas de la ventana del pop up
        DisplayMetrics medidasVentana=new DisplayMetrics();

        getWindowManager().getDefaultDisplay().getMetrics(medidasVentana);

        int ancho=medidasVentana.widthPixels;
        int alto=medidasVentana.heightPixels;

        getWindow().setLayout((int)(ancho * 0.85), (int) (alto * 0.7));

        EditText textViewNick=findViewById(R.id.textViewNick);
        TextView textViewPwd=findViewById(R.id.textViewPwd);
        TextView textViewEmail=findViewById(R.id.textViewEmail);
        TextView textViewDNI=findViewById(R.id.textViewDNI);
        TextView textViewPeso=findViewById(R.id.textViewPeso);
        TextView textViewAltura=findViewById(R.id.textViewAltura);
        TextView textViewFechaNac=findViewById(R.id.textViewFechaNac);
        TextView textViewNumTelefono=findViewById(R.id.textViewNumTelefono);

        textViewNick.setEnabled(false);
        if(preferences.getString("nick", null)==null){
            //nick=l.ultimoUsuarioLogeado();

            DatabaseReference myRef= FirebaseDatabase.getInstance().getReference();
            myRef.child("Usuarios").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    String nick="";
                    String pwd="";
                    String email="";
                    String dni="";
                    String peso="";
                    String altura="";
                    String fechaNac="";
                    String numTelefono="";

                    for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                        if(dataSnapshot.child("nick").getValue().toString().equals(l.ultimoUsuarioLogeado()) || dataSnapshot.child("email").getValue().toString().equals(l.ultimoUsuarioLogeado())){
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
                    textViewNick.setText(nick);
                    textViewPwd.setText(pwd);
                    textViewEmail.setText(email);
                    textViewDNI.setText(dni);
                    textViewPeso.setText(peso);
                    textViewAltura.setText(altura);
                    textViewFechaNac.setText(fechaNac);
                    textViewNumTelefono.setText(numTelefono);

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }else{
            //nick=preferences.getString("nick", null);

            DatabaseReference myRef= FirebaseDatabase.getInstance().getReference();
            myRef.child("Usuarios").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    String nickString=preferences.getString("nick",null);
                    String nick="";
                    String pwd="";
                    String email="";
                    String dni="";
                    String peso="";
                    String altura="";
                    String fechaNac="";
                    String numTelefono="";

                    for(DataSnapshot dataSnapshot: snapshot.getChildren()){
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
                    textViewNick.setText(nick);
                    textViewPwd.setText(pwd);
                    textViewEmail.setText(email);
                    textViewDNI.setText(dni);
                    textViewPeso.setText(peso);
                    textViewAltura.setText(altura);
                    textViewFechaNac.setText(fechaNac);
                    textViewNumTelefono.setText(numTelefono);

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
        ImageButton botonConfig=(ImageButton) findViewById(R.id.imageButtonConfig);

        Button botonSiguiente=(Button) findViewById(R.id.buttonNext);
        botonSiguiente.setVisibility(View.INVISIBLE);
        botonSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                botonConfig.setVisibility(View.VISIBLE);
                botonSiguiente.setVisibility(View.INVISIBLE);
                textViewNick.setEnabled(false);
            }
        });


        botonConfig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                botonConfig.setVisibility(View.INVISIBLE);
                textViewNick.setEnabled(true);
                botonSiguiente.setVisibility(View.VISIBLE);
            }
        });

    }
}