package com.example.eatfit;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


public class Adaptador extends ArrayAdapter {

    private POJO[] datos;

    public Adaptador(Context contexto, POJO[] datos){
        super(contexto, R.layout.activity_adaptador, datos);
        this.datos = datos;
    }
    public View getView(int posicion, View convertView, ViewGroup parent){
        LayoutInflater mostrado = LayoutInflater.from(getContext());
        View elemento = mostrado.inflate(R.layout.activity_adaptador, parent, false);
        ImageView imagen=elemento.findViewById(R.id.imagen);
        imagen.setImageResource(datos[posicion].getImagen());
        TextView texto1 = elemento.findViewById(R.id.textViewOlvidarPwd);
        texto1.setText(datos[posicion].getTexto1());
        return elemento;
    }

}