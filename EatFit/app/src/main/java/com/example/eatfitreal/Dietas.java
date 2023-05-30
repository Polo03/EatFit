package com.example.eatfitreal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Dietas extends AppCompatActivity {

    SharedPreferences preferences;
    int index=0;
    int[] nums={6,3,0,1,2,5,4};
    String[] horas={"Desayuno","Media Mañana","Comida","Merienda","Cena"};
    int galeria[]={R.drawable.lunes,R.drawable.martes,R.drawable.miercoles,R.drawable.jueves,R.drawable.viernes,R.drawable.sabado,R.drawable.domingo};

    String galeriaTexto[][]=new String[7][5];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dietas);

        Login l=new Login();
        preferences = getSharedPreferences("Preferences", MODE_PRIVATE);

        //Para las medidas de la ventana del pop up
        DisplayMetrics medidasVentana=new DisplayMetrics();

        getWindowManager().getDefaultDisplay().getMetrics(medidasVentana);

        int ancho=medidasVentana.widthPixels;
        int alto=medidasVentana.heightPixels;

        getWindow().setLayout((int)(ancho * 0.90), (int) (alto * 0.70));

        Calendar calendario = Calendar.getInstance();
        long ahora = System.currentTimeMillis();
        Date fecha = new Date(ahora);
        int dia=fecha.getDay();
        int hora = fecha.getHours();
        if(hora>=4 && hora<10)
            index=0;
        else if(hora>=10 && hora<12)
            index=1;
        else if(hora>=12 && hora<17)
            index=2;
        else if(hora>=17 && hora<19)
            index=3;
        else
            index=4;

        ImageView imagenDia=findViewById(R.id.imageViewDia);
        imagenDia.setImageResource(galeria[dia-1]);

        DatabaseReference myRef= FirebaseDatabase.getInstance().getReference();

        TextView textSwitcher=findViewById(R.id.textSwitcherConteo);
        ImageButton beforeButton=findViewById(R.id.imageButtonBeforeConteo);
        ImageButton afterButton=findViewById(R.id.imageButtonAfterConteo);
        TextView textViewHora=findViewById(R.id.textView8);

        String nickStr="";
        if(preferences.getString("nick", null)==null)
            nickStr=l.ultimoUsuarioLogeado();
        else
            nickStr=preferences.getString("nick", null);

        String finalNickStr = nickStr;
        String[] funcion={""};
        textSwitcher.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);

        myRef.child("Cuestionario").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    if(dataSnapshot.child("nick").getValue().toString().equals(finalNickStr)){
                        funcion[0]=dataSnapshot.child("objetivo4").getValue().toString();
                    }
                }
                textViewHora.setText(horas[index]);
                if(funcion[0].equals("Adelgazar")){
                    myRef.child("Dietas").child("Definicion").addValueEventListener(new ValueEventListener() {
                        int cont=0;
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                                galeriaTexto[nums[cont]][0]=dataSnapshot.child("desayuno").getValue().toString();
                                galeriaTexto[nums[cont]][1]=dataSnapshot.child("media_mañana").getValue().toString();
                                galeriaTexto[nums[cont]][2]=dataSnapshot.child("comida").getValue().toString();
                                galeriaTexto[nums[cont]][3]=dataSnapshot.child("merienda").getValue().toString();
                                galeriaTexto[nums[cont]][4]=dataSnapshot.child("cena").getValue().toString();
                                cont++;
                            }
                            String cadena="";
                            String c="";
                            if(dia==0){
                                for(int i=0;i<galeriaTexto[6][index].length();i++){
                                    if(galeriaTexto[6][index].charAt(i)=='_') {
                                        cadena += c + "\n";
                                        c="";
                                    }else{
                                        c += galeriaTexto[6][index].charAt(i);
                                    }

                                }
                                if(c.length()==galeriaTexto[6][index].length())
                                    cadena=c;
                            }else{
                                for(int i=0;i<galeriaTexto[dia-1][index].length();i++){
                                    if(galeriaTexto[dia-1][index].charAt(i)=='_') {
                                        cadena += c + "\n";
                                        c="";
                                    }else {
                                        c += galeriaTexto[dia - 1][index].charAt(i);
                                    }
                                }
                                if(c.length()==galeriaTexto[dia-1][index].length())
                                    cadena=c;

                            }
                            textSwitcher.setText(cadena);
                            textViewHora.setText(horas[index]);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }else{
                    myRef.child("Dietas").child("Volumen").addValueEventListener(new ValueEventListener() {
                        ArrayList<String> a=new ArrayList<>();
                        int cont=0;
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                                galeriaTexto[nums[cont]][0]=dataSnapshot.child("desayuno").getValue().toString();
                                galeriaTexto[nums[cont]][1]=dataSnapshot.child("media_mañana").getValue().toString();
                                galeriaTexto[nums[cont]][2]=dataSnapshot.child("comida").getValue().toString();
                                galeriaTexto[nums[cont]][3]=dataSnapshot.child("merienda").getValue().toString();
                                galeriaTexto[nums[cont]][4]=dataSnapshot.child("cena").getValue().toString();
                                cont++;
                            }
                            String cadena="";
                            String c="";
                            if(dia==0){
                                for(int i=0;i<galeriaTexto[6][index].length();i++){
                                    if(galeriaTexto[6][index].charAt(i)=='_') {
                                        cadena += c + "\n";
                                        c="";
                                    }else
                                        c+=galeriaTexto[6][index].charAt(i);

                                }
                                if(c.length()==galeriaTexto[6][index].length())
                                    cadena=c;
                            }else{
                                for(int i=0;i<galeriaTexto[dia-1][index].length();i++){
                                    if(galeriaTexto[dia-1][index].charAt(i)=='_') {
                                        cadena += c + "\n";
                                        c="";
                                    }else
                                        c+=galeriaTexto[dia-1][index].charAt(i);
                                }
                                if(c.length()==galeriaTexto[dia-1][index].length())
                                    cadena=c;

                            }
                            textSwitcher.setText(cadena);
                            textViewHora.setText(horas[index]);


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        beforeButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                myRef.child("Cuestionario").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                            if(dataSnapshot.child("nick").getValue().toString().equals(finalNickStr)){
                                funcion[0]=dataSnapshot.child("objetivo4").getValue().toString();
                            }
                        }
                        index--;
                        if(funcion[0].equals("Adelgazar")){
                            myRef.child("Dietas").child("Definicion").addValueEventListener(new ValueEventListener() {
                                ArrayList<String> a=new ArrayList<>();
                                int cont=0;

                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                                        galeriaTexto[nums[cont]][0]=dataSnapshot.child("desayuno").getValue().toString();
                                        galeriaTexto[nums[cont]][1]=dataSnapshot.child("media_mañana").getValue().toString();
                                        galeriaTexto[nums[cont]][2]=dataSnapshot.child("comida").getValue().toString();
                                        galeriaTexto[nums[cont]][3]=dataSnapshot.child("merienda").getValue().toString();
                                        galeriaTexto[nums[cont]][4]=dataSnapshot.child("cena").getValue().toString();
                                        cont++;
                                    }
                                    String cadena="";
                                    String c="";
                                    if(dia==0){
                                        if(index<0){
                                            index=galeriaTexto[6].length-1;
                                        }
                                        for(int i=0;i<galeriaTexto[6][index].length();i++){
                                            if(galeriaTexto[6][index].charAt(i)=='_') {
                                                cadena += c + "\n";
                                                c="";
                                            }else
                                                c+=galeriaTexto[6][index].charAt(i);

                                        }
                                        if(c.length()==galeriaTexto[6][index].length())
                                            cadena=c;
                                    }else{
                                        if(index<0){
                                            index=galeriaTexto[dia-1].length-1;
                                        }
                                        for(int i=0;i<galeriaTexto[dia-1][index].length();i++){
                                            if(galeriaTexto[dia-1][index].charAt(i)=='_') {
                                                cadena += c + "\n";
                                                c="";
                                            }else
                                                c+=galeriaTexto[dia-1][index].charAt(i);

                                        }
                                        if(c.length()==galeriaTexto[dia-1][index].length())
                                            cadena=c;

                                    }
                                    textViewHora.setText(horas[index]);
                                    textSwitcher.setText(cadena+"");
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        }else{
                            myRef.child("Dietas").child("Volumen").addValueEventListener(new ValueEventListener() {
                                ArrayList<String> a=new ArrayList<>();
                                int cont=0;
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                                        galeriaTexto[nums[cont]][0]=dataSnapshot.child("desayuno").getValue().toString();
                                        galeriaTexto[nums[cont]][1]=dataSnapshot.child("media_mañana").getValue().toString();
                                        galeriaTexto[nums[cont]][2]=dataSnapshot.child("comida").getValue().toString();
                                        galeriaTexto[nums[cont]][3]=dataSnapshot.child("merienda").getValue().toString();
                                        galeriaTexto[nums[cont]][4]=dataSnapshot.child("cena").getValue().toString();
                                        cont++;
                                    }
                                    String cadena="";
                                    String c="";
                                    if(dia<=0){
                                        if(index<0){
                                            index=galeriaTexto[6].length-1;
                                        }
                                        for(int i=0;i<galeriaTexto[6][index].length();i++){
                                            if(galeriaTexto[6][index].charAt(i)=='_') {
                                                cadena += c + "\n";
                                                c="";
                                            }else
                                                c+=galeriaTexto[6][index].charAt(i);

                                        }
                                        if(c.length()==galeriaTexto[6][index].length())
                                            cadena=c;
                                    }else{
                                        if(index<0){
                                            index=galeriaTexto[dia-1].length-1;
                                        }
                                        for(int i=0;i<galeriaTexto[dia-1][index].length();i++){
                                            if(galeriaTexto[dia-1][index].charAt(i)=='_') {
                                                cadena += c + "\n";
                                                c="";
                                            }else
                                                c+=galeriaTexto[dia-1][index].charAt(i);

                                        }
                                        if(c.length()==galeriaTexto[dia-1][index].length())
                                            cadena=c;
                                    }
                                    textViewHora.setText(horas[index]);
                                    textSwitcher.setText(cadena);
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });

                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

        afterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myRef.child("Cuestionario").addValueEventListener(new ValueEventListener() {

                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                            if(dataSnapshot.child("nick").getValue().toString().equals(finalNickStr)){
                                funcion[0]=dataSnapshot.child("objetivo4").getValue().toString();
                            }
                        }
                        index++;

                        if(funcion[0].equals("Adelgazar")){
                            myRef.child("Dietas").child("Definicion").addValueEventListener(new ValueEventListener() {
                                ArrayList<String> a=new ArrayList<>();
                                int cont=0;
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                                        galeriaTexto[nums[cont]][0]=dataSnapshot.child("desayuno").getValue().toString();
                                        galeriaTexto[nums[cont]][1]=dataSnapshot.child("media_mañana").getValue().toString();
                                        galeriaTexto[nums[cont]][2]=dataSnapshot.child("comida").getValue().toString();
                                        galeriaTexto[nums[cont]][3]=dataSnapshot.child("merienda").getValue().toString();
                                        galeriaTexto[nums[cont]][4]=dataSnapshot.child("cena").getValue().toString();
                                        cont++;
                                    }
                                    String cadena="";
                                    String c="";
                                    if(dia==0){
                                        if(index==galeriaTexto[6].length){
                                            index=0;
                                        }
                                        for(int i=0;i<galeriaTexto[6][index].length();i++){
                                            if(galeriaTexto[6][index].charAt(i)=='_') {
                                                cadena += c + "\n";
                                                c="";
                                            }else
                                                c+=galeriaTexto[6][index].charAt(i);
                                            if(c.length()==galeriaTexto[6][index].length())
                                                cadena=c;
                                        }
                                    }else{
                                        if(index==galeriaTexto[dia-1].length){
                                            index=0;
                                        }
                                        for(int i=0;i<galeriaTexto[dia-1][index].length();i++){
                                            if(galeriaTexto[dia-1][index].charAt(i)=='_') {
                                                cadena += c + "\n";
                                                c="";
                                            }else
                                                c+=galeriaTexto[dia-1][index].charAt(i);
                                            if(c.length()==galeriaTexto[dia-1][index].length())
                                                cadena=c;
                                        }
                                    }
                                    textViewHora.setText(horas[index]);
                                    textSwitcher.setText(cadena);
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        }else{
                            myRef.child("Dietas").child("Volumen").addValueEventListener(new ValueEventListener() {
                                ArrayList<String> a=new ArrayList<>();
                                int cont=0;
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                                        galeriaTexto[nums[cont]][0]=dataSnapshot.child("desayuno").getValue().toString();
                                        galeriaTexto[nums[cont]][1]=dataSnapshot.child("media_mañana").getValue().toString();
                                        galeriaTexto[nums[cont]][2]=dataSnapshot.child("comida").getValue().toString();
                                        galeriaTexto[nums[cont]][3]=dataSnapshot.child("merienda").getValue().toString();
                                        galeriaTexto[nums[cont]][4]=dataSnapshot.child("cena").getValue().toString();
                                        cont++;
                                    }
                                    String cadena="";
                                    String c="";
                                    if(dia<=0){
                                        if(index==galeriaTexto[6].length){
                                            index=0;
                                        }
                                        for(int i=0;i<galeriaTexto[6][index].length();i++){
                                            if(galeriaTexto[6][index].charAt(i)=='_') {
                                                cadena += c + "\n";
                                                c="";
                                            }else
                                                c+=galeriaTexto[6][index].charAt(i);
                                            if(c.length()==galeriaTexto[6][index].length())
                                                cadena=c;
                                        }
                                    }else{
                                        if(index==galeriaTexto[dia-1].length){
                                            index=0;
                                        }
                                        for(int i=0;i<galeriaTexto[dia-1][index].length();i++){
                                            if(galeriaTexto[dia-1][index].charAt(i)=='_') {
                                                cadena += c + "\n";
                                                c="";
                                            }else
                                                c+=galeriaTexto[dia-1][index].charAt(i);
                                            if(c.length()==galeriaTexto[dia-1][index].length())
                                                cadena=c;
                                        }
                                    }
                                    textViewHora.setText(horas[index]);
                                    textSwitcher.setText(cadena);
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });

    }
}