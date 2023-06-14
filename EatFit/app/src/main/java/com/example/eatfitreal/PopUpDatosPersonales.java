package com.example.eatfitreal;

import static android.view.RoundedCorner.POSITION_TOP_RIGHT;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.DisplayMetrics;
import android.view.RoundedCorner;
import android.view.View;
import android.view.WindowInsets;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PopUpDatosPersonales extends AppCompatActivity {

    private SharedPreferences preferences;
    private DatabaseReference myRef;
    private int version=0;
    private EditText textViewFechaNac;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up_datos_personales);

        Login l=new Login();
        preferences = getSharedPreferences("Preferences", MODE_PRIVATE);

        //Para las medidas de la ventana del pop up
        DisplayMetrics medidasVentana=new DisplayMetrics();

        getWindowManager().getDefaultDisplay().getMetrics(medidasVentana);

        int ancho=medidasVentana.widthPixels;
        int alto=medidasVentana.heightPixels;

        getWindow().setLayout((int)(ancho * 0.85), (int) (alto * 0.7));

        TextView textViewNick=findViewById(R.id.textViewNick);
        EditText textViewPwd=findViewById(R.id.textViewPwd);
        EditText textViewEmail=findViewById(R.id.textViewEmail);
        EditText textViewDNI=findViewById(R.id.textViewDNI);
        EditText textViewPeso=findViewById(R.id.textViewPeso);
        EditText textViewAltura=findViewById(R.id.textViewAltura);
        textViewFechaNac=findViewById(R.id.textViewFechaNac);
        EditText textViewNumTelefono=findViewById(R.id.textViewNumTelefono);


        textViewPwd.setEnabled(false);
        textViewEmail.setEnabled(false);
        textViewDNI.setEnabled(false);
        textViewPeso.setEnabled(false);
        textViewAltura.setEnabled(false);
        textViewFechaNac.setEnabled(false);
        textViewNumTelefono.setEnabled(false);
        textViewNick.setTextColor(Color.WHITE);
        textViewPwd.setTextColor(Color.WHITE);
        textViewEmail.setTextColor(Color.WHITE);
        textViewDNI.setTextColor(Color.WHITE);
        textViewPeso.setTextColor(Color.WHITE);
        textViewAltura.setTextColor(Color.WHITE);
        textViewFechaNac.setTextColor(Color.WHITE);
        textViewNumTelefono.setTextColor(Color.WHITE);
        String nickStr="";
        if(preferences.getString("nick", null)==null)
            nickStr=l.ultimoUsuarioLogeado();
        else
            nickStr=preferences.getString("nick", null);
        myRef= FirebaseDatabase.getInstance().getReference();
        String finalNickStr = nickStr;
        myRef.child("Usuarios").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String nick="";
                String pwd="";
                String email="";
                String dni="";
                String peso="";
                String altura="";
                String fechaNac="";
                String numTelefono="";
                String nickStr2= finalNickStr;

                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    if(dataSnapshot.child("nick").getValue().toString().equals(finalNickStr) || dataSnapshot.child("email").getValue().toString().equals(finalNickStr)){
                        nick=dataSnapshot.child("nick").getValue().toString();
                        pwd=dataSnapshot.child("password").getValue().toString();
                        email=dataSnapshot.child("email").getValue().toString();
                        dni=dataSnapshot.child("DNI").getValue().toString();
                        peso=dataSnapshot.child("peso").getValue().toString();
                        altura=dataSnapshot.child("altura").getValue().toString();
                        fechaNac=dataSnapshot.child("fechaNac").getValue().toString();
                        numTelefono=dataSnapshot.child("phone").getValue().toString();
                        version=Integer.parseInt(dataSnapshot.child("version").getValue().toString());
                    }
                }

                textViewNick.setText(nick);
                textViewPwd.setText(pwd);
                textViewEmail.setText(email);
                textViewDNI.setText(dni);
                textViewPeso.setText(peso);
                textViewAltura.setText(altura);
                textViewFechaNac.setText(fechaNac);
                textViewNumTelefono.setText(numTelefono);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        Button botonSiguiente=(Button) findViewById(R.id.buttonNext);
        botonSiguiente.setVisibility(View.INVISIBLE);
        myRef.child("Usuarios").addValueEventListener(new ValueEventListener() {
            String dniActual="";
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                botonSiguiente.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        Pattern patronEmail=Pattern.compile("[A-Z|a-z|0-9]+@gmail.com");
                        Pattern patronNumTelefono = Pattern.compile("[0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9]");
                        Matcher matcherEmail=patronEmail.matcher(textViewEmail.getText().toString());
                        Matcher matcherNumTelefono=patronNumTelefono.matcher(textViewNumTelefono.getText().toString());
                        if(!matcherEmail.matches()){
                            AlertDialog.Builder builder = new AlertDialog.Builder(PopUpDatosPersonales.this);
                            builder.setTitle("ERROR");
                            builder.setMessage("Revise el formato del email");
                            AlertDialog dialog = builder.create();
                            dialog.show();
                        }else if(!matcherNumTelefono.matches()) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(PopUpDatosPersonales.this);
                            builder.setTitle("ERROR");
                            builder.setMessage("Revise el formato del numero de telefono");
                            AlertDialog dialog = builder.create();
                            dialog.show();
                        }else{
                            AlertDialog.Builder builder = new AlertDialog.Builder(PopUpDatosPersonales.this);
                            builder.setTitle("ALERTA");
                            builder.setMessage("¿Desea confirmar los cambios?");        // add the buttons
                            builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Map<String, Object> datosRoot = new HashMap<>();
                                    datosRoot.put("nick",textViewNick.getText().toString());
                                    datosRoot.put("password",textViewPwd.getText().toString());
                                    datosRoot.put("email",textViewEmail.getText().toString());
                                    datosRoot.put("DNI",textViewDNI.getText().toString());
                                    datosRoot.put("peso",textViewPeso.getText().toString());
                                    datosRoot.put("altura",textViewAltura.getText().toString());
                                    datosRoot.put("fechaNac",textViewFechaNac.getText().toString());
                                    datosRoot.put("phone",textViewNumTelefono.getText().toString());
                                    datosRoot.put("vecesLogeado",1);
                                    datosRoot.put("version",version);
                                    //El .child es como una especie de ruta, en este caso, usuarios seria la tabla y el registro es Root.
                                    myRef.child("Usuarios").child(textViewNick.getText().toString()).setValue(datosRoot);
                                    //Para terminar la actividad en la cual introduces el comando
                                    finish();
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

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        ImageButton botonConfig=(ImageButton) findViewById(R.id.imageButtonConfig);
        botonConfig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                botonConfig.setVisibility(View.INVISIBLE);
                textViewPwd.setEnabled(true);
                textViewEmail.setEnabled(true);
                textViewPeso.setEnabled(true);
                textViewAltura.setEnabled(true);
                textViewFechaNac.setEnabled(true);
                textViewNumTelefono.setEnabled(true);
                botonSiguiente.setVisibility(View.VISIBLE);
                textViewPwd.getBackground().setTint(Color.WHITE);
                textViewEmail.getBackground().setTint(Color.WHITE);
                textViewPeso.getBackground().setTint(Color.WHITE);
                textViewAltura.getBackground().setTint(Color.WHITE);
                textViewFechaNac.getBackground().setTint(Color.WHITE);
                textViewNumTelefono.getBackground().setTint(Color.WHITE);
            }
        });

    }

    public void mostrarCalendario(View v){
        DatePickerDialog d=new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                if(month+1<10)
                    if(day<10)
                        textViewFechaNac.setText("0"+day+"/0"+(month+1)+"/"+year);
                    else
                        textViewFechaNac.setText(day+"/0"+(month+1)+"/"+year);
                else
                if(day<10)
                    textViewFechaNac.setText("0"+day+"/"+(month+1)+"/"+year);
                else
                    textViewFechaNac.setText(day+"/"+(month+1)+"/"+year);
            }
        },2003,0,1);
        d.setMessage("Seleccione su fecha de nacimiento");
        d.show();
    }

}