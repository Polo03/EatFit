package com.example.eatfitreal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class PopUpRespuesta extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up_respuesta);
        //Para las medidas de la ventana del pop up
        DisplayMetrics medidasVentana=new DisplayMetrics();

        getWindowManager().getDefaultDisplay().getMetrics(medidasVentana);

        int ancho=medidasVentana.widthPixels;
        int alto=medidasVentana.heightPixels;

        getWindow().setLayout((int)(ancho * 0.85), (int) (alto * 0.35));

        int i=getIntent().getIntExtra("posicion",0);

        TextView textViewPregunta=findViewById(R.id.textView_pregunta);
        TextView respuesta=(TextView) findViewById(R.id.textViewRespuesta);
        respuesta.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
        DatabaseReference myRef= FirebaseDatabase.getInstance().getReference();
        ArrayList<String> respuestas=new ArrayList<>();
        myRef.child("Mensajes").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    respuestas.add(dataSnapshot.child("respuesta").getValue().toString());
                    //Toast.makeText(PopUpRespuesta.this, dataSnapshot.child("respuesta").getValue().toString(), Toast.LENGTH_SHORT).show();
                }
                textViewPregunta.setText("Pregunta "+(i+1));
                if(respuestas.get(i).equals(""))
                    respuesta.setText("Estamos deliverando si esta pregunta es válida para nuestro foro, si es válida, la respuesta será plasmada con las otras preguntas válidas, sino, la pregunta será eliminada de nuestro foro");
                else
                    respuesta.setText(respuestas.get(i));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}