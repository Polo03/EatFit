package com.example.eatfitreal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CreateAccount extends AppCompatActivity {

    EditText editTextFechaNacimiento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        EditText editTextUser=(EditText) findViewById(R.id.editTextUserCreate);
        EditText editTextPwd=(EditText) findViewById(R.id.editTextPwdCreate);
        EditText editTextDni=(EditText) findViewById(R.id.editTextDNI);
        EditText editTextEmail=(EditText) findViewById(R.id.editTextEmail);
        EditText editTextPeso=(EditText) findViewById(R.id.editTextPeso);
        EditText editTextAltura=(EditText) findViewById(R.id.editTextAltura);
        editTextFechaNacimiento=(EditText) findViewById(R.id.editTextDate);
        EditText editTextNumTelefono=(EditText) findViewById(R.id.editTextNumTelefono);
        Button botonCrearCuenta=(Button) findViewById(R.id.buttonCrearCuenta);

        botonCrearCuenta.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String[] nick={""};
                String[] email={""};
                String[] dni={""};
                boolean[] unaVez={false};
                //Si el nick contiene la cadena "@gmail.com", mostramos mensaje de que no puede tener esa cadena
                DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();
                myRef.child("Usuarios").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(!unaVez[0]){
                            //List<String> nicks=new ArrayList<>();
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                                if(dataSnapshot.child("nick").getValue().toString().equals(editTextUser.getText().toString()))
                                    nick[0]=dataSnapshot.child("nick").getValue().toString();
                                if(dataSnapshot.child("email").getValue().toString().equals(editTextEmail.getText().toString()))
                                    email[0]=dataSnapshot.child("email").getValue().toString();
                                if(dataSnapshot.child("DNI").getValue().toString().equals(editTextDni.getText().toString()))
                                    dni[0]=dataSnapshot.child("DNI").getValue().toString();
                            }
                            if(editTextUser.getText().toString().contains("@")){

                                Context context = getApplicationContext();
                                CharSequence text = "El nick no puede contener carácteres especiales";
                                int duration = Toast.LENGTH_LONG;

                                Toast toast = Toast.makeText(context, text, duration);
                                toast.show();
                                //Si existe el nick, mostramos mensaje de que el nick ya esta en uso
                            }else if(!nick[0].equals("")){

                                Context context = getApplicationContext();
                                CharSequence text = "El nick ya esta en uso";
                                int duration = Toast.LENGTH_LONG;

                                Toast toast = Toast.makeText(context, text, duration);
                                toast.show();
                                //Si existe el email, mostramos mensaje de que el email ya esta en uso
                            }else if(!email[0].equals("")){

                                Context context = getApplicationContext();
                                CharSequence text = "El email ya esta en uso";
                                int duration = Toast.LENGTH_LONG;

                                Toast toast = Toast.makeText(context, text, duration);
                                toast.show();
                                //Si DNI el email, mostramos mensaje de que el DNI ya esta en uso
                            }else if(!dni[0].equals("")){

                                Context context = getApplicationContext();
                                CharSequence text = "El DNI ya esta en uso";
                                int duration = Toast.LENGTH_LONG;

                                Toast toast = Toast.makeText(context, text, duration);
                                toast.show();
                                //Sino
                            }else{

                                String nickString=editTextUser.getText().toString();
                                String pwdString=editTextPwd.getText().toString();
                                String emailString=editTextEmail.getText().toString();
                                String dniString=editTextDni.getText().toString();
                                Double pesoDouble=Double.parseDouble(editTextPeso.getText().toString());
                                Double alturaDouble=Double.parseDouble(editTextAltura.getText().toString());
                                String fechaNacString=editTextFechaNacimiento.getText().toString();
                                String numTelefonoString=editTextNumTelefono.getText().toString();

                                Pattern patronDNI=Pattern.compile("[0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][A-Z]");
                                Pattern patronEmail=Pattern.compile("[A-Z|a-z|0-9]+@gmail.com");
                                Pattern patronNumTelefono = Pattern.compile("[0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9]");

                                Matcher matcherDNI=patronDNI.matcher(dniString);
                                Matcher matcherEmail=patronEmail.matcher(emailString);
                                Matcher matcherNumTelefono=patronNumTelefono.matcher(numTelefonoString);
                                //Si algun formato de email, dni o telefono no es el adecuado
                                if(!matcherEmail.matches() || !matcherDNI.matches() || !matcherNumTelefono.matches()){
                                    //Mostramos un mensaje de error
                                    Context context = getApplicationContext();
                                    CharSequence text = "Revise los campos";
                                    int duration = Toast.LENGTH_LONG;

                                    Toast toast = Toast.makeText(context, text, duration);
                                    toast.show();
                                    //Sino, insertamos en la base de datos el nuevo usuario, con todos sus datos
                                }else{

                                    Map<String, Object> datosUser = new HashMap<>();
                                    datosUser.put("nick",nickString);
                                    datosUser.put("password",pwdString);
                                    datosUser.put("email",emailString);
                                    datosUser.put("DNI",dniString);
                                    datosUser.put("Peso",pesoDouble);
                                    datosUser.put("Altura",alturaDouble);
                                    datosUser.put("FechaNac",fechaNacString);
                                    datosUser.put("Phone",numTelefonoString);
                                    datosUser.put("VecesLogeado",0);
                                    datosUser.put("NumRutina",0);
                                    myRef.child("Usuarios").child(nickString).setValue(datosUser);

                                    Intent intent = new Intent(CreateAccount.this, Login.class);
                                    startActivity(intent);
                                }

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
    }

    //Para seleccionar la fecha de nacimiento en el calendario
    public void mostrarCalendario(View v){
        DatePickerDialog d=new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                if(month+1<10)
                    if(day<10)
                        editTextFechaNacimiento.setText("0"+day+"/0"+(month+1)+"/"+year);
                    else
                        editTextFechaNacimiento.setText(day+"/0"+(month+1)+"/"+year);
                else
                    if(day<10)
                        editTextFechaNacimiento.setText("0"+day+"/"+(month+1)+"/"+year);
                    else
                        editTextFechaNacimiento.setText(day+"/"+(month+1)+"/"+year);
            }
        },2003,0,1);
        d.setMessage("Seleccione su fecha de nacimiento");
        d.show();
    }

}