package com.example.eatfitreal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class Foro extends AppCompatActivity {
    ArrayList<POJOForo> datos=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foro);

        ListView lista=(ListView) findViewById(R.id.listaPreguntas);
        Button botonAdd=findViewById(R.id.button_add);
        EditText editText=findViewById(R.id.editTextAddPregunta);

        DatabaseReference myRef= FirebaseDatabase.getInstance().getReference();

        myRef.child("Mensajes").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int unaVez=0;
                datos.clear();
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    datos.add(new POJOForo(dataSnapshot.child("pregunta").getValue().toString()));
                }

                AdaptadorForo miAdaptador = new AdaptadorForo(getApplicationContext(), datos);
                lista.setAdapter(miAdaptador);

                lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Intent intent = new Intent(Foro.this, PopUpRespuesta.class);
                        intent.putExtra("posicion",i);
                        startActivity(intent);
                    }
                });

                botonAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //datos.clear();
                        addPregunta(lista);
                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    public void addPregunta(ListView lista){
        EditText editText=findViewById(R.id.editTextAddPregunta);
        if(editText.getText().toString().equals("")){
            Toast.makeText(this, "La pregunta debe tener algo", Toast.LENGTH_SHORT).show();
        }else{
            DatabaseReference myRef= FirebaseDatabase.getInstance().getReference();
            ArrayList<POJOForo> datos=new ArrayList<>();
            myRef.child("Mensajes").addValueEventListener(new ValueEventListener() {
                int unaVez=0;
                boolean existe=false;

                ArrayList<Integer> ids=new ArrayList<>();
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(unaVez==0){
                        String id="0";
                        for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                            datos.add(new POJOForo(dataSnapshot.child("pregunta").getValue().toString()));
                            id=dataSnapshot.child("id").getValue().toString();
                            ids.add(Integer.parseInt(id));
                        }
                        int max=0;

                        for(int i=0;i<ids.size();i++){
                            if(ids.get(i)>max)
                                max=ids.get(i);
                        }

                        for(POJOForo dato: datos){
                            if(dato.getTexto1().equals(editText.getText().toString()))
                                existe=true;
                        }

                        if(existe)
                            Toast.makeText(Foro.this, "La pregunta ya existe, vaya a ver su respuesta", Toast.LENGTH_SHORT).show();
                        else{
                            int nextId=max+1;
                            HashMap<String,Object> pregunta=new HashMap<>();
                            pregunta.put("id",nextId);
                            pregunta.put("pregunta",editText.getText().toString().toLowerCase());
                            pregunta.put("respuesta","");

                            myRef.child("Mensajes").child("Mensaje"+(nextId)).setValue(pregunta);
                            lista.setAdapter(null);

                        }
                        //rellenaLista(lista);
                        unaVez++;
                        editText.setText("");
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }
}