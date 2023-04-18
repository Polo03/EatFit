package com.example.eatfit;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.sql.Blob;

public class MenuPrincipal extends AppCompatActivity {

    private SharedPreferences preferences;
    private EatFit eatFit;

    private Logica logica;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);

        init();

        //Por si el usuario ha decidido no cerrar sesión, recogemos el nick con
        //las shared preferences.
        String nick=preferences.getString("nick", null);

        Login login=new Login();

        TextView texto = (TextView) findViewById(R.id.textViewRutina);
        if(nick!=null){
            texto.setText(nick + "-->" + logica.getRutina(nick));
        }else{
            texto.setText(login.ultimoUsuarioLogeado() + "-->" + logica.getRutina(login.ultimoUsuarioLogeado()));
        }

        //Botón para cuando cerramos sesión
        ImageButton botonSalir = (ImageButton) findViewById(R.id.buttonSalir);
        botonSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cerrarSesion();

                Intent intent = new Intent(MenuPrincipal.this, Login.class);
                startActivity(intent);
            }
        });

        ImageView imagenRutina = (ImageView) findViewById(R.id.imageViewRutina);
    }

    //Método para iniciar todas las variables
    private void init(){
        preferences = getSharedPreferences("Preferences", MODE_PRIVATE);
        eatFit = new EatFit(this);
        logica = new Logica(eatFit, preferences);
    }

    //Método para cerrar sesión, es decir, para limpiar las shared preferences.
    private void cerrarSesion() {
        preferences.edit().clear().apply();
    }

}

