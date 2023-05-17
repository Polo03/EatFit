package com.example.eatfitreal;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;

public class Dietas extends AppCompatActivity {


    ImageButton beforeButton, afterButton;
    ImageSwitcher imageSwitcher;
    int index=0;
    int galeria[]={R.drawable.datospersonales,R.drawable.foro,R.drawable.botonsalir};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dietas);

        long ahora = System.currentTimeMillis();
        Date fecha = new Date(ahora);

        String[] diasSemana={"Lunes","Martes","Miercoles","Jueves","Viernes","Sabado","Domingo"};

        imageSwitcher=findViewById(R.id.imageSwitcher);
        beforeButton=findViewById(R.id.imageButtonBefore);
        afterButton=findViewById(R.id.imageButtonAfter);


        beforeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                index--;
                if(index<0){
                    index=galeria.length-1;
                }
                imageSwitcher.setImageResource(galeria[index]);
            }
        });

        afterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                index++;
                if(index==galeria.length){
                    index=0;
                }
                imageSwitcher.setImageResource(galeria[index]);
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

        imageSwitcher.setImageResource(galeria[index]);
    }
}