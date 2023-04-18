package com.example.eatfit;

import static android.app.PendingIntent.getActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import java.io.File;
import java.security.Permission;

public class Login extends AppCompatActivity {

    static EditText nick;
    static EatFit eatFit;
    private boolean isActivateRadioButton;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        nick=(EditText) findViewById(R.id.editTextUsuario);
        EditText pwd=(EditText) findViewById(R.id.editTextPwd);
        RadioButton radioButtonSesion=(RadioButton)findViewById(R.id.radioButtonSesion);
        Button buttonOlvidarPassword=(Button) findViewById(R.id.buttonOlvidarPwd);
        preferences=getSharedPreferences("Preferences",MODE_PRIVATE);
        eatFit =new EatFit(this);

        //Creamos un objeto Logica para llamar a métodos que necesitamos.
        Logica logica=new Logica(eatFit, preferences);

        //Si hay base de datos existente que no se cree otra base de datos.
        if(!new File("/data/data/com.example.eatfit/databases/eatfit").exists()) {
            preferences.edit().clear().apply();
            logica.introduceRutinas();
        }
        //Validamos si hay alguna sesion abierta, es decir, si hay algun usuario ya logeado ha querido mantener su sesión abierta
        validarSesion();

        isActivateRadioButton=radioButtonSesion.isChecked();

        radioButtonSesion.setOnClickListener(new View.OnClickListener() {
            //ACTIVADO
            @Override
            public void onClick(View view) {
                //hacemos esto para poder activar y descativar el botón de no cerrar sesion
                if(isActivateRadioButton)
                    radioButtonSesion.setChecked(false);

                isActivateRadioButton=radioButtonSesion.isChecked();
            }
        });

        //Boton para iniciar sesión

        Button botonInicioSesion=(Button)findViewById(R.id.buttonInicioSesion);
        botonInicioSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Si existe el usuario
                if (logica.existeUsuarioPorNickYPassword(nick.getText().toString(),pwd.getText().toString())){
                    //Si esta activado el botón para no cerrar sesión, se guardan unas shared preferences
                    if(isActivateRadioButton) {
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString("nick", nick.getText().toString());
                        editor.putString("password", pwd.getText().toString());
                        editor.commit();
                    }
                    //Si es la primera vez que se logea, se muestra un cuestionario
                    if(logica.primeraVezLogeado(nick.getText().toString(),pwd.getText().toString())){
                        Intent intent = new Intent(Login.this, Cuestionario.class);
                        startActivity(intent);
                    //Sino, se muestra el meenu principal
                    }else{
                        Intent intent = new Intent(Login.this, MenuPrincipal.class);
                        startActivity(intent);
                    }
                //Sino se muestra un mensajee de que no existe el usuario
                }else{
                    Context context = getApplicationContext();
                    CharSequence text = "Usuario No Existente";
                    int duration = Toast.LENGTH_LONG;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }

            }
        });

        //Si quiere crear una cuenta
        Button botonCreateAccount=(Button) findViewById(R.id.buttonCreateAccount);
        botonCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, CreateAccount.class);
                startActivity(intent);
            }
        });

        //Pedimos permisos para mandar un sms desde android studio
        if(ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.SEND_SMS)!=PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(Login.this,new String[]{Manifest.permission.SEND_SMS},1);
        }

        //Si ha olvidado la contraseña
       buttonOlvidarPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SmsManager smsmanager=SmsManager.getDefault();
                smsmanager.sendTextMessage("601 36 19 84",null,"Mensaje de prueba",null, null);

                Toast.makeText(Login.this,"MSJ Enviado",Toast.LENGTH_LONG).show();

            }
        });

    }



    private void mostrarVentanaMenuPrincipal(){
        Intent intent = new Intent(Login.this, MenuPrincipal.class);
        startActivity(intent);
    }

    //Lo que hacemos es validar si hay una sesión abierta
    private void validarSesion(){
        String nick=preferences.getString("nick",null);
        String password=preferences.getString("password",null);

        //Si hay sesion abierta nos lleva directamente a la ventana de menu principal
        if(nick!=null && password!=null){
            mostrarVentanaMenuPrincipal();
        }
    }


    //Devuelve el ultimo usuario logeado
    public String ultimoUsuarioLogeado(){
        return nick.getText().toString();
    }

}