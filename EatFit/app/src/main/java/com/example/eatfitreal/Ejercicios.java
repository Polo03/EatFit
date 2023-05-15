package com.example.eatfitreal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Ejercicios extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ejercicios);
        MenuPrincipal menuPrincipal = new MenuPrincipal();
        int i = getIntent().getIntExtra("count", 0);
        String item=getIntent().getStringExtra("item");
        ListView listView = findViewById(R.id.lista_ejercicios);
        menuPrincipal.setButtonvalor(0);
        Adaptador miAdaptador = new Adaptador(this, getPojo(i,item));
        listView.setAdapter(miAdaptador);
    }

    public POJO [] getPojo(int i,String item)
    {

        DatabaseReference myRef= FirebaseDatabase.getInstance().getReference();
        if(i==1){
            POJO [] datos_1 = new POJO[5];
            if(item.equals("Principiante")){
                myRef.child("Rutinas").child("Definicion").child("BrazoYPecho").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                            if(dataSnapshot.child("id").getValue().toString().equals("1")){
                                //Toast.makeText(Ejercicios.this, "Rutina1", Toast.LENGTH_SHORT).show();
                                datos_1[0]=new POJO(R.drawable.fondo, dataSnapshot.child("ejercicio1").getValue().toString());
                                datos_1[1]=new POJO(R.drawable.dietas, dataSnapshot.child("ejercicio2").getValue().toString());
                                datos_1[2]=new POJO(R.drawable.dietas, dataSnapshot.child("ejercicio3").getValue().toString());
                                datos_1[3]=new POJO(R.drawable.dietas, dataSnapshot.child("ejercicio4").getValue().toString());
                                datos_1[4]=new POJO(R.drawable.dietas, dataSnapshot.child("ejercicio5").getValue().toString());
                            }

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                return datos_1;
            }

        }
        if(i==2)
        {
            POJO[] datos_2 = new POJO[]{
                    new POJO(R.drawable.dietas, "Ejercicio 1 de Abdominales"),
                    new POJO(R.drawable.calendario, "Ejercicio 2 de Abdominales"),
                    new POJO(R.drawable.datospersonales, "Ejercicio 3 de Abdominales"),
                    new POJO(R.drawable.foro, "Ejercicio 4 de Abdominales"),
            };
            return datos_2;
        }
        if(i==3){
            POJO[] datos_3 = new POJO[]{
                    new POJO(R.drawable.dietas, "Ejercicio 1 de Espalda"),
                    new POJO(R.drawable.calendario, "Ejercicio 2 de Espalda"),
                    new POJO(R.drawable.datospersonales, "Ejercicio 3 de Espalda"),
                    new POJO(R.drawable.foro, "Ejercicio 4 de Espalda"),
            };
            return datos_3;
        }
        POJO[] datos_4 = new POJO[]{
                new POJO(R.drawable.dietas, "Ejercicio 1 de Pierna"),
                new POJO(R.drawable.calendario, "Ejercicio 2 de Pierna"),
                new POJO(R.drawable.datospersonales, "Ejercicio 3 de Pierna"),
                new POJO(R.drawable.foro, "Ejercicio 4 de Pierna"),
        };
        return datos_4;

    }
}