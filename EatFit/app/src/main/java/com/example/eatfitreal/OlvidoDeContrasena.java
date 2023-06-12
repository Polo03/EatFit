package com.example.eatfitreal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;


public class OlvidoDeContrasena extends AppCompatActivity{

    private SharedPreferences preferences;

    private boolean realizado=false;

    //private String codigo="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_olvido_de_contrasena);

        //Para las medidas de la ventana del pop up
        DisplayMetrics medidasVentana=new DisplayMetrics();

        getWindowManager().getDefaultDisplay().getMetrics(medidasVentana);

        int ancho=medidasVentana.widthPixels;
        int alto=medidasVentana.heightPixels;

        getWindow().setLayout((int)(ancho * 0.85), (int) (alto * 0.55));

        //getWindow().setBackgroundDrawable(new BitmapDrawable());
        // Closes the popup window when touch outside.
        //getWindow().setOutsideTouchable(true);
        //getWindow().setFocusable(true);
        // Removes default background.
        //getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        Random ale=new Random();
        String codigo=ale.nextInt(10)+""+ale.nextInt(10)+""+ale.nextInt(10)+""+ale.nextInt(10)+""+ale.nextInt(10)+""+ale.nextInt(10);

        if(ActivityCompat.checkSelfPermission(OlvidoDeContrasena.this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(OlvidoDeContrasena.this,new String[]{Manifest.permission.SEND_SMS},1);
        }
        TextView texto=findViewById(R.id.textViewInfoOP);

        Button enviarCodigo=(Button) findViewById(R.id.buttonConfirmarCambioPassword);
        Button recibirCodigo=(Button) findViewById(R.id.buttonContinuar);
        Button cambiarPwd=(Button) findViewById(R.id.buttonCambiarPwd);
        recibirCodigo.setVisibility(View.INVISIBLE);
        cambiarPwd.setVisibility(View.INVISIBLE);
        EditText editText=(EditText) findViewById(R.id.editTextUsuarioCambiarContraseña);
        preferences=getSharedPreferences("Preferences",MODE_PRIVATE);
        String[] nick={""};
        //codigo=ale.nextInt(10)+""+ale.nextInt(10)+""+ale.nextInt(10)+""+ale.nextInt(10)+""+ale.nextInt(10)+""+ale.nextInt(10);

        enviarCodigo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean[] unaVez={false};
                DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();
                myRef.child("Usuarios").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(!unaVez[0]){
                            Login l=new Login();
                            String phone="";
                            for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                                if(dataSnapshot.child("nick").getValue().toString().equals(editText.getText().toString()) || dataSnapshot.child("email").getValue().toString().equals(editText.getText().toString())){
                                    nick[0]=dataSnapshot.child("nick").getValue().toString();
                                    phone=dataSnapshot.child("phone").getValue().toString();
                                }
                            }
                            if(!phone.equals("")){

                                //Toast.makeText(OlvidoDeContrasena.this, "El codigo enviado es:"+codigo, Toast.LENGTH_LONG).show();
                                SmsManager smsManager=SmsManager.getDefault();
                                smsManager.sendTextMessage(phone, null, codigo, null, null);

                                texto.setText("Introduzca el código que le hemos enviado");
                                editText.setHint("Codigo");
                                editText.setText("");
                                enviarCodigo.setVisibility(View.INVISIBLE);
                                recibirCodigo.setVisibility(View.VISIBLE);

                            }else{
                                Toast.makeText(OlvidoDeContrasena.this, "No se ha encontrado el usuario", Toast.LENGTH_SHORT).show();
                            }
                            unaVez[0]=true;
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });

        recibirCodigo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(codigo.equals(editText.getText().toString())){
                    //Toast.makeText(OlvidoDeContrasena.this, "VAMOOO", Toast.LENGTH_SHORT).show();
                    texto.setText("Introduzca la nueva contraseña");
                    editText.setHint("Nueva Contraseña");
                    editText.setText("");
                    recibirCodigo.setVisibility(View.INVISIBLE);
                    cambiarPwd.setVisibility(View.VISIBLE);
                }else
                    Toast.makeText(OlvidoDeContrasena.this, "ESE CÓDIGO NO ES EL QUE LE HEMOS ENVIADO", Toast.LENGTH_SHORT).show();
            }
        });

        cambiarPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean[] unaVez2={false};
                DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();
                myRef.child("Usuarios").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        String pwd="";
                        String email="";
                        String DNI="";
                        String peso="";
                        String phone="";
                        String altura="";
                        String fechaNac="";
                        //String numRutina="";
                        String vecesLogeado="";
                        int version=0;
                        for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                            if(dataSnapshot.child("nick").getValue().toString().equals(nick[0].toString()) || dataSnapshot.child("email").getValue().toString().equals(nick[0].toString())){
                                phone=dataSnapshot.child("phone").getValue().toString();
                                //pwd=dataSnapshot.child("password").getValue().toString();
                                email=dataSnapshot.child("email").getValue().toString();
                                peso=dataSnapshot.child("peso").getValue().toString();
                                altura=dataSnapshot.child("altura").getValue().toString();
                                DNI=dataSnapshot.child("DNI").getValue().toString();
                                fechaNac=dataSnapshot.child("fechaNac").getValue().toString();
                                //numRutina=dataSnapshot.child("numRutina").getValue().toString();
                                vecesLogeado=dataSnapshot.child("vecesLogeado").getValue().toString();
                                version=Integer.parseInt(dataSnapshot.child("version").getValue().toString());
                            }
                        }
                        Map<String, Object> datosUser = new HashMap<>();
                        datosUser.put("nick",nick[0]);
                        datosUser.put("password",editText.getText().toString());
                        datosUser.put("email",email);
                        datosUser.put("DNI",DNI);
                        datosUser.put("peso",peso);
                        datosUser.put("altura",altura);
                        datosUser.put("fechaNac",fechaNac);
                        datosUser.put("phone",phone);
                        datosUser.put("vecesLogeado",vecesLogeado);
                        //datosUser.put("numRutina",numRutina);
                        datosUser.put("version",version);
                        myRef.child("Usuarios").child(nick[0]).setValue(datosUser);

                        if(!unaVez2[0]){
                            Intent intent = new Intent(OlvidoDeContrasena.this, Login.class);
                            startActivity(intent);
                            unaVez2[0]=true;
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
    }




}