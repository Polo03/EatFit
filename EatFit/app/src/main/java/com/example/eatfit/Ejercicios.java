package com.example.eatfit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

public class Ejercicios extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ejercicios);
        MenuPrincipal menuPrincipal = new MenuPrincipal();
        int i = getIntent().getIntExtra("count", 0);
        ListView listView = findViewById(R.id.lista_ejercicios);
        menuPrincipal.setButtonvalor(0);
        Adaptador miAdaptador = new Adaptador(this, getPojo(i));
        listView.setAdapter(miAdaptador);
    }

    public POJO [] getPojo(int i)
    {
        if(i==1){
            POJO [] datos_1 = new POJO[]{
                    new POJO(R.drawable.dietas, "Ejercicio 1 de brazo y pecho"),
                    new POJO(R.drawable.calendario, "Ejercicio 2 de brazo y pecho"),
                    new POJO(R.drawable.datospersonales, "Ejercicio 3 de brazo y pecho"),
                    new POJO(R.drawable.foro, "Ejercicio 4 de brazo y pecho"),
                    new POJO(R.drawable.foro, "Ejercicio 4 de brazo y pecho"),
            };
            return datos_1;
        }
        if(i==2)
        {
            POJO[] datos_2 = new POJO[]{
                    new POJO(R.drawable.dietas, "Ejercicio 1 de Abdominales"),
                    new POJO(R.drawable.calendario, "Ejercicio 2 de Abdominales"),
                    new POJO(R.drawable.datospersonales, "Ejercicio 3 de Abdominales"),
                    new POJO(R.drawable.foro, "Ejercicio 4 de Abdominales"),
            };
            return datos_2;
        }
        if(i==3){
            POJO[] datos_3 = new POJO[]{
                    new POJO(R.drawable.dietas, "Ejercicio 1 de Espalda"),
                    new POJO(R.drawable.calendario, "Ejercicio 2 de Espalda"),
                    new POJO(R.drawable.datospersonales, "Ejercicio 3 de Espalda"),
                    new POJO(R.drawable.foro, "Ejercicio 4 de Espalda"),
            };
            return datos_3;
        }
        POJO[] datos_4 = new POJO[]{
                new POJO(R.drawable.dietas, "Ejercicio 1 de Pierna"),
                new POJO(R.drawable.calendario, "Ejercicio 2 de Pierna"),
                new POJO(R.drawable.datospersonales, "Ejercicio 3 de Pierna"),
                new POJO(R.drawable.foro, "Ejercicio 4 de Pierna"),
        };
        return datos_4;

    }
}