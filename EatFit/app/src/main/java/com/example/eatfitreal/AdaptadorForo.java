package com.example.eatfitreal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class AdaptadorForo extends ArrayAdapter {

    private ArrayList<POJOForo> datos;

    public AdaptadorForo(Context contexto, ArrayList<POJOForo> datos){
        super(contexto, R.layout.activity_adaptador, datos);
        this.datos = datos;
    }
    public View getView(int posicion, View convertView, ViewGroup parent){
        LayoutInflater mostrado = LayoutInflater.from(getContext());
        View elemento = mostrado.inflate(R.layout.activity_adaptador_foro, parent, false);
        TextView texto1 = elemento.findViewById(R.id.textViewMostrarPregunta);
        texto1.setText(datos.get(posicion).getTexto1());
        return elemento;
    }
}