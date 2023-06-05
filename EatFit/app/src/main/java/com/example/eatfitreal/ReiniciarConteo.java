package com.example.eatfitreal;

import static android.content.Context.MODE_PRIVATE;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ReiniciarConteo extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
        Calendar calendario = Calendar.getInstance();
        long ahora = System.currentTimeMillis();
        calendario.setTimeInMillis(ahora);
        Date fecha = new Date(ahora);
        int dia=calendario.get(Calendar.DAY_OF_MONTH);
        int year=calendario.get(Calendar.YEAR);
        int hora = fecha.getHours()+2;
        int min= fecha.getMinutes();
        int seg= fecha.getSeconds();

        Login l=new Login();

        String nickStr="";

        SharedPreferences preferences = context.getSharedPreferences("Preferences", MODE_PRIVATE);
        if(preferences.getString("nick", null)==null)
            nickStr=l.ultimoUsuarioLogeado();
        else
            nickStr=preferences.getString("nick", null);

        if(min==27){

            Log.e("Estado","Min==27");
            DatabaseReference myRef= FirebaseDatabase.getInstance().getReference();
            Map<String, Object> calorias = new HashMap<>();
            calorias.put("nick",nickStr);
            calorias.put("caloriasDeseadas",0);
            calorias.put("caloriasConsumidas",0);
            calorias.put("caloriasEstablecidas",0);
            myRef.child("Calorias").child(nickStr).setValue(calorias);
        }
    }
}
