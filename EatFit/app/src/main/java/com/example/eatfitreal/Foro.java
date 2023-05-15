package com.example.eatfitreal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class Foro extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foro);
        ListView lista=(ListView) findViewById(R.id.listaPreguntas);
        AdaptadorForo miAdaptador = new AdaptadorForo(this, getPojo());
        lista.setAdapter(miAdaptador);
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(Foro.this, i + "", Toast.LENGTH_SHORT).show();
            }
        });

    }


    public ArrayList<POJOForo> getPojo(){
        DatabaseReference myRef=FirebaseDatabase.getInstance().getReference();
        ArrayList<POJOForo> datos=new ArrayList<>();
        myRef.child("Mensajes").child("Preguntas").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    datos.add(new POJOForo(dataSnapshot.child("pregunta").getValue().toString()));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //Toast.makeText(Foro.this, datos.toString(), Toast.LENGTH_SHORT).show();
        return datos;
    }

    public ArrayList<POJOForo> getPojo2(){

        ArrayList<POJOForo> datos=new ArrayList<>();

        DatabaseReference myRef=FirebaseDatabase.getInstance().getReference();

        myRef.child("Mensajes").child("Preguntas").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    datos.add(new POJOForo(dataSnapshot.getValue().toString()));
                }

                Toast.makeText(Foro.this, datos.get(0).toString(), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });

        /*datos.add(new POJOForo("A"));
        datos.add(new POJOForo("B"));
        datos.add(new POJOForo("C"));*/

        return datos;

    }
}