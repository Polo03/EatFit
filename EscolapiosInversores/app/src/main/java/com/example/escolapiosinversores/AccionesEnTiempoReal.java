package com.example.escolapiosinversores;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

public class AccionesEnTiempoReal extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acciones_en_tiempo_real);

        Button botonVolver=findViewById(R.id.buttonVolverTiempoReal);
        TextView textViewAcciones=findViewById(R.id.textViewAccionesEnTiempoReal);

        Random ale=new Random();
        String[] acciones={"Microsoft","Pepephone","Movistar"};
        textViewAcciones.setText("");
        for(int i=0;i<acciones.length;i++){
            textViewAcciones.setText(textViewAcciones.getText()+""+acciones[i]+" "+(ale.nextInt(50)+50)+"€\n");
        }

        botonVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AccionesEnTiempoReal.this, MenuAccionesCliente.class);
                startActivity(intent);
            }
        });
    }
}