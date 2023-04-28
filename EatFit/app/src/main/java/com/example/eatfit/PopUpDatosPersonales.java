package com.example.eatfit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.TextView;

public class PopUpDatosPersonales extends AppCompatActivity {

    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up_datos_personales);

        EatFit eatFit=new EatFit(this);
        Login l=new Login();
        preferences = getSharedPreferences("Preferences", MODE_PRIVATE);

        //Para las medidas de la ventana del pop up
        DisplayMetrics medidasVentana=new DisplayMetrics();

        getWindowManager().getDefaultDisplay().getMetrics(medidasVentana);

        int ancho=medidasVentana.widthPixels;
        int alto=medidasVentana.heightPixels;

        getWindow().setLayout((int)(ancho * 0.85), (int) (alto * 0.7));

        String nick="";

        if(preferences.getString("nick", null)==null){
            nick=l.ultimoUsuarioLogeado();
        }else{
            nick=preferences.getString("nick", null);
        }

        TextView textViewNick=findViewById(R.id.textViewNick);
        TextView textViewPwd=findViewById(R.id.textViewPwd);
        TextView textViewEmail=findViewById(R.id.textViewEmail);
        TextView textViewDNI=findViewById(R.id.textViewDNI);
        TextView textViewPeso=findViewById(R.id.textViewPeso);
        TextView textViewAltura=findViewById(R.id.textViewAltura);
        TextView textViewFechaNac=findViewById(R.id.textViewFechaNac);
        TextView textViewNumTelefono=findViewById(R.id.textViewNumTelefono);

        SQLiteDatabase db = eatFit.getReadableDatabase();

        if(!nick.contains("@gmail.com")){
            String[] projection = {
                    Usuarios.Table.COLUMN_NAME_Nick,
                    Usuarios.Table.COLUMN_NAME_Password,
                    Usuarios.Table.COLUMN_NAME_Email,
                    Usuarios.Table.COLUMN_NAME_DNI,
                    Usuarios.Table.COLUMN_NAME_Peso,
                    Usuarios.Table.COLUMN_NAME_Altura,
                    Usuarios.Table.COLUMN_NAME_FechaNacimiento,
                    Usuarios.Table.COLUMN_NAME_NumTelefono
            };

            String selection = Usuarios.Table.COLUMN_NAME_Nick + " = ? ";
            String[] selectionArgs = { nick };


            Cursor cursor = db.query(
                    Usuarios.Table.TABLE_NAME,   // The table to query
                    projection,             // The array of columns to return (pass null to get all)
                    selection,              // The columns for the WHERE clause
                    selectionArgs,          // The values for the WHERE clause
                    null,                   // don't group the rows
                    null,                   // don't filter by row groups
                    null                    // The sort order
            );

            if(cursor.moveToNext()) {
                textViewNick.setText("NICK-->" + cursor.getString(cursor.getColumnIndexOrThrow(Usuarios.Table.COLUMN_NAME_Nick)));
                textViewPwd.setText("CONTRASEÑA-->" + cursor.getString(cursor.getColumnIndexOrThrow(Usuarios.Table.COLUMN_NAME_Password)));
                textViewEmail.setText("EMAIL-->" + cursor.getString(cursor.getColumnIndexOrThrow(Usuarios.Table.COLUMN_NAME_Email)));
                textViewDNI.setText("DNI-->" + cursor.getString(cursor.getColumnIndexOrThrow(Usuarios.Table.COLUMN_NAME_DNI)));
                textViewPeso.setText("PESO-->" + cursor.getDouble(cursor.getColumnIndexOrThrow(Usuarios.Table.COLUMN_NAME_Peso)));
                textViewAltura.setText("ALTURA-->" + cursor.getDouble(cursor.getColumnIndexOrThrow(Usuarios.Table.COLUMN_NAME_Altura)));
                textViewFechaNac.setText("FECHANAC-->" + cursor.getString(cursor.getColumnIndexOrThrow(Usuarios.Table.COLUMN_NAME_FechaNacimiento)));
                textViewNumTelefono.setText("NUMTELEFONO-->" + cursor.getString(cursor.getColumnIndexOrThrow(Usuarios.Table.COLUMN_NAME_NumTelefono)));
            }
        }else{
            String[] projection = {
                    Usuarios.Table.COLUMN_NAME_Nick,
                    Usuarios.Table.COLUMN_NAME_Password,
                    Usuarios.Table.COLUMN_NAME_Email,
                    Usuarios.Table.COLUMN_NAME_DNI,
                    Usuarios.Table.COLUMN_NAME_Peso,
                    Usuarios.Table.COLUMN_NAME_Altura,
                    Usuarios.Table.COLUMN_NAME_FechaNacimiento,
                    Usuarios.Table.COLUMN_NAME_NumTelefono

            };

            String selection = Usuarios.Table.COLUMN_NAME_Email + " = ? ";
            String[] selectionArgs = { nick };


            Cursor cursor = db.query(
                    Usuarios.Table.TABLE_NAME,   // The table to query
                    projection,             // The array of columns to return (pass null to get all)
                    selection,              // The columns for the WHERE clause
                    selectionArgs,          // The values for the WHERE clause
                    null,                   // don't group the rows
                    null,                   // don't filter by row groups
                    null                    // The sort order
            );

            if(cursor.moveToNext()) {
                textViewNick.setText("NICK-->" + cursor.getString(cursor.getColumnIndexOrThrow(Usuarios.Table.COLUMN_NAME_Nick)));
                textViewPwd.setText("CONTRASEÑA-->" + cursor.getString(cursor.getColumnIndexOrThrow(Usuarios.Table.COLUMN_NAME_Password)));
                textViewEmail.setText("EMAIL-->" + cursor.getString(cursor.getColumnIndexOrThrow(Usuarios.Table.COLUMN_NAME_Email)));
                textViewDNI.setText("DNI-->" + cursor.getString(cursor.getColumnIndexOrThrow(Usuarios.Table.COLUMN_NAME_DNI)));
                textViewPeso.setText("PESO-->" + cursor.getDouble(cursor.getColumnIndexOrThrow(Usuarios.Table.COLUMN_NAME_Peso)));
                textViewAltura.setText("ALTURA-->" + cursor.getDouble(cursor.getColumnIndexOrThrow(Usuarios.Table.COLUMN_NAME_Altura)));
                textViewFechaNac.setText("FECHANAC-->" + cursor.getString(cursor.getColumnIndexOrThrow(Usuarios.Table.COLUMN_NAME_FechaNacimiento)));
                textViewNumTelefono.setText("NUMTELEFONO-->" + cursor.getString(cursor.getColumnIndexOrThrow(Usuarios.Table.COLUMN_NAME_NumTelefono)));
            }
        }




    }
}