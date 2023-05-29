package com.example.eatfitreal;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.MediaController;
import android.widget.VideoView;

public class PopupCronometro extends AppCompatActivity {
    Chronometer cronometro;
    ImageButton buttonstart , buttonpause , buttonrestart;
    boolean correr;
    long deternerse=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup_cronometro);
        //Para las medidas de la ventana del pop up
        DisplayMetrics medidasVentana=new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(medidasVentana);
        int ancho=medidasVentana.widthPixels;
        int alto=medidasVentana.heightPixels;
        getWindow().setLayout((int)(ancho * 0.70), (int) (alto * 0.25));
        buttonstart=findViewById(R.id.imageButton_start);
        buttonpause=findViewById(R.id.imageButton_stop);
        buttonrestart=findViewById(R.id.imageButton);
        buttonpause.setVisibility(View.INVISIBLE);
        cronometro=(Chronometer)findViewById(R.id.cronometro);
        buttonstart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startChronometro();
            }


        });

        buttonpause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopChronometro();
            }
        });

        buttonrestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                restartChronometro();
            }
        });

    }
    private void startChronometro() {
        if(!correr)
        {
            // TRnasucrra en tiempo real
            cronometro.setBase(SystemClock.elapsedRealtime()-deternerse);
            cronometro.start();
            correr=true;
            buttonstart.setVisibility(View.INVISIBLE);
            buttonpause.setVisibility(View.VISIBLE);
        }
    }

    private void stopChronometro() {
        if(correr)
        {
            // TRnasucrra en tiempo real
            cronometro.stop();
            deternerse=SystemClock.elapsedRealtime() - cronometro.getBase();
            correr=false;
            buttonpause.setVisibility(View.INVISIBLE);
            buttonstart.setVisibility(View.VISIBLE);
        }
    }

    private void restartChronometro() {
        cronometro.setBase(SystemClock.elapsedRealtime());
        deternerse=0;
    }
}