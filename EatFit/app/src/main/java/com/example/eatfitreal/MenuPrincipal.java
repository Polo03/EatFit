package com.example.eatfitreal;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class MenuPrincipal extends AppCompatActivity {

    private final static String CHANNEL_ID = "NOTIFICACION";
    private final static int NOTIFICATION_ID = 0;
    private SharedPreferences preferences;
    public int count = 0;
    int index = 1;
    static String nickStr="";

    private int version=0;

    String[] niveles = {"Principiante", "Intermedio", "Avanzado"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);

        Login l=new Login();
        preferences = getSharedPreferences("Preferences", MODE_PRIVATE);

        Calendar calendario = Calendar.getInstance();
        long ahora = System.currentTimeMillis();
        calendario.setTimeInMillis(ahora);
        Date fecha = new Date(ahora);
        int dia=calendario.get(Calendar.DAY_OF_MONTH);
        int year=calendario.get(Calendar.YEAR);
        int hora = fecha.getHours()+2;
        int min= fecha.getMinutes();
        int seg= fecha.getSeconds();

        //Por si el usuario ha decidido no cerrar sesión, recogemos el nick con
        //las shared preferences.
        String nickStr="";
        if(preferences.getString("nick", null)==null)
            nickStr=l.ultimoUsuarioLogeado();
        else
            nickStr=preferences.getString("nick", null);
        String pwdPreferences = preferences.getString("password", null);

        Login login = new Login();

        TextView texto = (TextView) findViewById(R.id.textViewRutina_Brazo_Pecho);
        ImageButton conteoCalorico = findViewById(R.id.buttonCalendario);
        String finalNickStr = nickStr;
        int finalHora = hora;


        DatabaseReference myRef2 = FirebaseDatabase.getInstance().getReference();

        /*myRef2.child("Cuestionario").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                boolean existe=true;

                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    if(dataSnapshot.child("nick").getValue().toString().equals("")){
                        existe=false;
                    }
                }
                if(!existe){
                    Intent intent = new Intent(MenuPrincipal.this, Login.class);
                    startActivity(intent);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });*/

        boolean existe=true;
        if(finalNickStr.equals("") || finalNickStr==null){
            existe=false;
        }
        //Toast.makeText(this, finalNickStr+"", Toast.LENGTH_SHORT).show();
        if(!existe){
            Intent intent = new Intent(MenuPrincipal.this, Login.class);
            startActivity(intent);
        }


        DatabaseReference myRef=FirebaseDatabase.getInstance().getReference();
        myRef.child("Usuarios").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    if(dataSnapshot.child("nick").getValue().toString().equals(finalNickStr)){
                        version= Integer.parseInt(dataSnapshot.child("version").getValue().toString());
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        conteoCalorico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(version==1){
                    DatabaseReference myRef=FirebaseDatabase.getInstance().getReference();
                    myRef.child("Calorias").addValueEventListener(new ValueEventListener() {
                        int unaVez=0;
                        int caloriasEstablecidas=0;
                        int caloriasTotales=0;
                        int caloriasConsumidas=0;

                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                                if(dataSnapshot.child("nick").getValue().toString().equals(finalNickStr)){
                                    caloriasEstablecidas=Integer.parseInt(dataSnapshot.child("caloriasEstablecidas").getValue().toString());
                                    caloriasTotales=Integer.parseInt(dataSnapshot.child("caloriasDeseadas").getValue().toString());
                                    caloriasConsumidas=Integer.parseInt(dataSnapshot.child("caloriasConsumidas").getValue().toString());
                                }
                            }
                            if(unaVez==0){
                                if(caloriasConsumidas>=caloriasTotales){
                                    Toast.makeText(MenuPrincipal.this, "Ya ha consumido todas las calorias que se propuso, las cuales eran "+caloriasTotales+" calorias.", Toast.LENGTH_SHORT).show();
                                }else{
                                    Intent intent = new Intent(MenuPrincipal.this, ConteoCalorias.class);
                                    startActivity(intent);
                                }
                                unaVez++;
                            }

                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }else
                    Toast.makeText(MenuPrincipal.this, "No tiene la version correspondiente", Toast.LENGTH_SHORT).show();
            }
        });

        //Botón para cuando cerramos sesión
        ImageButton botonSalir = (ImageButton) findViewById(R.id.buttonSalir);
        botonSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*AlertDialog.Builder builder = new AlertDialog.Builder(MenuPrincipal.this);
                builder.setTitle("ALERTA");
                builder.setMessage("¿Desea cerrar sesión?");        // add the buttons
                builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        cerrarSesion();
                        Intent intent = new Intent(MenuPrincipal.this, Login.class);
                        startActivity(intent);
                        finishAndRemoveTask();

                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();*/

                finishAffinity();
                preferences.edit().clear().apply();

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
                if(version==1){
                    Intent intent = new Intent(MenuPrincipal.this, Dietas.class);
                    startActivity(intent);
                }else
                    Toast.makeText(MenuPrincipal.this, "No tiene la version correspondiente", Toast.LENGTH_SHORT).show();

            }
        });
    }

    //Método para cerrar sesión, es decir, para limpiar las shared preferences.
    private void cerrarSesion() {
        //super.onDestroy();
        finish();
        //preferences.edit().clear().apply();
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