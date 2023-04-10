package com.example.escolapiosinversores;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MenuAccionesCliente extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_clientes);

        Button botonMostrarDatos=(Button) findViewById(R.id.buttonMostrarDatosCliente);
        Button botonModificarDatos=(Button) findViewById(R.id.buttonModificarDatosCliente);
        Button botonMostrarAcciones=(Button) findViewById(R.id.buttonAcciones);
        Button botonMostrarAccionesPropias=(Button) findViewById(R.id.buttonMostrarAccionesPropias);
        Button botonVenderAcciones=(Button) findViewById(R.id.buttonVender);
        Button botonHistorial=(Button) findViewById(R.id.buttonhistorial);
        Button botonAccionesTiempoReal=(Button) findViewById(R.id.buttonAccionesTiempoReal);

        botonMostrarDatos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuAccionesCliente.this, MostrarDatosCliente.class);
                startActivity(intent);
            }
        });

        botonModificarDatos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuAccionesCliente.this, ModificarDatosCliente.class);
                startActivity(intent);
            }
        });

        botonMostrarAcciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuAccionesCliente.this, ComprarAcciones.class);
                startActivity(intent);
            }
        });

        botonMostrarAccionesPropias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuAccionesCliente.this, MostrarAcciones.class);
                startActivity(intent);
            }
        });

        botonVenderAcciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuAccionesCliente.this, VenderAcciones.class);
                startActivity(intent);
            }
        });

        botonHistorial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuAccionesCliente.this, historialMovimientos.class);
                startActivity(intent);
            }
        });

        botonAccionesTiempoReal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuAccionesCliente.this, AccionesEnTiempoReal.class);
                startActivity(intent);
            }
        });

    }
}