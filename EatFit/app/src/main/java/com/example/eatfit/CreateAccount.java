package com.example.eatfit;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CreateAccount extends AppCompatActivity {

    EditText editTextFechaNacimiento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        EatFit eatFit =new EatFit(this);

        Logica logica=new Logica(eatFit);

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
                //Si no existe el nick
                if(!logica.existeNick(editTextUser.getText().toString())){

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
                        // Gets the data repository in write mode
                        SQLiteDatabase db = eatFit.getWritableDatabase();

                        // Create a new map of values, where column names are the keys
                        ContentValues values = new ContentValues();
                        values.put(Usuarios.Table.COLUMN_NAME_Nick, nickString);
                        values.put(Usuarios.Table.COLUMN_NAME_Password, pwdString);
                        values.put(Usuarios.Table.COLUMN_NAME_Email, emailString);
                        values.put(Usuarios.Table.COLUMN_NAME_DNI, dniString);
                        values.put(Usuarios.Table.COLUMN_NAME_Altura, alturaDouble);
                        values.put(Usuarios.Table.COLUMN_NAME_Peso, pesoDouble);
                        values.put(Usuarios.Table.COLUMN_NAME_seHaLogeado, 0);
                        values.put(Usuarios.Table.COLUMN_NAME_FechaNacimiento, fechaNacString);
                        values.put(Usuarios.Table.COLUMN_NAME_NumTelefono,numTelefonoString);
                        values.put(Usuarios.Table.COLUMN_NAME_NumRutina, 0);

                        // Insert the new row, returning the primary key value of the new row
                        long newRowId = db.insert(Usuarios.Table.TABLE_NAME, null, values);

                        Intent intent = new Intent(CreateAccount.this, Login.class);
                        startActivity(intent);
                    }

                //Sino, le decimos al usuario que introduzca otro nick
                }else{
                    Context context = getApplicationContext();
                    CharSequence text = "Escoga otro nombre de usuario";
                    int duration = Toast.LENGTH_LONG;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
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
        d.show();
    }

}