package com.example.eatfitreal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
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

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;

public class Dietas extends AppCompatActivity {


    SharedPreferences preferences;
    ImageButton beforeButton, afterButton;
    ImageSwitcher imageSwitcher;
    TextSwitcher textSwitcher;
    int index=0;
    int index2=0;

    int galeriaVolumen[]={R.drawable.datospersonales,R.drawable.foro,R.drawable.botonsalir};
    int galeriaDefinicion[]={R.drawable.datospersonales,R.drawable.foro,R.drawable.botonsalir};

    String lunesVolumenDesayuno="Desayuno\n3 huevos enteros\n1 taza de avena cocida con leche descremada\n1 taza de frutas frescas (fresas, arándanos, plátanos, etc.)";
    String lunesVolumenMediaMañana="Media Mañana\n1 batido de proteína de suero de leche\n1 manzana";
    String lunesVolumenAlmuerzo="Almuerzo\n150 gramos de pollo a la parrilla\n1 taza de arroz integral\n1 taza de brócoli al vapor";
    String lunesVolumenMerienda="Merienda\n1 taza de yogur griego sin azúcar\n1 taza de frutas frescas (kiwi, mango, piña, etc.)";
    String lunesVolumenCena="Cena\n150 gramos de salmón a la parrilla\n1 taza de quinoa cocida\n1 taza de espinacas cocidas";
    String lunesVolumenAntesDeDormir="Antes de Dormir\n1 batido de proteína de caseína";
    String martesVolumenDesayuno="Desayuno\n2 huevos revueltos o una tortilla francesa.\n1 rebanada de pan integral tostado con aceite de oliva.\n1 taza de frutas frescas.";
    String martesVolumenMediaMañana="Media Mañana\n1 puñado de frutos secos (nueces, almendras, etc.).\n1 pieza de fruta fresca.";
    String martesVolumenAlmuerzo="Almuerzo\n150 gramos de pechuga de pollo a la plancha o pescado blanco a la parrilla.\n1 taza de verduras al vapor o ensalada mixta (lechuga, tomate, cebolla, zanahoria, etc.).\n1/2 taza de arroz integral o quinoa.";
    String martesVolumenMerienda="Merienda\n1 yogur natural bajo en grasas.\n1 pieza de fruta fresca.";
    String martesVolumenCena="Cena\n150 gramos de salmón a la parrilla o tofu salteado.\n1 taza de verduras al vapor o ensalada mixta.\n1/2 taza de arroz integral o quinoa.";
    String miercolesVolumenDesayuno="Desayuno\n2-3 huevos enteros revueltos o cocidos.\n1 taza de avena con leche descremada y frutas.\n1 rebanada de pan integral tostado con aceite de oliva.";
    String miercolesVolumenMediaMañana="Media Mañana\nBatido de proteínas en polvo mezclado con agua o leche descremada.\n1 puñado de frutos secos (nueces, almendras, etc.).";
    String miercolesVolumenAlmuerzo="Almuerzo\n150-200 gramos de pechuga de pollo o ternera a la parrilla.\n1 taza de arroz integral.\n1 taza de verduras al vapor o ensalada mixta.";
    String miercolesVolumenMerienda="Merienda\n1 batido de proteínas en polvo mezclado con agua o leche descremada.\n1 pieza de fruta fresca.";
    String miercolesVolumenCena="Cena\n150-200 gramos de pescado blanco a la parrilla o pollo a la plancha.\n1 taza de arroz integral o quinoa.\n1 taza de verduras al vapor o ensalada mixta.";
    String juevesVolumenDesayuno="Desayuno\nTazón de yogur con avena\nOrejones de melocotón\nPipas de girasol.";
    String juevesVolumenMediaMañana="Media Mañana\nVaso de leche\nBarritas proteicas de chocolate\nAlmendras.";
    String juevesVolumenAlmuerzo="Almuerzo\nPastel de arroz, atún y queso feta\nPlátano";
    String juevesVolumenMerienda="Merienda\nZumo de fruta\nBocadillo de jamón serrano y queso.";
    String juevesVolumenCena="Cena\nGuiso de pavo con pimientos y champiñones\nManzana.";
    String lunesVolumen[]={lunesVolumenDesayuno,lunesVolumenMediaMañana,lunesVolumenAlmuerzo,lunesVolumenMerienda,lunesVolumenCena,lunesVolumenAntesDeDormir};
    String martesVolumen[]={martesVolumenDesayuno,martesVolumenMediaMañana,martesVolumenAlmuerzo,martesVolumenMerienda,martesVolumenCena};
    String miercolesVolumen[]={miercolesVolumenDesayuno,miercolesVolumenMediaMañana,miercolesVolumenAlmuerzo,miercolesVolumenMerienda,miercolesVolumenCena};
    String juevesVolumen[]={juevesVolumenDesayuno,juevesVolumenMediaMañana,juevesVolumenAlmuerzo,juevesVolumenMerienda,juevesVolumenCena};
    String galeriaTextoVolumen[][]={lunesVolumen,martesVolumen,miercolesVolumen,juevesVolumen,{"aV1","bV1","cV1"},{"aS1","bS1","cS1"},{"aD1","bD1","cD1"}};
    String galeriaTextoDefinicion[][]={{"aL2,bL2,cL2"},{"aM2","bM2","cM2"},{"aX2","bX2","cX2"},{"aJ2","bJ2","cJ2"},{"aV2","bV2","cV2"},{"aS2","bS2","cS2"},{"aD2","bD2","cD2"}};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dietas);

        Login l=new Login();
        preferences = getSharedPreferences("Preferences", MODE_PRIVATE);

        long ahora = System.currentTimeMillis();
        Date fecha = new Date(ahora);
        int dia=fecha.getDay();

        String[] diasSemana={"Lunes","Martes","Miercoles","Jueves","Viernes","Sabado","Domingo"};
        DatabaseReference myRef= FirebaseDatabase.getInstance().getReference();

        imageSwitcher=findViewById(R.id.imageSwitcher);
        textSwitcher=findViewById(R.id.textSwitcher);
        beforeButton=findViewById(R.id.imageButtonBefore);
        afterButton=findViewById(R.id.imageButtonAfter);


        String nickStr="";
        if(preferences.getString("nick", null)==null)
            nickStr=l.ultimoUsuarioLogeado();
        else
            nickStr=preferences.getString("nick", null);

        String finalNickStr = nickStr;
        String[] funcion={""};


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
                        Toast.makeText(Dietas.this, funcion[0], Toast.LENGTH_SHORT).show();
                        if(funcion[0].equals("Adelgazar")){
                            index--;
                            index2--;
                            if(index==galeriaDefinicion.length-1){
                                index=0;
                                index2=0;
                            }
                            //imageSwitcher.setImageResource(galeriaDefinicion[index]);
                            textSwitcher.setText(galeriaTextoDefinicion[dia-1][index2]);
                        }else{

                            index--;
                            index2--;
                            if(index2==juevesVolumen.length-1){
                                index=0;
                                index2=0;
                            }
                            //imageSwitcher.setImageResource(galeriaVolumen[index]);
                            Toast.makeText(Dietas.this, index2 , Toast.LENGTH_SHORT).show();
                            textSwitcher.setText(galeriaTextoVolumen[dia-1][index2]);
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
                        Toast.makeText(Dietas.this, funcion[0], Toast.LENGTH_SHORT).show();
                        if(funcion[0].equals("Adelgazar")){
                            index++;
                            index2++;
                            if(index==galeriaDefinicion.length){
                                index=0;
                                index2=0;
                            }
                            imageSwitcher.setImageResource(galeriaDefinicion[index]);
                            textSwitcher.setText(galeriaTextoDefinicion[dia-1][index2]);
                        }else{
                            index++;
                            index2++;
                            if(index2==juevesVolumen.length){
                                index=0;
                                index2=0;
                            }
                            //imageSwitcher.setImageResource(galeriaVolumen[index]);
                            textSwitcher.setText(galeriaTextoVolumen[dia-1][index2]);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });

        imageSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                ImageView imageView =new ImageView(getApplicationContext());
                imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                imageView.setMaxHeight(187);
                imageView.setMaxWidth(308);
                return imageView;
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

        myRef.child("Cuestionario").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    if(dataSnapshot.child("nick").getValue().toString().equals(finalNickStr)){
                        funcion[0]=dataSnapshot.child("objetivo4").getValue().toString();
                    }
                }
                Toast.makeText(Dietas.this, funcion[0], Toast.LENGTH_SHORT).show();
                if(funcion[0].equals("Adelgazar")){
                    imageSwitcher.setImageResource(galeriaDefinicion[index]);
                    textSwitcher.setText(galeriaTextoDefinicion[dia-1][index2]);
                }else{
                    imageSwitcher.setImageResource(galeriaVolumen[index]);
                    textSwitcher.setText(galeriaTextoVolumen[dia-1][index2]);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}