package com.example.eatfitreal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ventanaSMS extends AppCompatActivity {

    private EditText editTextCodigo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ventana_sms);

        Button boton=(Button) findViewById(R.id.buttonSiguienteSMS);

        editTextCodigo=(EditText) findViewById(R.id.editTextCodigo);

        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OlvidoDeContrasena o=new OlvidoDeContrasena();
                if(editTextCodigo.getText().toString().equals(o.dameCodigoSMS())){
                    Intent intent = new Intent(ventanaSMS.this, Login.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(ventanaSMS.this, "No es el codigo que te hemos enviado", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}