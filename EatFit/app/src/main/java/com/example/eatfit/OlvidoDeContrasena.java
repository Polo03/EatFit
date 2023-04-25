package com.example.eatfit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class OlvidoDeContrasena extends AppCompatActivity {

    static EatFit eatFit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_olvido_de_contrasena);

        eatFit =new EatFit(this);
        Logica logica=new Logica(eatFit);

        Button cambiarPassword=(Button) findViewById(R.id.buttonConfirmarCambioPassword);
        EditText usuario=(EditText) findViewById(R.id.editTextUsuarioCambiarContraseña);
        EditText nuevaPwd=(EditText) findViewById(R.id.editTextCambiarContraseña);

        cambiarPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(logica.existeUsuarioPorNick(usuario.getText().toString())){
                   SQLiteDatabase db = eatFit.getReadableDatabase();

                    // New value for one column
                    String title = nuevaPwd.getText().toString();
                    ContentValues values = new ContentValues();
                    values.put(Usuarios.Table.COLUMN_NAME_Password, title);

                    // Which row to update, based on the title
                    String selection = Usuarios.Table.COLUMN_NAME_Nick + " LIKE ?";
                    String[] selectionArgs = { usuario.getText().toString() };

                    int count = db.update(
                            Usuarios.Table.TABLE_NAME,
                            values,
                            selection,
                            selectionArgs);



                    Intent intent = new Intent(OlvidoDeContrasena.this, Login.class);
                    startActivity(intent);
                    Context context = getApplicationContext();

                }else{
                    Context context = getApplicationContext();
                    CharSequence text = "Usuario No Existente";
                    int duration = Toast.LENGTH_LONG;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }

            }
        });
    }

}