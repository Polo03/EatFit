package com.example.eatfitreal;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MenuPrincipal extends AppCompatActivity {

    private SharedPreferences preferences;
    public int count=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);

        preferences = getSharedPreferences("Preferences", MODE_PRIVATE);

        //Por si el usuario ha decidido no cerrar sesión, recogemos el nick con
        //las shared preferences.
        String nickPreferences=preferences.getString("nick", null);
        String pwdPreferences=preferences.getString("password",null);

        Login login=new Login();

        TextView texto = (TextView) findViewById(R.id.textViewRutina_Brazo_Pecho);

        //Botón para cuando cerramos sesión
        ImageButton botonSalir = (ImageButton) findViewById(R.id.buttonSalir);
        botonSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MenuPrincipal.this);
                builder.setTitle("ALERTA");
                builder.setMessage("¿Desea cerrar sesión?");        // add the buttons
                builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        cerrarSesion();
                        Intent intent = new Intent(MenuPrincipal.this, Login.class);
                        startActivity(intent);
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();

            }
        });

        //Cuando pulse el boton del foro
        ImageButton botonForo=(ImageButton)findViewById(R.id.buttonForo);
        botonForo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuPrincipal.this, Foro.class);
                startActivity(intent);
            }
        });

        Button button_brazo_pecho=(Button)findViewById(R.id.button_brazo_pecho);
        button_brazo_pecho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Creamos la instancia del menu
                PopupMenu popup= new PopupMenu(MenuPrincipal.this, button_brazo_pecho);
                // Dibujamos el xml que creamos
                popup.getMenuInflater().inflate(R.menu.item_menu, popup.getMenu());
                // Ponemos el escuchador del popup
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        count=1;
                        Intent intent = new Intent(MenuPrincipal.this, Ejercicios.class);
                        intent.putExtra("count", getButtonvalor());
                        intent.putExtra("item",item.getTitle());
                        startActivity(intent);
                        //  Toast.makeText(MenuPrincipal.this, "Seleccionastes " +item.getTitle(), Toast.LENGTH_LONG).show();
                        return true;
                    }
                });
                // Muestra el popup
                popup.show();

            }
        });
        Button button_abdomen=(Button)findViewById(R.id.button_abdomen);
        button_abdomen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Creamos la instancia del menu
                PopupMenu popup= new PopupMenu(MenuPrincipal.this, button_abdomen);
                // Dibujamos el xml que creamos
                popup.getMenuInflater().inflate(R.menu.item_menu, popup.getMenu());
                // Ponemos el escuchador del popup
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        count=2;
                        Intent intent = new Intent(MenuPrincipal.this, Ejercicios.class);
                        intent.putExtra("count", getButtonvalor());
                        intent.putExtra("item",item.getTitle());
                        startActivity(intent);
                        //  Toast.makeText(MenuPrincipal.this, "Seleccionastes " +item.getTitle(), Toast.LENGTH_LONG).show();
                        return true;
                    }
                });
                // Muestra el popup
                popup.show();
            }
        });
        Button button_hombros_espalda=(Button)findViewById(R.id.button_hombros_espalda);
        button_hombros_espalda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Creamos la instancia del menu
                PopupMenu popup= new PopupMenu(MenuPrincipal.this, button_hombros_espalda);
                // Dibujamos el xml que creamos
                popup.getMenuInflater().inflate(R.menu.item_menu, popup.getMenu());
                // Ponemos el escuchador del popup
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        count=3;
                        Intent intent = new Intent(MenuPrincipal.this, Ejercicios.class);
                        intent.putExtra("count", getButtonvalor());
                        intent.putExtra("item",item.getTitle());
                        startActivity(intent);
                        //  Toast.makeText(MenuPrincipal.this, "Seleccionastes " +item.getTitle(), Toast.LENGTH_LONG).show();
                        return true;
                    }
                });
                // Muestra el popup
                popup.show();
            }
        });
        Button button_piernas=(Button)findViewById(R.id.button_piernas);
        button_piernas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Creamos la instancia del menu
                PopupMenu popup= new PopupMenu(MenuPrincipal.this, button_piernas);
                // Dibujamos el xml que creamos
                popup.getMenuInflater().inflate(R.menu.item_menu, popup.getMenu());
                // Ponemos el escuchador del popup
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        count=4;
                        Intent intent = new Intent(MenuPrincipal.this, Ejercicios.class);
                        intent.putExtra("count", getButtonvalor());
                        intent.putExtra("item",item.getTitle());
                        startActivity(intent);
                        //  Toast.makeText(MenuPrincipal.this, "Seleccionastes " +item.getTitle(), Toast.LENGTH_LONG).show();
                        return true;
                    }
                });
                // Muestra el popup
                popup.show();
            }
        });

        ImageButton botonDietas=(ImageButton) findViewById(R.id.buttonDietas);
        botonDietas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuPrincipal.this, Dietas.class);
                startActivity(intent);
            }
        });

    }

    //Método para cerrar sesión, es decir, para limpiar las shared preferences.
    private void cerrarSesion() {
        preferences.edit().clear().apply();
    }

    public int getButtonvalor()
    {
        return count;
    }
    public void setButtonvalor(int valor)
    {
        count=valor;
    }

    public void abrirPopUpDatosPersonales(View v){
        Intent intent = new Intent(MenuPrincipal.this, PopUpDatosPersonales.class);
        startActivity(intent);
    }

}