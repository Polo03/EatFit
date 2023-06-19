package com.example.eatfitreal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ConteoCalorias extends AppCompatActivity {

    SharedPreferences preferences;
    final private ArrayList<String> alimentos=new ArrayList<>();
    final private ArrayList<String> cantidad=new ArrayList<>();
    final private ArrayList<Integer> calorias=new ArrayList<>();
    int index=0;

    final private int[] galeria={R.drawable.aguacate,R.drawable.almendras,R.drawable.atun,R.drawable.avena,R.drawable.brocoli,R.drawable.clarasdehuevo,R.drawable.espinacas,R.drawable.fresas,R.drawable.manzana,R.drawable.pavo,R.drawable.pepino,R.drawable.platano,R.drawable.pollo,R.drawable.queso,R.drawable.quinoa,R.drawable.salmon,R.drawable.sandia,R.drawable.tomate,R.drawable.yogur,R.drawable.zanahorias};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conteo_calorias);
        TextView textView=findViewById(R.id.textViewCaloriasTotales);

        Login l=new Login();
        preferences = getSharedPreferences("Preferences", MODE_PRIVATE);

        //Para las medidas de la ventana del pop up
        DisplayMetrics medidasVentana=new DisplayMetrics();

        getWindowManager().getDefaultDisplay().getMetrics(medidasVentana);

        int ancho=medidasVentana.widthPixels;
        int alto=medidasVentana.heightPixels;

        getWindow().setLayout((int)(ancho * 0.90), (int) (alto * 0.70));

        TextView textSwitcher=findViewById(R.id.textSwitcherConteo);
        ImageView imageSwitcher=findViewById(R.id.imageViewConteo);
        TextView textView2=findViewById(R.id.textViewCaloriasTotales3);

        //Si ha decidido no cerrar sesión o no
        String nickStr="";
        if(preferences.getString("nick", null)==null)
            nickStr=l.ultimoUsuarioLogeado();
        else
            nickStr=preferences.getString("nick", null);

        DatabaseReference myRef= FirebaseDatabase.getInstance().getReference();
        String finalNickStr = nickStr;

        myRef.child("Alimentos").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    alimentos.add(dataSnapshot.child("nombreAlimento").getValue().toString());
                    cantidad.add(dataSnapshot.child("cantidad").getValue().toString());
                    calorias.add(Integer.parseInt(dataSnapshot.child("calorias").getValue().toString()));
                }
                textSwitcher.setText(alimentos.get(index)+"-->"+calorias.get(index)+" calorías");
                imageSwitcher.setImageResource(galeria[index]);
                //Buscamos en la "tabla" Calorias
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
                        //Si no ha establecido las calorias
                        if(caloriasEstablecidas==0){
                            //Mostramos una alerta para que decida si las calorias que quiere consumir ese dia
                            AlertDialog.Builder builder = new AlertDialog.Builder(ConteoCalorias.this);
                            builder.setTitle("Información");
                            builder.setMessage("¿Cuantas calorías quiere consumir hoy?");        // add the buttons
                            //Implementamos un editText a esa alerta para que establezca las calorias
                            final EditText textCalorias=new EditText(ConteoCalorias.this);
                            textCalorias.setInputType(InputType.TYPE_CLASS_NUMBER);
                            textCalorias.setHint("Calorías Diarias");
                            builder.setView(textCalorias);
                            //Para establecer un botón a la alerta
                            builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if(!textCalorias.getText().toString().equals("")){
                                        //Introduce las calorias totales en la base de datos
                                        DatabaseReference myRef= FirebaseDatabase.getInstance().getReference();
                                        myRef = FirebaseDatabase.getInstance().getReference();

                                        Map<String, Object> calorias = new HashMap<>();
                                        calorias.put("nick",finalNickStr);
                                        calorias.put("caloriasDeseadas",Integer.parseInt(textCalorias.getText().toString()));
                                        calorias.put("caloriasConsumidas",0);
                                        calorias.put("caloriasEstablecidas",1);
                                        myRef.child("Calorias").child(finalNickStr).setValue(calorias);
                                        dialog.dismiss();
                                    }else{
                                        Toast.makeText(ConteoCalorias.this, "DEBE INTRODUCIR ALGUN NÚMERO DE CALORIAS DIARIAS", Toast.LENGTH_SHORT).show();
                                        textCalorias.setText("");
                                        finish();
                                    }
                                }
                            });
                            AlertDialog dialog = builder.create();
                            dialog.show();
                        }
                        textView.setText("Calorías deseadas:"+caloriasTotales+"");
                        textView2.setText("Calorías consumidas:"+caloriasConsumidas+"");
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        imageSwitcher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myRef.child("Calorias").addValueEventListener(new ValueEventListener() {

                    int caloriasEstablecidas=0;
                    int caloriasTotales=0;
                    int caloriasConsumidas=0;
                    int unaVez=0;

                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                            if(dataSnapshot.child("nick").getValue().toString().equals(finalNickStr)){
                                caloriasTotales=Integer.parseInt(dataSnapshot.child("caloriasDeseadas").getValue().toString());
                                caloriasConsumidas=Integer.parseInt(dataSnapshot.child("caloriasConsumidas").getValue().toString());
                            }
                        }
                        //Para que no de la vuelta dos veces al onDataChange
                        if(unaVez==0){
                            unaVez++;
                            actualizaCalorias(finalNickStr,caloriasTotales,caloriasConsumidas,calorias.get(index));
                            //Si las calorias consumidas es mayor o igual a las calorias establecidas
                            //se cierra el activity
                            if(caloriasConsumidas+calorias.get(index)>=caloriasTotales){
                                finish();
                            }

                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

        ImageButton buttonBefore=findViewById(R.id.imageButtonBeforeConteo);
        buttonBefore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                index--;
                //Si la variable index es menor o igual a 0,
                //la variable index pasa a valer la cantidad de alimentos.
                if(index<0)
                    index=alimentos.size()-1;
                textSwitcher.setText(alimentos.get(index)+"-->"+calorias.get(index)+" calorías");
                imageSwitcher.setImageResource(galeria[index]);
            }
        });

        ImageButton buttonAfter=findViewById(R.id.imageButtonAfterConteo);
        buttonAfter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                index++;
                //Si la variable index es mayor o igual a la cantidad de alimentos,
                //la variable index pasa a valer 0.
                if(index>=alimentos.size())
                    index=0;
                textSwitcher.setText(alimentos.get(index)+"-->"+calorias.get(index)+" calorías");
                imageSwitcher.setImageResource(galeria[index]);
            }
        });


    }

    //Método para actualizar las calorias de la base de datos.
    public static void actualizaCalorias(String nick, int caloriasTotales, int caloriasConsumidas, int cal){
        DatabaseReference myRef=FirebaseDatabase.getInstance().getReference();
        Map<String, Object> calorias = new HashMap<>();
        calorias.put("nick",nick);
        calorias.put("caloriasDeseadas",caloriasTotales);
        calorias.put("caloriasConsumidas",(caloriasConsumidas+cal));
        calorias.put("caloriasEstablecidas",1);
        myRef.child("Calorias").child(nick).setValue(calorias);
    }

}