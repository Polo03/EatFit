package com.example.eatfitreal;

import static android.app.PendingIntent.getActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {

    static EditText nick;
    private boolean isActivateRadioButton,esVisible;
    private SharedPreferences preferences;

    private DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        nick=(EditText) findViewById(R.id.editTextUsuario);
        EditText pwd=(EditText) findViewById(R.id.editTextPwd);
        RadioButton radioButtonSesion=(RadioButton)findViewById(R.id.radioButtonSesion);
        TextView textViewOlvidarPwd=(TextView) findViewById(R.id.textViewMostrarPregunta);
        ImageButton vision=(ImageButton) findViewById(R.id.imageButtonVision);

        preferences=getSharedPreferences("Preferences",MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        //eliminarRoot();
        //introduceRutinas();
        //introduceUserRoot();
        //introduceDietas();
        //introducePreguntas();
        //introduceCaloriasPersonales();
        //introduceAlimentos();
        //cuestionarioRoot();

        Calendar calendario = Calendar.getInstance();
        long ahora = System.currentTimeMillis();
        Date fecha = new Date(ahora);
        int hora = fecha.getHours();
        int min=fecha.getMinutes();
        int seg=fecha.getSeconds();

        //if(hora==14){
            /*NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                    .setContentTitle("Conteo")
                    .setContentText("Es hora de que haga su conteo diario de calorias")
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT);*/
            //Toast.makeText(this, "a", Toast.LENGTH_SHORT).show();
        //}

        //Validamos si hay alguna sesion abierta, es decir, si hay algun usuario ya logueado ha querido mantener su sesión abierta
        validarSesion();

        isActivateRadioButton=radioButtonSesion.isChecked();
        radioButtonSesion.setOnClickListener(new View.OnClickListener() {
            //ACTIVADO
            @Override
            public void onClick(View view) {
                //hacemos esto para poder activar y desactivar el botón de no cerrar sesion
                if(isActivateRadioButton)
                    radioButtonSesion.setChecked(false);
                isActivateRadioButton=radioButtonSesion.isChecked();
            }
        });


        esVisible=true;
        vision.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!esVisible) {
                    pwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    esVisible = true;
                }
                else {
                    pwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    esVisible = false;
                }
            }
        });


        //Boton para iniciar sesión
        Button botonInicioSesion=(Button)findViewById(R.id.buttonInicioSesion);
        botonInicioSesion.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                myRef = FirebaseDatabase.getInstance().getReference();
                myRef.child("Usuarios").addValueEventListener(new ValueEventListener() {
                    String nickString="";
                    String password="";
                    String email="";
                    int vecesLogeado=0;

                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                            if(dataSnapshot.child("nick").getValue().toString().equals(nick.getText().toString()) || dataSnapshot.child("email").getValue().toString().equals(nick.getText().toString())){
                                nickString=dataSnapshot.child("nick").getValue().toString();
                                password=dataSnapshot.child("password").getValue().toString();
                                email=dataSnapshot.child("email").getValue().toString();
                                vecesLogeado=Integer.parseInt(dataSnapshot.child("vecesLogeado").getValue().toString());
                            }
                        }

                        if(password.equals(pwd.getText().toString()) && !password.replaceAll("  ", " ").trim().equals("") && !nickString.replaceAll("  " , " ").trim().equals("")){
                            //Toast.makeText(Login.this, "Usuario Valido", Toast.LENGTH_SHORT).show();
                            if(isActivateRadioButton) {
                                editor.putString("nick", nickString);
                                editor.putString("password", pwd.getText().toString());
                                editor.commit();
                            }

                            //Si es la primera vez que se logea, se muestra un cuestionario
                            if(vecesLogeado==0){
                                Intent intent = new Intent(Login.this, Cuestionario.class);
                                startActivity(intent);
                                //Sino, se muestra el menu principal
                            }else{
                                Intent intent = new Intent(Login.this, MenuPrincipal.class);
                                startActivity(intent);
                            }
                        }else{
                            Toast.makeText(Login.this, "Usuario Invalido", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


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

        //Iniciar sesión con google
        ImageButton botonGoogle=(ImageButton) findViewById(R.id.imageButtonGoogle);
        botonGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //GoogleSignInOptions gso=new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken("HOLA").requestEmail().build();

            }
        });

        TextView terminos=(TextView) findViewById(R.id.textView2);
        terminos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, PopUpTerminosdePrivacidad.class);
                startActivity(intent);
            }
        });

    }

    //Lo que hacemos es validar si hay una sesión abierta
    private void validarSesion(){
        String nick=preferences.getString("nick",null);
        String password=preferences.getString("password",null);

        //Si hay sesion abierta nos lleva directamente a la ventana de menu principal
        if(nick!=null && password!=null){
            Intent intent = new Intent(Login.this, MenuPrincipal.class);
            startActivity(intent);
        }
    }

    //Devuelve el ultimo usuario logeado
    public String ultimoUsuarioLogeado(){
        return nick.getText().toString();
    }

    public void mostrarVentanaOlvidarContrasena(View v){
        Intent intent = new Intent(Login.this, OlvidoDeContrasena.class);
        startActivity(intent);
    }

    public void introduceRutinas(){

        myRef = FirebaseDatabase.getInstance().getReference();

        Map<String, Object> rutina1Definicion = new HashMap<>();
        rutina1Definicion.put("id","1");
        rutina1Definicion.put("ejercicio1","Flexiones de rodillas→ 3 series de 10 repeticiones.");
        rutina1Definicion.put("ejercicio2","Press de mancuernas→ 3 series de 10 repeticiones.");
        rutina1Definicion.put("ejercicio3","Elevaciones laterales con mancuernas→ 3 series de 10 repeticiones.");
        rutina1Definicion.put("ejercicio4","Extensiones de tríceps con mancuernas→ 3 series de 10 repeticiones.");
        rutina1Definicion.put("ejercicio5","Curl de bíceps con mancuernas→ 3 series de 10 repeticiones.");
        //El .child es como una especie de ruta, en este caso, usuarios seria la tabla y el registro es Root.
        myRef.child("Rutinas").child("Definicion").child("BrazoYPecho").child("RutinaPrincipiante").setValue(rutina1Definicion);

        Map<String, Object> rutina1Volumen = new HashMap<>();
        rutina1Volumen.put("id","1");
        rutina1Volumen.put("ejercicio1","Flexiones de pared → Haz 3 series de 10 repeticiones cada una.");
        rutina1Volumen.put("ejercicio2","Flexiones de rodillas → Haz 3 series de 10 repeticiones cada una.");
        rutina1Volumen.put("ejercicio3","Curl de bíceps con mancuernas → Haz 3 series de 12 repeticiones cada una.");
        rutina1Volumen.put("ejercicio4","Press de banca con mancuernas → Haz 3 series de 12 repeticiones cada una.");
        rutina1Volumen.put("ejercicio5","Flexiones diamante → Haz 3 series de 10 repeticiones cada una.");
        //El .child es como una especie de ruta, en este caso, usuarios seria la tabla y el registro es Root.
        myRef.child("Rutinas").child("Volumen").child("BrazoYPecho").child("RutinaPrincipiante").setValue(rutina1Volumen);

    }

    public void introducePreguntas(){

        myRef = FirebaseDatabase.getInstance().getReference();
        Map<String, Object> mensajePrueba = new HashMap<>();
        mensajePrueba.put("id",1);
        mensajePrueba.put("pregunta","¿Pregunta1?");
        mensajePrueba.put("respuesta","Respuesta1");
        myRef.child("Mensajes").child("Mensaje1").setValue(mensajePrueba);

    }

    public void introduceUserRoot(){

        myRef = FirebaseDatabase.getInstance().getReference();

        Map<String, Object> datosRoot = new HashMap<>();
        datosRoot.put("nick","Root");
        datosRoot.put("password","Root");
        datosRoot.put("email","poloadrian3@gmail.com");
        datosRoot.put("DNI","47318093D");
        datosRoot.put("peso","90");
        datosRoot.put("altura","170");
        datosRoot.put("fechaNac","17/10/2003");
        datosRoot.put("phone","601361984");
        datosRoot.put("vecesLogeado",1);
        //El .child es como una especie de ruta, en este caso, usuarios seria la tabla y el registro es Root.
        myRef.child("Usuarios").child("Root").setValue(datosRoot);

    }

    public void eliminarRoot(){
        DatabaseReference mDatabase =FirebaseDatabase.getInstance().getReference();
        DatabaseReference currentUserBD = mDatabase.child("Dietas");
        currentUserBD.removeValue();
    }

    public void cuestionarioRoot(){
        myRef = FirebaseDatabase.getInstance().getReference();

        Map<String, Object> datosUserCuest = new HashMap<>();
        datosUserCuest.put("nick","Root");
        datosUserCuest.put("objetivo1","1");
        datosUserCuest.put("objetivo2","2");
        datosUserCuest.put("objetivo3","3");
        datosUserCuest.put("objetivo4","Adelgazar");
        datosUserCuest.put("objetivo5","5");
        datosUserCuest.put("objetivo6","6");
        myRef.child("Cuestionario").child("Root").setValue(datosUserCuest);
    }
    public void introduceDietas(){

        myRef = FirebaseDatabase.getInstance().getReference();

        Map<String, Object> dietas = new HashMap<>();
        dietas.put("desayuno","Batido de proteínas hecho con leche descremada, proteína en polvo y una cucharada de mantequilla de maní.|Una porción de fruta (por ejemplo, bayas).");
        dietas.put("media_mañana","Palitos de zanahoria con hummus.");
        dietas.put("comida","Ensalada de camarones con hojas verdes, tomate cherry, aguacate y vinagreta ligera.|Una porción de pan integral.");
        dietas.put("merienda","Un yogur griego bajo en grasa.|Un puñado de uvas.");
        dietas.put("cena","Pechuga de pollo a la plancha con brócoli y champiñones salteados.|Una porción de batata asada.");

        myRef.child("Dietas").child("Definicion").child("Domingo").setValue(dietas);

    }

    public void introduceCaloriasPersonales(){

        myRef = FirebaseDatabase.getInstance().getReference();

        Map<String, Object> calorias = new HashMap<>();
        calorias.put("nick","b");
        calorias.put("caloriasDeseadas",3500);
        calorias.put("caloriasConsumidas",500);
        calorias.put("caloriasEstablecidas",0);
        myRef.child("Calorias").child("b").setValue(calorias);

    }
    public void introduceAlimentos(){

        myRef = FirebaseDatabase.getInstance().getReference();

        Map<String, Object> calorias = new HashMap<>();
        calorias.put("nombreAlimento","Queso cottage bajo en grasa");
        calorias.put("cantidad","1/2 taza");
        calorias.put("calorias",82);
        myRef.child("Alimentos").child("Queso cottage bajo en grasa").setValue(calorias);

    }


}