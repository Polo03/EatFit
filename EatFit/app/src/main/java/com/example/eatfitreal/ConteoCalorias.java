package com.example.eatfitreal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ConteoCalorias extends AppCompatActivity {

    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conteo_calorias);

        Login l=new Login();
        preferences = getSharedPreferences("Preferences", MODE_PRIVATE);

        //Para las medidas de la ventana del pop up
        DisplayMetrics medidasVentana=new DisplayMetrics();

        getWindowManager().getDefaultDisplay().getMetrics(medidasVentana);

        int ancho=medidasVentana.widthPixels;
        int alto=medidasVentana.heightPixels;

        getWindow().setLayout((int)(ancho * 0.90), (int) (alto * 0.70));

        String nickStr="";
        if(preferences.getString("nick", null)==null)
            nickStr=l.ultimoUsuarioLogeado();
        else
            nickStr=preferences.getString("nick", null);

        Intent intent = new Intent(ConteoCalorias.this, MenuPrincipal.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(ConteoCalorias.this, 0,intent, 0);

        conteoCalorias(nickStr);
    }

    public void conteoCalorias(String nick){

        DatabaseReference myRef= FirebaseDatabase.getInstance().getReference();

        TextView textView=findViewById(R.id.textViewCalorias);

        myRef.child("Calorias").addValueEventListener(new ValueEventListener() {

            int caloriasTotales=0;
            int caloriasRestantes=0;
            int caloriasConsumidas=0;

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    if(dataSnapshot.child("nick").getValue().toString().equals(nick)){
                        caloriasTotales=Integer.parseInt(dataSnapshot.child("caloriasDeseadas").getValue().toString());
                        caloriasConsumidas=Integer.parseInt(dataSnapshot.child("caloriasConsumidas").getValue().toString());
                        caloriasRestantes=caloriasTotales-caloriasConsumidas;
                    }
                }

                textView.setText("Las calorias totales que quiere consumir son "+caloriasTotales+", las calorias consumidas actualmente son "+caloriasConsumidas+", las calorias restantes actualmente son "+caloriasRestantes);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

}