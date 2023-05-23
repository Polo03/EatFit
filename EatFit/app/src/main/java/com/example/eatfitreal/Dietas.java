package com.example.eatfitreal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;

public class Dietas extends AppCompatActivity {

    SharedPreferences preferences;
    int index=0;

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

        getWindow().setLayout((int)(ancho * 0.85), (int) (alto * 0.15));

        long ahora = System.currentTimeMillis();
        Date fecha = new Date(ahora);
        int dia=fecha.getDay();

        DatabaseReference myRef= FirebaseDatabase.getInstance().getReference();

        TextSwitcher textSwitcher=findViewById(R.id.textSwitcher);
        ImageButton beforeButton=findViewById(R.id.imageButtonBefore);
        ImageButton afterButton=findViewById(R.id.imageButtonAfter);

        String nickStr="";
        if(preferences.getString("nick", null)==null)
            nickStr=l.ultimoUsuarioLogeado();
        else
            nickStr=preferences.getString("nick", null);

        String finalNickStr = nickStr;
        String[] funcion={""};

        myRef.child("Cuestionario").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    if(dataSnapshot.child("nick").getValue().toString().equals(finalNickStr)){
                        funcion[0]=dataSnapshot.child("objetivo4").getValue().toString();
                    }
                }

                if(funcion[0].equals("Adelgazar")){
                    myRef.child("Dietas").child("Definicion").addValueEventListener(new ValueEventListener() {
                        ArrayList<String> a=new ArrayList<>();
                        int cont=0;
                        int[] nums={6,3,0,1,2,5,4};
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
                            String[] galery;
                            String cadena="";
                            if(dia==0){
                                textSwitcher.setText(galeriaTexto[6][index]+"");
                            }else{
                                textSwitcher.setText(galeriaTexto[dia-1][index]+"");
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }else{
                    myRef.child("Dietas").child("Volumen").addValueEventListener(new ValueEventListener() {
                        ArrayList<String> a=new ArrayList<>();
                        int cont=0;
                        int[] nums={6,3,0,1,2,5,4};
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
                            if(dia==0){
                                textSwitcher.setText(galeriaTexto[6][index]);
                            }else{
                                textSwitcher.setText(galeriaTexto[dia-1][index]);
                            }


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
                        if(funcion[0].equals("Adelgazar")){
                            myRef.child("Dietas").child("Definicion").addValueEventListener(new ValueEventListener() {
                                ArrayList<String> a=new ArrayList<>();
                                int cont=0;
                                int[] nums={6,3,0,1,2,5,4};
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
                                    String[] galery;
                                    if(dia==0){
                                        index--;
                                        if(index<0){
                                            index=galeriaTexto[6].length-1;
                                        }
                                        textSwitcher.setText(galeriaTexto[6][index]+"");
                                    }else{
                                        index--;
                                        if(index<0){
                                            index=galeriaTexto[6].length-1;
                                        }
                                        textSwitcher.setText(galeriaTexto[dia-1][index]+"");
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        }else{
                            myRef.child("Dietas").child("Volumen").addValueEventListener(new ValueEventListener() {
                                ArrayList<String> a=new ArrayList<>();
                                int cont=0;
                                int[] nums={6,3,0,1,2,5,4};
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
                                    if(dia<=0){
                                        index--;
                                        if(index<0){
                                            index=galeriaTexto[6].length-1;
                                        }
                                        textSwitcher.setText(galeriaTexto[6][index]);
                                    }else{
                                        index--;
                                        if(index<0){
                                            index=galeriaTexto[6].length-1;
                                        }
                                        textSwitcher.setText(galeriaTexto[dia-1][index]);
                                    }
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
                        if(funcion[0].equals("Adelgazar")){
                            myRef.child("Dietas").child("Definicion").addValueEventListener(new ValueEventListener() {
                                ArrayList<String> a=new ArrayList<>();
                                int cont=0;
                                int[] nums={6,3,0,1,2,5,4};
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
                                    String[] galery;
                                    if(dia==0){
                                        index++;
                                        if(index==galeriaTexto[6].length){
                                            index=0;
                                        }
                                        textSwitcher.setText(galeriaTexto[6][index]+"");
                                    }else{
                                        index++;
                                        if(index==galeriaTexto[6].length){
                                            index=0;
                                        }
                                        textSwitcher.setText(galeriaTexto[dia-1][index]+"");
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        }else{
                            myRef.child("Dietas").child("Volumen").addValueEventListener(new ValueEventListener() {
                                ArrayList<String> a=new ArrayList<>();
                                int cont=0;
                                int[] nums={6,3,0,1,2,5,4};
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
                                    if(dia<=0){
                                        index++;
                                        if(index==galeriaTexto[6].length){
                                            index=0;
                                        }
                                        textSwitcher.setText(galeriaTexto[6][index]);
                                    }else{
                                        index++;
                                        if(index==galeriaTexto[6].length){
                                            index=0;
                                        }
                                        textSwitcher.setText(galeriaTexto[dia-1][index]);
                                    }
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


        textSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                TextView t=new TextView(getApplicationContext());
                t.setMaxHeight(187);
                t.setMaxWidth(308);
                return t;
            }
        });


    }
}