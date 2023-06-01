package com.example.eatfitreal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ConteoCalorias extends AppCompatActivity {

    SharedPreferences preferences;
    final private ArrayList<String> alimentos=new ArrayList<>();
    final private ArrayList<String> cantidad=new ArrayList<>();
    final private ArrayList<Integer> calorias=new ArrayList<>();
    int index=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conteo_calorias);
        TextView textView=findViewById(R.id.textViewCalorias);

        Login l=new Login();
        preferences = getSharedPreferences("Preferences", MODE_PRIVATE);

        //Para las medidas de la ventana del pop up
        DisplayMetrics medidasVentana=new DisplayMetrics();

        getWindowManager().getDefaultDisplay().getMetrics(medidasVentana);

        int ancho=medidasVentana.widthPixels;
        int alto=medidasVentana.heightPixels;

        getWindow().setLayout((int)(ancho * 0.90), (int) (alto * 0.70));

        TextView textSwitcher=findViewById(R.id.textSwitcherConteo);

        String nickStr="";
        if(preferences.getString("nick", null)==null)
            nickStr=l.ultimoUsuarioLogeado();
        else
            nickStr=preferences.getString("nick", null);

        DatabaseReference myRef= FirebaseDatabase.getInstance().getReference();
        String finalNickStr = nickStr;

        myRef.child("Alimentos").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    alimentos.add(dataSnapshot.child("nombreAlimento").getValue().toString());
                    cantidad.add(dataSnapshot.child("nombreAlimento").getValue().toString());
                    calorias.add(Integer.parseInt(dataSnapshot.child("calorias").getValue().toString()));
                }
                textSwitcher.setText(alimentos.get(index));
                myRef.child("Calorias").addValueEventListener(new ValueEventListener() {
                    int unaVez=0;

                    int caloriasEstablecidas=0;
                    int caloriasTotales=0;
                    int caloriasConsumidas=0;

                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                            if(dataSnapshot.child("nick").getValue().toString().equals(finalNickStr)){
                                caloriasEstablecidas=Integer.parseInt(dataSnapshot.child("caloriasEstablecidas").getValue().toString());
                                caloriasTotales=Integer.parseInt(dataSnapshot.child("caloriasDeseadas").getValue().toString());
                                caloriasConsumidas=Integer.parseInt(dataSnapshot.child("caloriasConsumidas").getValue().toString());
                            }
                        }
                        if(caloriasEstablecidas==0){
                            AlertDialog.Builder builder = new AlertDialog.Builder(ConteoCalorias.this);
                            builder.setTitle("Informacion");
                            builder.setMessage("¿Cuantas calorias quiere consumir hoy?");        // add the buttons
                            final EditText textCalorias=new EditText(ConteoCalorias.this);
                            textCalorias.setInputType(InputType.TYPE_CLASS_NUMBER);
                            textCalorias.setHint("Calorias Diarias");
                            builder.setView(textCalorias);
                            builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    DatabaseReference myRef= FirebaseDatabase.getInstance().getReference();
                                    myRef = FirebaseDatabase.getInstance().getReference();

                                    Map<String, Object> calorias = new HashMap<>();
                                    calorias.put("nick",finalNickStr);
                                    calorias.put("caloriasDeseadas",Integer.parseInt(textCalorias.getText().toString()));
                                    calorias.put("caloriasConsumidas",0);
                                    calorias.put("caloriasEstablecidas",1);
                                    myRef.child("Calorias").child(finalNickStr).setValue(calorias);
                                    dialog.dismiss();
                                }
                            });
                            AlertDialog dialog = builder.create();
                            dialog.show();
                        }
                        int caloriasRestantes=caloriasTotales-caloriasConsumidas;
                        textView.setText("Las calorias totales que quiere consumir son "+caloriasTotales+", las calorias consumidas actualmente son "+caloriasConsumidas+", las calorias restantes actualmente son "+caloriasRestantes);

                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        textSwitcher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myRef.child("Calorias").addValueEventListener(new ValueEventListener() {

                    int caloriasEstablecidas=0;
                    int caloriasTotales=0;
                    int caloriasConsumidas=0;
                    int unaVez=0;

                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                            if(dataSnapshot.child("nick").getValue().toString().equals(finalNickStr)){
                                caloriasTotales=Integer.parseInt(dataSnapshot.child("caloriasDeseadas").getValue().toString());
                                caloriasConsumidas=Integer.parseInt(dataSnapshot.child("caloriasConsumidas").getValue().toString());
                            }
                        }
                        if(unaVez==0){
                            unaVez++;
                            //int cal=calorias.get(index);

                            actualizaCalorias(finalNickStr,caloriasTotales,caloriasConsumidas,calorias.get(index));
                        }

                        //Toast.makeText(ConteoCalorias.this, +"", Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

        ImageButton buttonBefore=findViewById(R.id.imageButtonBeforeConteo);
        buttonBefore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                index--;
                if(index<0)
                    index=alimentos.size()-1;
                textSwitcher.setText(alimentos.get(index));
            }
        });

        ImageButton buttonAfter=findViewById(R.id.imageButtonAfterConteo);
        buttonAfter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                index++;
                if(index>=alimentos.size())
                    index=0;
                textSwitcher.setText(alimentos.get(index));
            }
        });


    }

    public static void actualizaCalorias(String nick, int caloriasTotales, int caloriasConsumidas, int cal){
        DatabaseReference myRef=FirebaseDatabase.getInstance().getReference();
        Map<String, Object> calorias = new HashMap<>();
        calorias.put("nick",nick);
        calorias.put("caloriasDeseadas",caloriasTotales);
        calorias.put("caloriasConsumidas",(caloriasConsumidas+cal));
        calorias.put("caloriasEstablecidas",1);
        myRef.child("Calorias").child(nick).setValue(calorias);
    }

}