package com.example.eatfit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class CreateAccount extends AppCompatActivity {

    static EatFit finalDbHelper;
    static EditText editTextUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        finalDbHelper=new EatFit(this);

        editTextUser=(EditText) findViewById(R.id.editTextUserCreate);
        EditText editTextPwd=(EditText) findViewById(R.id.editTextPwdCreate);
        EditText editTextDni=(EditText) findViewById(R.id.editTextDNI);
        EditText editTextEmail=(EditText) findViewById(R.id.editTextEmail);
        EditText editTextPeso=(EditText) findViewById(R.id.editTextPeso);
        EditText editTextAltura=(EditText) findViewById(R.id.editTextAltura);
        EditText editTextFechaNacimiento=(EditText) findViewById(R.id.editTextFechaNacimiento);
        Button botonCrearCuenta=(Button) findViewById(R.id.buttonCrearCuenta);

        botonCrearCuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!existeNick()){

                    String nickString=editTextUser.getText().toString();
                    String pwdString=editTextPwd.getText().toString();
                    String emailString=editTextEmail.getText().toString();
                    String dniString=editTextDni.getText().toString();
                    Double pesoDouble=Double.parseDouble(editTextPeso.getText().toString());
                    Double alturaDouble=Double.parseDouble(editTextAltura.getText().toString());
                    String fechaNacString=editTextFechaNacimiento.getText().toString();

                    // Gets the data repository in write mode
                    SQLiteDatabase db = finalDbHelper.getWritableDatabase();

                    // Create a new map of values, where column names are the keys
                    ContentValues values = new ContentValues();
                    values.put(Usuarios.Table.COLUMN_NAME_Nick, nickString);
                    values.put(Usuarios.Table.COLUMN_NAME_Password, pwdString);
                    values.put(Usuarios.Table.COLUMN_NAME_Email, emailString);
                    values.put(Usuarios.Table.COLUMN_NAME_DNI, dniString);
                    values.put(Usuarios.Table.COLUMN_NAME_Altura, alturaDouble);
                    values.put(Usuarios.Table.COLUMN_NAME_Peso, pesoDouble);
                    values.put(Usuarios.Table.COLUMN_NAME_FechaNacimiento, fechaNacString);
                    values.put(Usuarios.Table.COLUMN_NAME_seHaLogeado, false);

                    // Insert the new row, returning the primary key value of the new row
                    long newRowId = db.insert(Usuarios.Table.TABLE_NAME, null, values);

                    Intent intent = new Intent(CreateAccount.this, Login.class);
                    startActivity(intent);
                }else{
                    Context context = getApplicationContext();
                    CharSequence text = "Escoga otro nick";
                    int duration = Toast.LENGTH_LONG;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
            }
        });
    }

    public static Integer dameUltimoUsuario(){
        SQLiteDatabase db = finalDbHelper.getReadableDatabase();
        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                BaseColumns._ID,
        };

        // How you want the results sorted in the resulting Cursor
        String sortOrder = Usuarios.Table._ID + " DESC";

        Cursor cursor = db.query(
                Usuarios.Table.TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                null,              // The columns for the WHERE clause
                null,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                sortOrder               // The sort order
        );

        if (cursor.moveToNext()){
            int numUsuarioInt = cursor.getInt(cursor.getColumnIndexOrThrow(Usuarios.Table._ID));
            return numUsuarioInt;
        }
        return 0;
    }

    public static boolean existeNick(){
        SQLiteDatabase db = finalDbHelper.getReadableDatabase();
        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                Usuarios.Table.COLUMN_NAME_Nick
        };

        Cursor cursor = db.query(
                Usuarios.Table.TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                null,              // The columns for the WHERE clause
                null,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                null               // The sort order
        );

        List itemIds = new ArrayList<>();
        while(cursor.moveToNext()) {
            String nick = cursor.getString(
                    cursor.getColumnIndexOrThrow(Usuarios.Table.COLUMN_NAME_Nick));
            itemIds.add(nick);
        }
        cursor.close();

        boolean existe=false;

        String nickString=editTextUser.getText().toString();


        for(int i=0;i<itemIds.size();i++){
            if(itemIds.get(i).equals(nickString)){
                return true;
            }
        }
        return false;
    }
}