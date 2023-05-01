package com.example.eatfitreal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;


public class OlvidoDeContrasena extends AppCompatActivity {

    private SharedPreferences preferences;

    private String codigo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_olvido_de_contrasena);

        if(ActivityCompat.checkSelfPermission(OlvidoDeContrasena.this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(OlvidoDeContrasena.this,new String[]{Manifest.permission.SEND_SMS},1);
        }

        Button cambiarPassword=(Button) findViewById(R.id.buttonConfirmarCambioPassword);
        EditText usuario=(EditText) findViewById(R.id.editTextUsuarioCambiarContraseña);
        preferences=getSharedPreferences("Preferences",MODE_PRIVATE);

        cambiarPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Random ale=new Random();

                DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();

                myRef.child("Usuarios").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        Login l=new Login();

                        String nickString=l.ultimoUsuarioLogeado();
                        String nick="";
                        String phone="";

                        codigo=ale.nextInt(10)+""+ale.nextInt(10)+""+ale.nextInt(10)+""+ale.nextInt(10)+""+ale.nextInt(10)+""+ale.nextInt(10);

                        for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                            if(dataSnapshot.child("nick").getValue().toString().equals(usuario.getText().toString()) || dataSnapshot.child("email").getValue().toString().equals(usuario.getText().toString())){
                                nick=dataSnapshot.child("nick").getValue().toString();
                                phone=dataSnapshot.child("Phone").getValue().toString();
                            }
                        }

                        if(!phone.equals("")){
                            SmsManager smsManager=SmsManager.getDefault();
                            smsManager.sendTextMessage(phone, null, codigo, null, null);

                            Intent intent = new Intent(OlvidoDeContrasena.this, ventanaSMS.class);
                            startActivity(intent);
                        }else{
                            Toast.makeText(OlvidoDeContrasena.this, "No se ha encontrado el usuario", Toast.LENGTH_SHORT).show();
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