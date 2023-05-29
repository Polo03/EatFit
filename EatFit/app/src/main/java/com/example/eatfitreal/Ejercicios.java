package com.example.eatfitreal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class Ejercicios extends AppCompatActivity {

    private SharedPreferences preferences;
    private TextView titulo_ejercicios;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ejercicios);
        //Recogemos los preferences si ha decidido no cerrar sesión
        preferences=getSharedPreferences("Preferences",MODE_PRIVATE);
        MenuPrincipal menuPrincipal = new MenuPrincipal();
        int i = getIntent().getIntExtra("count", 0);
        String item=getIntent().getStringExtra("item");
        menuPrincipal.setButtonvalor(0);
        rellenaListView(i,item);
    }

    public void rellenaListView(int i, String item){
        titulo_ejercicios=(TextView)findViewById(R.id.textView_titulo_ejercicios);
        Login l=new Login();
        String nickStr="";
        if(preferences.getString("nick", null)==null)
            nickStr=l.ultimoUsuarioLogeado();
        else
            nickStr=preferences.getString("nick", null);
        String finalNickStr = nickStr;
        ListView listView = findViewById(R.id.lista_ejercicios);
        listView.setDivider(null);
        DatabaseReference myRef= FirebaseDatabase.getInstance().getReference();
        myRef.child("Cuestionario").addValueEventListener(new ValueEventListener() {
            String funcion="";
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    if(dataSnapshot.child("nick").getValue().toString().equals(finalNickStr)){
                        funcion=dataSnapshot.child("objetivo4").getValue().toString();
                    }
                }
                POJO[] datos=null;
                //Si quiere adelgazar
                if (funcion.equals("Adelgazar")) {
                    //Si quiere rutina de pecho y brazos
                    if (i == 1) {
                        titulo_ejercicios.setText("Ejercicios de Pecho y Brazo");
                        //Si quiere nivel principiante
                        if (item.equals("Principiante")) {
                            datos = new POJO[]{
                                    new POJO(R.drawable.ejercicio1, "Flexiones con extension de cadera", "3 series de 10 repeticiones."),
                                    new POJO(R.drawable.ejercicio2, "Press de banca con barra", "3 series de 10 repeticiones."),
                                    new POJO(R.drawable.ejercicio3, "Elevaciones laterales con mancuernas", "3 series de 10 repeticiones."),
                                    new POJO(R.drawable.ejercicio4, "Extensiones de tríceps con mancuernas", "3 series de 10 repeticiones."),
                                    new POJO(R.drawable.ejercicio5, "Curl de bíceps con mancuernas", "3 series de 10 repeticiones.")
                            };
                        }
                        //Si quiere nivel intermedio
                        if (item.equals("Intermedio")) {
                            datos = new POJO[]{
                                    new POJO(R.drawable.ejercicio6, "Flexiones clásicas", "3 series de 12 repeticiones."),
                                    new POJO(R.drawable.ejercicio7, "Fondos en paralelas", "3 series de 10 repeticiones."),
                                    new POJO(R.drawable.ejercicio8, "Press militar con mancuernas", "3 series de 12 repeticiones."),
                                    new POJO(R.drawable.ejercicio9, "Curl de bíceps con barra", "3 series de 12 repeticiones."),
                                    new POJO(R.drawable.ejercicio4, "Extensiones de tríceps con mancuernas", "3 series de 12 repeticiones.")
                            };
                        }
                        //Si quiere nivel avanzado
                        if (item.equals("Avanzado")) {
                            datos = new POJO[]{
                                    new POJO(R.drawable.ejercicio11, "Flexiones de brazos con TRX", "3 series de 15 repeticiones."),
                                    new POJO(R.drawable.ejercicio12, "Dominadas con agarre estrecho", "3 series de 8 repeticiones."),
                                    new POJO(R.drawable.ejercicio2, "Press de banca con barra", "3 series de 12 repeticiones."),
                                    new POJO(R.drawable.ejercicio7, "Fondos en paralelas", " 4 series de 10 repeticiones."),
                                    new POJO(R.drawable.ejercicio5, "Curl de bíceps con mancuerna", "3 series de 15 repeticiones.")
                            };
                        }
                    }
                    //Si quiere rutina de abdominales
                    if (i == 2) {
                        titulo_ejercicios.setText("Ejercicios de Abdominales");
                        if (item.equals("Principiante")) {
                            datos = new POJO[]{
                                    new POJO(R.drawable.ejercicio16, "Equilibrio sobre pelotas de pilates", "3 series de 15 repeticiones"),
                                    new POJO(R.drawable.ejercicio17, "Tirón principal con mancuerna", "3 series de 30 segundos"),
                                    new POJO(R.drawable.ejercicio18, "Equilibrio sobre glúteos", "3 series de 10 repeticiones"),
                                    new POJO(R.drawable.ejercicio19, "Caida adelante con balón medicinal", "3 series de 15 repeticiones"),
                                    new POJO(R.drawable.ejercicio20, "Dominadas boca arriba con agarre supino", "3 series de 20 repeticiones")
                            };
                        }
                        //Si quiere nivel intermedio
                        if (item.equals("Intermedio")) {
                            datos = new POJO[]{
                                    new POJO(R.drawable.ejercicio21, "Encogimientos", "3 series de 12 repeticiones"),
                                    new POJO(R.drawable.ejercicio22, "Plancha horizontal isométrica", "3 series de 20 repeticiones"),
                                    new POJO(R.drawable.ejercicio17, "Tirón principal con mancuerna", "3 series de 14 repeticiones"),
                                    new POJO(R.drawable.ejercicio19, "Caida adelante con balón medicinal", "4 series de 12 repeticiones"),
                                    new POJO(R.drawable.ejercicio25, "Giros de cintura con barra", "3 series de 15 repeticiones")
                            };
                        }
                        //Si quiere nivel avanzado
                        if (item.equals("Avanzado")) {
                            datos = new POJO[]{
                                    new POJO(R.drawable.ejercicio26, "Plancha y patada lateral", "3 series de 30 segundos"),
                                    new POJO(R.drawable.ejercicio20, "Dominadas boca arriba con agarre supino", "3 series de 10 repeticiones"),
                                    new POJO(R.drawable.ejercicio25, "Giros de cintura con barra", "3 series de 8 repeticiones"),
                                    new POJO(R.drawable.ejercicio16, "Equilibrio sobre pelotas de pilates", "3 series de 20 repeticiones"),
                                    new POJO(R.drawable.ejercicio27, "Abdominales con roller", "3 series de 10 repeticiones")
                            };
                        }
                    }
                    //Si quiere rutina de hombros y espalda
                    if (i == 3) {
                        titulo_ejercicios.setText("Ejercicios de Hombros y Espalda");
                        //Si quiere nivel principiante
                        if (item.equals("Principiante")) {
                            datos = new POJO[]{
                                    new POJO(R.drawable.ejercicio28, "Remo con mancuernas", "3 series de 12 repeticiones"),
                                    new POJO(R.drawable.ejercicio3, "Elevaciones laterales con mancuernas", "3 series de 12 repeticiones"),
                                    new POJO(R.drawable.ejercicio30, "Extension de tronco y aperturas con mancuernas", "3 series de 12 repeticiones"),
                                    new POJO(R.drawable.ejercicio29, "Dominadas", "3 series de 8 repeticiones"),
                                    new POJO(R.drawable.ejercicio8, "Press militar con mancuernas", "3 series de 12 repeticiones")
                            };
                        }
                        //Si quiere nivel intermedio
                        if (item.equals("Intermedio")) {
                            datos = new POJO[]{
                                    new POJO(R.drawable.ejercicio31, "Remo con polea sentado en pelota de pilates", "3 series de 10 repeticiones"),
                                    new POJO(R.drawable.ejercicio8, "Press militar con mancuernas", "3 series de 10 repeticiones"),
                                    new POJO(R.drawable.ejercicio32, "Cruces con poleas", "3 series de 8 repeticiones"),
                                    new POJO(R.drawable.ejercicio33, "Elevaciones frontales con mancuernas de pie", "3 series de 12 repeticiones"),
                                    new POJO(R.drawable.ejercicio28, "Remo con mancuernas", "3 series de 12 repeticiones")
                            };
                        }
                        //Si quiere nivel avanzado
                        if (item.equals("Avanzado")) {
                            datos = new POJO[]{
                                    new POJO(R.drawable.ejercicio34, "Fondos de hombro bocaabajo", "3 series de 8 repeticiones"),
                                    new POJO(R.drawable.ejercicio8, "Press militar con mancuernas", "3 series de 8 repeticiones"),
                                    new POJO(R.drawable.ejercicio30, "Extension de tronco y aperturas con mancuernas", "3 series de 8 repeticiones"),
                                    new POJO(R.drawable.ejercicio33, "Elevaciones frontales con mancuernas de pie", "3 series de 10 repeticiones"),
                                    new POJO(R.drawable.ejercicio35, "Flexiones de brazo con palmada", "3 series de 12 repeticiones")
                            };
                        }
                    }
                    //Si quiere rutina de piernas
                    if (i == 4) {
                        titulo_ejercicios.setText("Ejercicios de Piernas");
                        //Si quiere nivel principiante
                        if (item.equals("Principiante")) {
                            datos = new POJO[]{
                                    new POJO(R.drawable.ejercicio36, "Media-Sentadilla", "3 series de 12 repeticiones"),
                                    new POJO(R.drawable.ejercicio37, "Zancadas lateral con deslizador", "3 series de 12 repeticiones"),
                                    new POJO(R.drawable.ejercicio38, "Elevaciones de una pierna estirada", "3 series de 12 repeticiones"),
                                    new POJO(R.drawable.ejercicio39, "Puentes de glúteos", "4 series de 12 repeticiones"),
                                    new POJO(R.drawable.ejercicio40, "Extensiones de piernas en máquina", "4 series de 10 repeticiones")
                            };
                        }
                        //Si quiere nivel intermedio
                        if (item.equals("Intermedio")) {
                            datos = new POJO[]{
                                    new POJO(R.drawable.ejercicio36, "Media-Sentadilla", "4 series de 12 repeticiones"),
                                    new POJO(R.drawable.ejercicio43, "Salto vertical con impulso", "4 series de 10 repeticiones"),
                                    new POJO(R.drawable.ejercicio42, "Prensa de piernas", "4 series de 12 repeticiones"),
                                    new POJO(R.drawable.ejercicio41, "Peso muerto", "3 series de 8 repeticiones"),
                                    new POJO(R.drawable.ejercicio44, "Zancadas con mancuernas", "4 series de 12 repeticiones")
                            };
                        }
                        //Si quiere nivel avanzado
                        if (item.equals("Avanzado")) {
                            datos = new POJO[]{
                                    new POJO(R.drawable.ejercicio44, "Zancadas con mancuernas", "4 series de 12 repeticiones"),
                                    new POJO(R.drawable.ejercicio46, "Peso muerto sumo con barra", "3 series de 10 repeticiones"),
                                    new POJO(R.drawable.ejercicio45, "Extendsion de gemelos en máquina", "4 series de 12 repeticiones"),
                                    new POJO(R.drawable.ejercicio42, "Prensa de piernas", "4 series de 10 repeticiones"),
                                    new POJO(R.drawable.ejercicio39, "Puentes de glúteos", "4 series de 12 repeticiones")
                            };
                        }
                    }
                    //Si quiere estar en volumen
                } else {
                    //Si quiere rutina de pecho y brazos
                    if (i == 1) {
                        titulo_ejercicios.setText("Ejercicios de Pecho y Brazo");
                        //Si quiere nivel principiante
                        if (item.equals("Principiante")) {
                            datos = new POJO[]{
                                    new POJO(R.drawable.ejercicio1, "Flexiones con extension de cadera", "3 series de 10 repeticiones."),
                                    new POJO(R.drawable.ejercicio2, "Press de banca con barra", "3 series de 10 repeticiones."),
                                    new POJO(R.drawable.ejercicio3, "Elevaciones laterales con mancuernas", "3 series de 10 repeticiones."),
                                    new POJO(R.drawable.ejercicio4, "Extensiones de tríceps con mancuernas", "3 series de 10 repeticiones."),
                                    new POJO(R.drawable.ejercicio5, "Curl de bíceps con mancuernas", "3 series de 10 repeticiones.")
                            };
                        }
                        //Si quiere nivel intermedio
                        if (item.equals("Intermedio")) {
                            datos = new POJO[]{
                                    new POJO(R.drawable.ejercicio6, "Flexiones clásicas", "3 series de 12 repeticiones."),
                                    new POJO(R.drawable.ejercicio7, "Fondos en paralelas", "3 series de 10 repeticiones."),
                                    new POJO(R.drawable.ejercicio8, "Press militar con mancuernas", "3 series de 12 repeticiones."),
                                    new POJO(R.drawable.ejercicio9, "Curl de bíceps con barra", "3 series de 12 repeticiones."),
                                    new POJO(R.drawable.ejercicio4, "Extensiones de tríceps con mancuernas", "3 series de 12 repeticiones.")
                            };
                        }
                        //Si quiere nivel avanzado
                        if (item.equals("Avanzado")) {
                            datos = new POJO[]{
                                    new POJO(R.drawable.ejercicio11, "Flexiones de brazos con TRX", "3 series de 15 repeticiones."),
                                    new POJO(R.drawable.ejercicio12, "Dominadas con agarre estrecho", "3 series de 8 repeticiones."),
                                    new POJO(R.drawable.ejercicio2, "Press de banca con barra", "3 series de 12 repeticiones."),
                                    new POJO(R.drawable.ejercicio7, "Fondos en paralelas", " 4 series de 10 repeticiones."),
                                    new POJO(R.drawable.ejercicio5, "Curl de bíceps con mancuerna", "3 series de 15 repeticiones.")
                            };
                        }
                    }
                    //Si quiere rutina de abdominales
                    if (i == 2) {
                        titulo_ejercicios.setText("Ejercicios de Abdominales");
                        if (item.equals("Principiante")) {
                            datos = new POJO[]{
                                    new POJO(R.drawable.ejercicio16, "Equilibrio sobre pelotas de pilates", "3 series de 15 repeticiones"),
                                    new POJO(R.drawable.ejercicio17, "Tirón principal con mancuerna", "3 series de 30 segundos"),
                                    new POJO(R.drawable.ejercicio18, "Equilibrio sobre glúteos", "3 series de 10 repeticiones"),
                                    new POJO(R.drawable.ejercicio19, "Caida adelante con balón medicinal", "3 series de 15 repeticiones"),
                                    new POJO(R.drawable.ejercicio20, "Dominadas boca arriba con agarre supino", "3 series de 20 repeticiones")
                            };
                        }
                        //Si quiere nivel intermedio
                        if (item.equals("Intermedio")) {
                            datos = new POJO[]{
                                    new POJO(R.drawable.ejercicio21, "Encogimientos", "3 series de 12 repeticiones"),
                                    new POJO(R.drawable.ejercicio22, "Plancha horizontal isométrica", "3 series de 20 repeticiones"),
                                    new POJO(R.drawable.ejercicio17, "Tirón principal con mancuerna", "3 series de 14 repeticiones"),
                                    new POJO(R.drawable.ejercicio19, "Caida adelante con balón medicinal", "4 series de 12 repeticiones"),
                                    new POJO(R.drawable.ejercicio25, "Giros de cintura con barra", "3 series de 15 repeticiones")
                            };
                        }
                        //Si quiere nivel avanzado
                        if (item.equals("Avanzado")) {
                            datos = new POJO[]{
                                    new POJO(R.drawable.ejercicio26, "Plancha y patada lateral", "3 series de 30 segundos"),
                                    new POJO(R.drawable.ejercicio20, "Dominadas boca arriba con agarre supino", "3 series de 10 repeticiones"),
                                    new POJO(R.drawable.ejercicio25, "Giros de cintura con barra", "3 series de 8 repeticiones"),
                                    new POJO(R.drawable.ejercicio16, "Equilibrio sobre pelotas de pilates", "3 series de 20 repeticiones"),
                                    new POJO(R.drawable.ejercicio27, "Abdominales con roller", "3 series de 10 repeticiones")
                            };
                        }
                    }
                    //Si quiere rutina de hombros y espalda
                    if (i == 3) {
                        titulo_ejercicios.setText("Ejercicios de Hombros y Espalda");
                        //Si quiere nivel principiante
                        if (item.equals("Principiante")) {
                            datos = new POJO[]{
                                    new POJO(R.drawable.ejercicio28, "Remo con mancuernas", "3 series de 12 repeticiones"),
                                    new POJO(R.drawable.ejercicio3, "Elevaciones laterales con mancuernas", "3 series de 12 repeticiones"),
                                    new POJO(R.drawable.ejercicio30, "Extension de tronco y aperturas con mancuernas", "3 series de 12 repeticiones"),
                                    new POJO(R.drawable.ejercicio29, "Dominadas", "3 series de 8 repeticiones"),
                                    new POJO(R.drawable.ejercicio8, "Press militar con mancuernas", "3 series de 12 repeticiones")
                            };
                        }
                        //Si quiere nivel intermedio
                        if (item.equals("Intermedio")) {
                            datos = new POJO[]{
                                    new POJO(R.drawable.ejercicio31, "Remo con polea sentado en pelota de pilates", "3 series de 10 repeticiones"),
                                    new POJO(R.drawable.ejercicio8, "Press militar con mancuernas", "3 series de 10 repeticiones"),
                                    new POJO(R.drawable.ejercicio32, "Cruces con poleas", "3 series de 8 repeticiones"),
                                    new POJO(R.drawable.ejercicio33, "Elevaciones frontales con mancuernas de pie", "3 series de 12 repeticiones"),
                                    new POJO(R.drawable.ejercicio28, "Remo con mancuernas", "3 series de 12 repeticiones")
                            };
                        }
                        //Si quiere nivel avanzado
                        if (item.equals("Avanzado")) {
                            datos = new POJO[]{
                                    new POJO(R.drawable.ejercicio34, "Fondos de hombro bocaabajo", "3 series de 8 repeticiones"),
                                    new POJO(R.drawable.ejercicio8, "Press militar con mancuernas", "3 series de 8 repeticiones"),
                                    new POJO(R.drawable.ejercicio30, "Extension de tronco y aperturas con mancuernas", "3 series de 8 repeticiones"),
                                    new POJO(R.drawable.ejercicio33, "Elevaciones frontales con mancuernas de pie", "3 series de 10 repeticiones"),
                                    new POJO(R.drawable.ejercicio35, "Flexiones de brazo con palmada", "3 series de 12 repeticiones")
                            };
                        }
                    }
                    //Si quiere rutina de piernas
                    if (i == 4) {
                        titulo_ejercicios.setText("Ejercicios de Piernas");
                        //Si quiere nivel principiante
                        if (item.equals("Principiante")) {
                            datos = new POJO[]{
                                    new POJO(R.drawable.ejercicio36, "Media-Sentadilla", "3 series de 12 repeticiones"),
                                    new POJO(R.drawable.ejercicio37, "Zancadas lateral con deslizador", "3 series de 12 repeticiones"),
                                    new POJO(R.drawable.ejercicio38, "Elevaciones de una pierna estirada", "3 series de 12 repeticiones"),
                                    new POJO(R.drawable.ejercicio39, "Puentes de glúteos", "4 series de 12 repeticiones"),
                                    new POJO(R.drawable.ejercicio40, "Extensiones de piernas en máquina", "4 series de 10 repeticiones")
                            };
                        }
                        //Si quiere nivel intermedio
                        if (item.equals("Intermedio")) {
                            datos = new POJO[]{
                                    new POJO(R.drawable.ejercicio36, "Media-Sentadilla", "4 series de 12 repeticiones"),
                                    new POJO(R.drawable.ejercicio43, "Salto vertical con impulso", "4 series de 10 repeticiones"),
                                    new POJO(R.drawable.ejercicio42, "Prensa de piernas", "4 series de 12 repeticiones"),
                                    new POJO(R.drawable.ejercicio41, "Peso muerto", "3 series de 8 repeticiones"),
                                    new POJO(R.drawable.ejercicio44, "Zancadas con mancuernas", "4 series de 12 repeticiones")
                            };
                        }
                        //Si quiere nivel avanzado
                        if (item.equals("Avanzado")) {
                            datos = new POJO[]{
                                    new POJO(R.drawable.ejercicio44, "Zancadas con mancuernas", "4 series de 12 repeticiones"),
                                    new POJO(R.drawable.ejercicio46, "Peso muerto sumo con barra", "3 series de 10 repeticiones"),
                                    new POJO(R.drawable.ejercicio45, "Extendsion de gemelos en máquina", "4 series de 12 repeticiones"),
                                    new POJO(R.drawable.ejercicio42, "Prensa de piernas", "4 series de 10 repeticiones"),
                                    new POJO(R.drawable.ejercicio39, "Puentes de glúteos", "4 series de 12 repeticiones")
                            };
                        }
                    }
                }
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        startActivity(new Intent(Ejercicios.this, PopupCronometro.class));
                    }
                });
                Adaptador miAdaptador = new Adaptador(getApplicationContext(), datos);
                listView.setAdapter(miAdaptador);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}