package com.example.eatfitreal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Ejercicios extends AppCompatActivity {

    private SharedPreferences preferences;

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
        ListView listView = findViewById(R.id.lista_ejercicios);
        DatabaseReference myRef= FirebaseDatabase.getInstance().getReference();
        //Si ha decidido no cerrar sesión
        if(noCierraSesion()) {
            myRef.child("Cuestionario").addValueEventListener(new ValueEventListener() {
                String nick=preferences.getString("nick",null);
                String funcion="";
                POJO[] datos=null;
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                        if(dataSnapshot.child("nick").getValue().toString().equals(nick)){
                            funcion=dataSnapshot.child("objetivo4").getValue().toString();
                        }
                    }
                    //Si quiere estar en definicion
                    if(funcion.equals("Adelgazar")){
                        //Si quiere rutina de pecho y brazos
                        if(i==1){
                            //Si quiere nivel principiante
                            if(item.equals("Principiante")){
                                datos=new POJO[]{
                                        new POJO(R.drawable.curlbiceps, "Flexiones de rodillas: 3 series de 10 repeticiones."),
                                        new POJO(R.drawable.calendario, "Press de mancuernas: 3 series de 10 repeticiones."),
                                        new POJO(R.drawable.datospersonales, "Elevaciones laterales con mancuernas: 3 series de 10 repeticiones."),
                                        new POJO(R.drawable.foro, "Extensiones de tríceps con mancuernas: 3 series de 10 repeticiones."),
                                        new POJO(R.drawable.foro, "Curl de bíceps con mancuernas: 3 series de 10 repeticiones.")
                                };
                            }
                            //Si quiere nivel intermedio
                            if(item.equals("Intermedio")){
                                datos=new POJO[]{
                                        new POJO(R.drawable.dietas, "Flexiones clásicas: 3 series de 12 repeticiones."),
                                        new POJO(R.drawable.calendario, "Fondos en paralelas: 3 series de 10 repeticiones."),
                                        new POJO(R.drawable.datospersonales, "Press militar con mancuernas: 3 series de 12 repeticiones."),
                                        new POJO(R.drawable.foro, "Curl de bíceps con barra: 3 series de 12 repeticiones."),
                                        new POJO(R.drawable.foro, "Extensiones de tríceps con mancuernas (por encima de la cabeza): 3 series de 12 repeticiones.")
                                };
                            }
                            //Si quiere nivel avanzado
                            if(item.equals("Avanzado")){
                                datos=new POJO[]{
                                        new POJO(R.drawable.dietas, "Flexiones en diamante: 3 series de 15 repeticiones."),
                                        new POJO(R.drawable.calendario, "Dominadas con agarre estrecho: 3 series de 8 repeticiones."),
                                        new POJO(R.drawable.datospersonales, "Press de banca con mancuernas: 3 series de 12 repeticiones."),
                                        new POJO(R.drawable.foro, "Fondos entre bancos (con peso añadido si se desea): 3 series de 10 repeticiones."),
                                        new POJO(R.drawable.foro, "Curl de bíceps concentrado con mancuerna: 3 series de 12 repeticiones.")
                                };
                            }
                        }
                        //Si quiere rutina de abdominales
                        if(i==2){
                            if(item.equals("Principiante")){
                                datos=new POJO[]{
                                        new POJO(R.drawable.dietas, "Crunches (3 series de 15 repeticiones)."),
                                        new POJO(R.drawable.calendario, "Plank (3 series de 30 segundos)."),
                                        new POJO(R.drawable.datospersonales, "Leg Raises (3 series de 10 repeticiones)."),
                                        new POJO(R.drawable.foro, "Russian Twists (3 series de 15 repeticiones)."),
                                        new POJO(R.drawable.foro, "Bicycle Crunches (3 series de 20 repeticiones).")
                                };
                            }
                            //Si quiere nivel intermedio
                            if(item.equals("Intermedio")){
                                datos=new POJO[]{
                                        new POJO(R.drawable.dietas, "Mountain Climbers (3 series de 30 segundos)."),
                                        new POJO(R.drawable.calendario, "Flutter Kicks (3 series de 20 repeticiones)."),
                                        new POJO(R.drawable.datospersonales, "Reverse Crunches (3 series de 12 repeticiones)."),
                                        new POJO(R.drawable.foro, "Side Plank (3 series de 30 segundos cada lado)."),
                                        new POJO(R.drawable.foro, "Scissor Kicks (3 series de 15 repeticiones).")
                                };
                            }
                            //Si quiere nivel avanzado
                            if(item.equals("Avanzado")){
                                datos=new POJO[]{
                                        new POJO(R.drawable.dietas, "V-Sit Crunches (3 series de 10 repeticiones)."),
                                        new POJO(R.drawable.calendario, "Dragon Flags (3 series de 6 repeticiones)."),
                                        new POJO(R.drawable.datospersonales, "Hanging Leg Raises (3 series de 8 repeticiones)."),
                                        new POJO(R.drawable.foro, "Plank with Knee Taps (3 series de 20 repeticiones)."),
                                        new POJO(R.drawable.foro, "Windshield Wipers (3 series de 10 repeticiones).")
                                };
                            }
                        }
                        //Si quiere rutina de hombros y espalda
                        if(i==3){
                            //Si quiere nivel principiante
                            if(item.equals("Principiante")){
                                datos=new POJO[]{
                                        new POJO(R.drawable.dietas, "Remo con mancuernas (3 series de 12 repeticiones)."),
                                        new POJO(R.drawable.calendario, "Elevaciones laterales con mancuernas (3 series de 12 repeticiones)."),
                                        new POJO(R.drawable.datospersonales, "Face pulls con polea (3 series de 12 repeticiones)."),
                                        new POJO(R.drawable.foro, "Dominadas asistidas con banda elástica (3 series de 8 repeticiones)."),
                                        new POJO(R.drawable.foro, "Press militar con mancuernas (3 series de 12 repeticiones).")
                                };
                            }
                            //Si quiere nivel intermedio
                            if(item.equals("Intermedio")){
                                datos=new POJO[]{
                                        new POJO(R.drawable.dietas, "Remo con barra (3 series de 10 repeticiones)."),
                                        new POJO(R.drawable.calendario, "Press de hombros con mancuernas (3 series de 10 repeticiones)."),
                                        new POJO(R.drawable.datospersonales, "Pull-ups con agarre amplio (3 series de 8 repeticiones)."),
                                        new POJO(R.drawable.foro, "Elevaciones frontales con mancuernas (3 series de 12 repeticiones)."),
                                        new POJO(R.drawable.foro, "Face pulls con cable en posición de rodillas (3 series de 12 repeticiones).")
                                };
                            }
                            //Si quiere nivel avanzado
                            if(item.equals("Avanzado")){
                                datos=new POJO[]{
                                        new POJO(R.drawable.dietas, "Peso muerto rumano (3 series de 8 repeticiones)."),
                                        new POJO(R.drawable.calendario, "Clean and press con barra (3 series de 8 repeticiones)."),
                                        new POJO(R.drawable.datospersonales, "Dominadas con agarre estrecho (3 series de 8 repeticiones)."),
                                        new POJO(R.drawable.foro, "Press de hombros con barra (3 series de 10 repeticiones)."),
                                        new POJO(R.drawable.foro, "Elevaciones laterales con mancuernas en posición sentada (3 series de 12 repeticiones).")
                                };
                            }
                        }
                        //Si quiere rutina de piernas
                        if(i==4){
                            //Si quiere nivel principiante
                            if(item.equals("Principiante")){
                                datos=new POJO[]{
                                        new POJO(R.drawable.dietas, "Sentadillas con peso corporal."),
                                        new POJO(R.drawable.calendario, "Zancadas hacia adelante con peso corporal."),
                                        new POJO(R.drawable.datospersonales, "Elevaciones de pantorrilla en posición de pie."),
                                        new POJO(R.drawable.foro, "Puentes de glúteos."),
                                        new POJO(R.drawable.foro, "Extensiones de piernas en máquina.")
                                };
                            }
                            //Si quiere nivel intermedio
                            if(item.equals("Intermedio")){
                                datos=new POJO[]{
                                        new POJO(R.drawable.dietas, "Sentadillas con barra."),
                                        new POJO(R.drawable.calendario, "Zancadas con mancuernas."),
                                        new POJO(R.drawable.datospersonales, "Prensa de piernas."),
                                        new POJO(R.drawable.foro, "Peso muerto con barra."),
                                        new POJO(R.drawable.foro, "Curl de piernas acostado en máquina.")
                                };
                            }
                            //Si quiere nivel avanzado
                            if(item.equals("Avanzado")){
                                datos=new POJO[]{
                                        new POJO(R.drawable.dietas, "Sentadillas con salto."),
                                        new POJO(R.drawable.calendario, "Paseo del granjero con pesas."),
                                        new POJO(R.drawable.datospersonales, "Prensa hack."),
                                        new POJO(R.drawable.foro, "Peso muerto rumano con mancuernas."),
                                        new POJO(R.drawable.foro, "Pliometría de caja.")
                                };
                            }
                        }
                    //Si quiere estar en volumen
                    }else{
                        //Si quiere rutina de pecho y brazos
                        if(i==1){
                            //Si quiere nivel principiante
                            if(item.equals("Principiante")){
                                datos=new POJO[]{
                                        new POJO(R.drawable.dietas, "Flexiones de pared → Haz 3 series de 10 repeticiones cada una."),
                                        new POJO(R.drawable.calendario, "Flexiones de rodillas → Haz 3 series de 10 repeticiones cada una."),
                                        new POJO(R.drawable.datospersonales, "Curl de bíceps con mancuernas → Haz 3 series de 12 repeticiones cada una."),
                                        new POJO(R.drawable.foro, "Press de banca con mancuernas → Haz 3 series de 12 repeticiones cada una."),
                                        new POJO(R.drawable.foro, "Flexiones diamante → Haz 3 series de 10 repeticiones cada una.")
                                };
                            }
                            //Si quiere nivel intermedio
                            if(item.equals("Intermedio")){
                                datos=new POJO[]{
                                        new POJO(R.drawable.dietas, "Flexiones de mano estrecha → Haz 4 series de 12 repeticiones cada una."),
                                        new POJO(R.drawable.calendario, "Curl de bíceps con barra → Haz 4 series de 10 repeticiones cada una."),
                                        new POJO(R.drawable.datospersonales, "Press de banca con barra → Haz 4 series de 10 repeticiones cada una."),
                                        new POJO(R.drawable.foro, "Pull-ups →  Haz 4 series de 8 repeticiones cada una."),
                                        new POJO(R.drawable.foro, "Dips → Haz 4 series de 10 repeticiones cada una.")
                                };
                            }
                            //Si quiere nivel avanzado
                            if(item.equals("Avanzado")){
                                datos=new POJO[]{
                                        new POJO(R.drawable.dietas, "Curl de bíceps con barra (4 series de 8 repeticiones)."),
                                        new POJO(R.drawable.calendario, "Curl alterno de bíceps con mancuernas (3 series de 10 repeticiones)."),
                                        new POJO(R.drawable.datospersonales, "Press de banca inclinado con mancuernas (3 series de 10 repeticiones)."),
                                        new POJO(R.drawable.foro, "Cruce de cables en polea (4 series de 8 repeticiones)."),
                                        new POJO(R.drawable.foro, "Aperturas con mancuernas en banco plano (3 series de 12 repeticiones).")
                                };
                            }
                        }
                        //Si quiere rutina de abdominales
                        if(i==2){
                            if(item.equals("Principiante")){
                                datos=new POJO[]{
                                        new POJO(R.drawable.dietas, "Crunches con peso (3 series de 12 repeticiones)."),
                                        new POJO(R.drawable.calendario, "Plank con elevación de pierna (3 series de 10 repeticiones por pierna)."),
                                        new POJO(R.drawable.datospersonales, "Abdominales con pelota suiza (3 series de 15 repeticiones)."),
                                        new POJO(R.drawable.foro, "Russian Twists con peso (3 series de 12 repeticiones)."),
                                        new POJO(R.drawable.foro, "Levantamiento de piernas con peso (3 series de 10 repeticiones).")
                                };
                            }
                            //Si quiere nivel intermedio
                            if(item.equals("Intermedio")){
                                datos=new POJO[]{
                                        new POJO(R.drawable.dietas, "Plank con elevación de pierna y brazo opuestos (3 series de 10 repeticiones por lado)."),
                                        new POJO(R.drawable.calendario, "Abdominales en \"V\" con peso (3 series de 12 repeticiones)."),
                                        new POJO(R.drawable.datospersonales, "Side Plank con elevación de cadera (3 series de 10 repeticiones por lado)."),
                                        new POJO(R.drawable.foro, "Bicycle Crunches con peso (3 series de 15 repeticiones)."),
                                        new POJO(R.drawable.foro, "Levantamiento de piernas colgado en barra con peso (3 series de 8 repeticiones).")
                                };
                            }
                            //Si quiere nivel avanzado
                            if(item.equals("Avanzado")){
                                datos=new POJO[]{
                                        new POJO(R.drawable.dietas, "Dragon Flags (3 series de 5 repeticiones)."),
                                        new POJO(R.drawable.calendario, "Abdominales en \"V\" colgado en barra con peso (3 series de 12 repeticiones)."),
                                        new POJO(R.drawable.datospersonales, "Plank con elevación de pierna, brazo opuesto y peso (3 series de 8 repeticiones por lado)."),
                                        new POJO(R.drawable.foro, "Windshield Wipers con peso (3 series de 10 repeticiones por lado)."),
                                        new POJO(R.drawable.foro, "Levantamiento de piernas colgado en barra con giro lateral (3 series de 8 repeticiones por lado).")
                                };
                            }
                        }
                        //Si quiere rutina de hombros y espalda
                        if(i==3){
                            //Si quiere nivel principiante
                            if(item.equals("Principiante")){
                                datos=new POJO[]{
                                        new POJO(R.drawable.dietas, "Remo con mancuernas: 3 series de 12 repeticiones."),
                                        new POJO(R.drawable.calendario, "Elevaciones laterales con mancuernas: 3 series de 12 repeticiones."),
                                        new POJO(R.drawable.datospersonales, "Dominadas asistidas con banda elástica: 3 series de 10 repeticiones."),
                                        new POJO(R.drawable.foro, "Press militar con mancuernas: 3 series de 12 repeticiones."),
                                        new POJO(R.drawable.foro, "Face pull con polea: 3 series de 12 repeticiones.")
                                };
                            }
                            //Si quiere nivel intermedio
                            if(item.equals("Intermedio")){
                                datos=new POJO[]{
                                        new POJO(R.drawable.dietas, "Remo con barra T: 4 series de 10 repeticiones."),
                                        new POJO(R.drawable.calendario, "Press Arnold con mancuernas: 4 series de 10 repeticiones."),
                                        new POJO(R.drawable.datospersonales, "Pull-ups con agarre amplio: 4 series de 8 repeticiones."),
                                        new POJO(R.drawable.foro, "Elevaciones frontales con mancuernas: 4 series de 10 repeticiones."),
                                        new POJO(R.drawable.foro, "Remo horizontal en máquina: 4 series de 12 repeticiones.")
                                };
                            }
                            //Si quiere nivel avanzado
                            if(item.equals("Avanzado")){
                                datos=new POJO[]{
                                        new POJO(R.drawable.dietas, "Dominadas con peso: 5 series de 6 repeticiones."),
                                        new POJO(R.drawable.calendario, "Press militar con barra: 5 series de 8 repeticiones.."),
                                        new POJO(R.drawable.datospersonales, "Remo Pendlay: 5 series de 8 repeticiones."),
                                        new POJO(R.drawable.foro, "Elevaciones laterales con mancuernas: 5 series de 10 repeticiones."),
                                        new POJO(R.drawable.foro, "Face pull con cuerda: 5 series de 12 repeticiones.")
                                };
                            }
                        }
                        //Si quiere rutina de piernas
                        if(i==4){
                            //Si quiere nivel principiante
                            if(item.equals("Principiante")){
                                datos=new POJO[]{
                                        new POJO(R.drawable.dietas, "Sentadillas con barra."),
                                        new POJO(R.drawable.calendario, "Prensa de piernas."),
                                        new POJO(R.drawable.datospersonales, "Extensiones de pierna en máquina."),
                                        new POJO(R.drawable.foro, "Elevaciones de talón sentado en máquina."),
                                        new POJO(R.drawable.foro, "Zancadas con mancuernas.")
                                };
                            }
                            //Si quiere nivel intermedio
                            if(item.equals("Intermedio")){
                                datos=new POJO[]{
                                        new POJO(R.drawable.dietas, "Sentadillas búlgaras con mancuernas."),
                                        new POJO(R.drawable.calendario, "Peso muerto rumano con barra."),
                                        new POJO(R.drawable.datospersonales, "Prensa hack."),
                                        new POJO(R.drawable.foro, "Curl de pierna acostado en máquina."),
                                        new POJO(R.drawable.foro, "Hip thrust con barra.")
                                };
                            }
                            //Si quiere nivel avanzado
                            if(item.equals("Avanzado")){
                                datos=new POJO[]{
                                        new POJO(R.drawable.dietas, "Sentadillas con salto."),
                                        new POJO(R.drawable.calendario, "Paseo del granjero con pesas."),
                                        new POJO(R.drawable.datospersonales, "Prensa de piernas con una sola pierna."),
                                        new POJO(R.drawable.foro, "Zancadas con salto."),
                                        new POJO(R.drawable.foro, "Pliometría de caja.")
                                };
                            }
                        }
                    }
                    Adaptador miAdaptador = new Adaptador(getApplicationContext(), datos);
                    listView.setAdapter(miAdaptador);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        //Si quieres navegar si no ha decidido no cerrar sesión
        }else{
            myRef.child("Cuestionario").addValueEventListener(new ValueEventListener() {
                Login l=new Login();
                String nick=l.ultimoUsuarioLogeado();
                String funcion="";
                POJO[] datos=null;
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                        if(dataSnapshot.child("nick").getValue().toString().equals(nick)){
                            funcion=dataSnapshot.child("objetivo4").getValue().toString();
                        }
                    }
                    //Si quiere estar en definicion
                    if(funcion.equals("Adelgazar")){
                        //Si quiere rutina de pecho y brazos
                        if(i==1){
                            //Si quiere nivel principiante
                            if(item.equals("Principiante")){
                                datos=new POJO[]{
                                        new POJO(R.drawable.dietas, "Flexiones de rodillas: 3 series de 10 repeticiones."),
                                        new POJO(R.drawable.calendario, "Press de mancuernas: 3 series de 10 repeticiones."),
                                        new POJO(R.drawable.datospersonales, "Elevaciones laterales con mancuernas: 3 series de 10 repeticiones."),
                                        new POJO(R.drawable.foro, "Extensiones de tríceps con mancuernas: 3 series de 10 repeticiones."),
                                        new POJO(R.drawable.foro, "Curl de bíceps con mancuernas: 3 series de 10 repeticiones.")
                                };
                            }
                            //Si quiere nivel intermedio
                            if(item.equals("Intermedio")){
                                datos=new POJO[]{
                                        new POJO(R.drawable.dietas, "Flexiones clásicas: 3 series de 12 repeticiones."),
                                        new POJO(R.drawable.calendario, "Fondos en paralelas: 3 series de 10 repeticiones."),
                                        new POJO(R.drawable.datospersonales, "Press militar con mancuernas: 3 series de 12 repeticiones."),
                                        new POJO(R.drawable.foro, "Curl de bíceps con barra: 3 series de 12 repeticiones."),
                                        new POJO(R.drawable.foro, "Extensiones de tríceps con mancuernas (por encima de la cabeza): 3 series de 12 repeticiones.")
                                };
                            }
                            //Si quiere nivel avanzado
                            if(item.equals("Avanzado")){
                                datos=new POJO[]{
                                        new POJO(R.drawable.dietas, "Flexiones en diamante: 3 series de 15 repeticiones."),
                                        new POJO(R.drawable.calendario, "Dominadas con agarre estrecho: 3 series de 8 repeticiones."),
                                        new POJO(R.drawable.datospersonales, "Press de banca con mancuernas: 3 series de 12 repeticiones."),
                                        new POJO(R.drawable.foro, "Fondos entre bancos (con peso añadido si se desea): 3 series de 10 repeticiones."),
                                        new POJO(R.drawable.foro, "Curl de bíceps concentrado con mancuerna: 3 series de 12 repeticiones.")
                                };
                            }
                        }
                        //Si quiere rutina de abdominales
                        if(i==2){
                            if(item.equals("Principiante")){
                                datos=new POJO[]{
                                        new POJO(R.drawable.dietas, "Crunches (3 series de 15 repeticiones)."),
                                        new POJO(R.drawable.calendario, "Plank (3 series de 30 segundos)."),
                                        new POJO(R.drawable.datospersonales, "Leg Raises (3 series de 10 repeticiones)."),
                                        new POJO(R.drawable.foro, "Russian Twists (3 series de 15 repeticiones)."),
                                        new POJO(R.drawable.foro, "Bicycle Crunches (3 series de 20 repeticiones).")
                                };
                            }
                            //Si quiere nivel intermedio
                            if(item.equals("Intermedio")){
                                datos=new POJO[]{
                                        new POJO(R.drawable.dietas, "Mountain Climbers (3 series de 30 segundos)."),
                                        new POJO(R.drawable.calendario, "Flutter Kicks (3 series de 20 repeticiones)."),
                                        new POJO(R.drawable.datospersonales, "Reverse Crunches (3 series de 12 repeticiones)."),
                                        new POJO(R.drawable.foro, "Side Plank (3 series de 30 segundos cada lado)."),
                                        new POJO(R.drawable.foro, "Scissor Kicks (3 series de 15 repeticiones).")
                                };
                            }
                            //Si quiere nivel avanzado
                            if(item.equals("Avanzado")){
                                datos=new POJO[]{
                                        new POJO(R.drawable.dietas, "V-Sit Crunches (3 series de 10 repeticiones)."),
                                        new POJO(R.drawable.calendario, "Dragon Flags (3 series de 6 repeticiones)."),
                                        new POJO(R.drawable.datospersonales, "Hanging Leg Raises (3 series de 8 repeticiones)."),
                                        new POJO(R.drawable.foro, "Plank with Knee Taps (3 series de 20 repeticiones)."),
                                        new POJO(R.drawable.foro, "Windshield Wipers (3 series de 10 repeticiones).")
                                };
                            }
                        }
                        //Si quiere rutina de hombros y espalda
                        if(i==3){
                            //Si quiere nivel principiante
                            if(item.equals("Principiante")){
                                datos=new POJO[]{
                                        new POJO(R.drawable.dietas, "Remo con mancuernas (3 series de 12 repeticiones)."),
                                        new POJO(R.drawable.calendario, "Elevaciones laterales con mancuernas (3 series de 12 repeticiones)."),
                                        new POJO(R.drawable.datospersonales, "Face pulls con polea (3 series de 12 repeticiones)."),
                                        new POJO(R.drawable.foro, "Dominadas asistidas con banda elástica (3 series de 8 repeticiones)."),
                                        new POJO(R.drawable.foro, "Press militar con mancuernas (3 series de 12 repeticiones).")
                                };
                            }
                            //Si quiere nivel intermedio
                            if(item.equals("Intermedio")){
                                datos=new POJO[]{
                                        new POJO(R.drawable.dietas, "Remo con barra (3 series de 10 repeticiones)."),
                                        new POJO(R.drawable.calendario, "Press de hombros con mancuernas (3 series de 10 repeticiones)."),
                                        new POJO(R.drawable.datospersonales, "Pull-ups con agarre amplio (3 series de 8 repeticiones)."),
                                        new POJO(R.drawable.foro, "Elevaciones frontales con mancuernas (3 series de 12 repeticiones)."),
                                        new POJO(R.drawable.foro, "Face pulls con cable en posición de rodillas (3 series de 12 repeticiones).")
                                };
                            }
                            //Si quiere nivel avanzado
                            if(item.equals("Avanzado")){
                                datos=new POJO[]{
                                        new POJO(R.drawable.dietas, "Peso muerto rumano (3 series de 8 repeticiones)."),
                                        new POJO(R.drawable.calendario, "Clean and press con barra (3 series de 8 repeticiones)."),
                                        new POJO(R.drawable.datospersonales, "Dominadas con agarre estrecho (3 series de 8 repeticiones)."),
                                        new POJO(R.drawable.foro, "Press de hombros con barra (3 series de 10 repeticiones)."),
                                        new POJO(R.drawable.foro, "Elevaciones laterales con mancuernas en posición sentada (3 series de 12 repeticiones).")
                                };
                            }
                        }
                        //Si quiere rutina de piernas
                        if(i==4){
                            //Si quiere nivel principiante
                            if(item.equals("Principiante")){
                                datos=new POJO[]{
                                        new POJO(R.drawable.dietas, "Sentadillas con peso corporal."),
                                        new POJO(R.drawable.calendario, "Zancadas hacia adelante con peso corporal."),
                                        new POJO(R.drawable.datospersonales, "Elevaciones de pantorrilla en posición de pie."),
                                        new POJO(R.drawable.foro, "Puentes de glúteos."),
                                        new POJO(R.drawable.foro, "Extensiones de piernas en máquina.")
                                };
                            }
                            //Si quiere nivel intermedio
                            if(item.equals("Intermedio")){
                                datos=new POJO[]{
                                        new POJO(R.drawable.dietas, "Sentadillas con barra."),
                                        new POJO(R.drawable.calendario, "Zancadas con mancuernas."),
                                        new POJO(R.drawable.datospersonales, "Prensa de piernas."),
                                        new POJO(R.drawable.foro, "Peso muerto con barra."),
                                        new POJO(R.drawable.foro, "Curl de piernas acostado en máquina.")
                                };
                            }
                            //Si quiere nivel avanzado
                            if(item.equals("Avanzado")){
                                datos=new POJO[]{
                                        new POJO(R.drawable.dietas, "Sentadillas con salto."),
                                        new POJO(R.drawable.calendario, "Paseo del granjero con pesas."),
                                        new POJO(R.drawable.datospersonales, "Prensa hack."),
                                        new POJO(R.drawable.foro, "Peso muerto rumano con mancuernas."),
                                        new POJO(R.drawable.foro, "Pliometría de caja.")
                                };
                            }
                        }
                        //Si quiere estar en volumen
                    }else{
                        //Si quiere rutina de pecho y brazos
                        if(i==1){
                            //Si quiere nivel principiante
                            if(item.equals("Principiante")){
                                datos=new POJO[]{
                                        new POJO(R.drawable.dietas, "Flexiones de pared → Haz 3 series de 10 repeticiones cada una."),
                                        new POJO(R.drawable.calendario, "Flexiones de rodillas → Haz 3 series de 10 repeticiones cada una."),
                                        new POJO(R.drawable.datospersonales, "Curl de bíceps con mancuernas → Haz 3 series de 12 repeticiones cada una."),
                                        new POJO(R.drawable.foro, "Press de banca con mancuernas → Haz 3 series de 12 repeticiones cada una."),
                                        new POJO(R.drawable.foro, "Flexiones diamante → Haz 3 series de 10 repeticiones cada una.")
                                };
                            }
                            //Si quiere nivel intermedio
                            if(item.equals("Intermedio")){
                                datos=new POJO[]{
                                        new POJO(R.drawable.dietas, "Flexiones de mano estrecha → Haz 4 series de 12 repeticiones cada una."),
                                        new POJO(R.drawable.calendario, "Curl de bíceps con barra → Haz 4 series de 10 repeticiones cada una."),
                                        new POJO(R.drawable.datospersonales, "Press de banca con barra →Haz 4 series de 10 repeticiones cada una."),
                                        new POJO(R.drawable.foro, "Pull-ups →Haz 4 series de 8 repeticiones cada una."),
                                        new POJO(R.drawable.foro, "Dips → Haz 4 series de 10 repeticiones cada una.")
                                };
                            }
                            //Si quiere nivel avanzado
                            if(item.equals("Avanzado")){
                                datos=new POJO[]{
                                        new POJO(R.drawable.dietas, "Curl de bíceps con barra (4 series de 8 repeticiones)."),
                                        new POJO(R.drawable.calendario, "Curl alterno de bíceps con mancuernas (3 series de 10 repeticiones)."),
                                        new POJO(R.drawable.datospersonales, "Press de banca inclinado con mancuernas (3 series de 10 repeticiones)."),
                                        new POJO(R.drawable.foro, "Cruce de cables en polea (4 series de 8 repeticiones)."),
                                        new POJO(R.drawable.foro, "Aperturas con mancuernas en banco plano (3 series de 12 repeticiones).")
                                };
                            }
                        }
                        //Si quiere rutina de abdominales
                        if(i==2){
                            if(item.equals("Principiante")){
                                datos=new POJO[]{
                                        new POJO(R.drawable.dietas, "Crunches con peso (3 series de 12 repeticiones)."),
                                        new POJO(R.drawable.calendario, "Plank con elevación de pierna (3 series de 10 repeticiones por pierna)."),
                                        new POJO(R.drawable.datospersonales, "Abdominales con pelota suiza (3 series de 15 repeticiones)."),
                                        new POJO(R.drawable.foro, "Russian Twists con peso (3 series de 12 repeticiones)."),
                                        new POJO(R.drawable.foro, "Levantamiento de piernas con peso (3 series de 10 repeticiones).")
                                };
                            }
                            //Si quiere nivel intermedio
                            if(item.equals("Intermedio")){
                                datos=new POJO[]{
                                        new POJO(R.drawable.dietas, "Plank con elevación de pierna y brazo opuestos (3 series de 10 repeticiones por lado)."),
                                        new POJO(R.drawable.calendario, "Abdominales en \"V\" con peso (3 series de 12 repeticiones)."),
                                        new POJO(R.drawable.datospersonales, "Side Plank con elevación de cadera (3 series de 10 repeticiones por lado)."),
                                        new POJO(R.drawable.foro, "Bicycle Crunches con peso (3 series de 15 repeticiones)."),
                                        new POJO(R.drawable.foro, "Levantamiento de piernas colgado en barra con peso (3 series de 8 repeticiones).")
                                };
                            }
                            //Si quiere nivel avanzado
                            if(item.equals("Avanzado")){
                                datos=new POJO[]{
                                        new POJO(R.drawable.dietas, "Dragon Flags (3 series de 5 repeticiones)."),
                                        new POJO(R.drawable.calendario, "Abdominales en \"V\" colgado en barra con peso (3 series de 12 repeticiones)."),
                                        new POJO(R.drawable.datospersonales, "Plank con elevación de pierna, brazo opuesto y peso (3 series de 8 repeticiones por lado)."),
                                        new POJO(R.drawable.foro, "Windshield Wipers con peso (3 series de 10 repeticiones por lado)."),
                                        new POJO(R.drawable.foro, "Levantamiento de piernas colgado en barra con giro lateral (3 series de 8 repeticiones por lado).")
                                };
                            }
                        }
                        //Si quiere rutina de hombros y espalda
                        if(i==3){
                            //Si quiere nivel principiante
                            if(item.equals("Principiante")){
                                datos=new POJO[]{
                                        new POJO(R.drawable.dietas, "Remo con mancuernas: 3 series de 12 repeticiones."),
                                        new POJO(R.drawable.calendario, "Elevaciones laterales con mancuernas: 3 series de 12 repeticiones."),
                                        new POJO(R.drawable.datospersonales, "Dominadas asistidas con banda elástica: 3 series de 10 repeticiones."),
                                        new POJO(R.drawable.foro, "Press militar con mancuernas: 3 series de 12 repeticiones."),
                                        new POJO(R.drawable.foro, "Face pull con polea: 3 series de 12 repeticiones.")
                                };
                            }
                            //Si quiere nivel intermedio
                            if(item.equals("Intermedio")){
                                datos=new POJO[]{
                                        new POJO(R.drawable.dietas, "Remo con barra T: 4 series de 10 repeticiones."),
                                        new POJO(R.drawable.calendario, "Press Arnold con mancuernas: 4 series de 10 repeticiones."),
                                        new POJO(R.drawable.datospersonales, "Pull-ups con agarre amplio: 4 series de 8 repeticiones."),
                                        new POJO(R.drawable.foro, "Elevaciones frontales con mancuernas: 4 series de 10 repeticiones."),
                                        new POJO(R.drawable.foro, "Remo horizontal en máquina: 4 series de 12 repeticiones.")
                                };
                            }
                            //Si quiere nivel avanzado
                            if(item.equals("Avanzado")){
                                datos=new POJO[]{
                                        new POJO(R.drawable.dietas, "Dominadas con peso: 5 series de 6 repeticiones."),
                                        new POJO(R.drawable.calendario, "Press militar con barra: 5 series de 8 repeticiones.."),
                                        new POJO(R.drawable.datospersonales, "Remo Pendlay: 5 series de 8 repeticiones."),
                                        new POJO(R.drawable.foro, "Elevaciones laterales con mancuernas: 5 series de 10 repeticiones."),
                                        new POJO(R.drawable.foro, "Face pull con cuerda: 5 series de 12 repeticiones.")
                                };
                            }
                        }
                        //Si quiere rutina de piernas
                        if(i==4){
                            //Si quiere nivel principiante
                            if(item.equals("Principiante")){
                                datos=new POJO[]{
                                        new POJO(R.drawable.dietas, "Sentadillas con barra."),
                                        new POJO(R.drawable.calendario, "Prensa de piernas."),
                                        new POJO(R.drawable.datospersonales, "Extensiones de pierna en máquina."),
                                        new POJO(R.drawable.foro, "Elevaciones de talón sentado en máquina."),
                                        new POJO(R.drawable.foro, "Zancadas con mancuernas.")
                                };
                            }
                            //Si quiere nivel intermedio
                            if(item.equals("Intermedio")){
                                datos=new POJO[]{
                                        new POJO(R.drawable.dietas, "Sentadillas búlgaras con mancuernas."),
                                        new POJO(R.drawable.calendario, "Peso muerto rumano con barra."),
                                        new POJO(R.drawable.datospersonales, "Prensa hack."),
                                        new POJO(R.drawable.foro, "Curl de pierna acostado en máquina."),
                                        new POJO(R.drawable.foro, "Hip thrust con barra.")
                                };
                            }
                            //Si quiere nivel avanzado
                            if(item.equals("Avanzado")){
                                datos=new POJO[]{
                                        new POJO(R.drawable.dietas, "Sentadillas con salto."),
                                        new POJO(R.drawable.calendario, "Paseo del granjero con pesas."),
                                        new POJO(R.drawable.datospersonales, "Prensa de piernas con una sola pierna."),
                                        new POJO(R.drawable.foro, "Zancadas con salto."),
                                        new POJO(R.drawable.foro, "Pliometría de caja.")
                                };
                            }
                        }
                    }
                    Adaptador miAdaptador = new Adaptador(getApplicationContext(), datos);
                    listView.setAdapter(miAdaptador);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }

    private boolean noCierraSesion(){
        if(preferences.getString("nick",null)!=null){
            return true;
        }
        return false;
    }
}