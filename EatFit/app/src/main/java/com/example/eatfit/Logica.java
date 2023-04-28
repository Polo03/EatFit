package com.example.eatfit;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/*La logica es una clase que gestionará todos los métodos que se usarán en las distintas
* ventanas*/

public class Logica {

    EatFit eatFit;
    private SharedPreferences preferences;

    public Logica(EatFit eatfit) {
        this.eatFit=eatfit;
    }
    public Logica(EatFit eatfit, SharedPreferences preferences) {
        this.eatFit=eatfit;
        this.preferences=preferences;
    }

    public boolean existeUsuarioPorNick(String usuario){

        SQLiteDatabase db = eatFit.getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                Usuarios.Table.COLUMN_NAME_Nick
        };

        // Filter results WHERE "title" = 'My Title'
        String selection = Usuarios.Table.COLUMN_NAME_Nick + " = ?";
        String[] selectionArgs = { usuario };


        Cursor cursor = db.query(
                Usuarios.Table.TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                selectionArgs,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                null               // The sort order
        );

        if(cursor.moveToNext()) {
            return true;
        }
        cursor.close();
        return false;
    }

    public boolean existeUsuarioPorEmail(String email){

        SQLiteDatabase db = eatFit.getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                Usuarios.Table.COLUMN_NAME_Nick
        };

        // Filter results WHERE "title" = 'My Title'
        String selection = Usuarios.Table.COLUMN_NAME_Email + " = ?";
        String[] selectionArgs = { email };


        Cursor cursor = db.query(
                Usuarios.Table.TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                selectionArgs,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                null               // The sort order
        );

        if(cursor.moveToNext()) {
            return true;
        }
        cursor.close();
        return false;
    }

    public boolean existeEmail(String email){
        SQLiteDatabase db = eatFit.getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                Usuarios.Table.COLUMN_NAME_Email
        };

        // Filter results WHERE "title" = 'My Title'
        String selection = Usuarios.Table.COLUMN_NAME_Email + " = ?";
        String[] selectionArgs = { email };


        Cursor cursor = db.query(
                Usuarios.Table.TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                selectionArgs,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                null               // The sort order
        );

        if(cursor.moveToNext()) {
            return true;
        }
        cursor.close();
        return false;
    }

    public boolean existeDNI(String dni){
        SQLiteDatabase db = eatFit.getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                Usuarios.Table.COLUMN_NAME_DNI
        };

        // Filter results WHERE "title" = 'My Title'
        String selection = Usuarios.Table.COLUMN_NAME_DNI + " = ?";
        String[] selectionArgs = { dni };


        Cursor cursor = db.query(
                Usuarios.Table.TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                selectionArgs,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                null               // The sort order
        );

        if(cursor.moveToNext()) {
            return true;
        }
        cursor.close();
        return false;
    }

    public boolean existeUsuarioPorNickYPassword(String nick, String pwd){

        SQLiteDatabase db = eatFit.getReadableDatabase();

        if(!nick.contains("@gmail.com")){
            String[] projection = {
                    Usuarios.Table.COLUMN_NAME_Nick,
                    Usuarios.Table.COLUMN_NAME_Password
            };

            String selection = Usuarios.Table.COLUMN_NAME_Nick + " = ? AND "+ Usuarios.Table.COLUMN_NAME_Password + " = ? ";
            String[] selectionArgs = { nick , pwd };


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
                return true;
            }
        }else{
            String[] projection = {
                    Usuarios.Table.COLUMN_NAME_Email,
                    Usuarios.Table.COLUMN_NAME_Password
            };

            String selection = Usuarios.Table.COLUMN_NAME_Email + " = ? AND "+ Usuarios.Table.COLUMN_NAME_Password + " = ? ";
            String[] selectionArgs = { nick , pwd };


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
                return true;
            }
        }
        return false;
    }

    public String getNickByCorreo(String email){
        SQLiteDatabase db = eatFit.getReadableDatabase();

        String[] projection = {
                Usuarios.Table.COLUMN_NAME_Nick
        };

        String selection = Usuarios.Table.COLUMN_NAME_Email + " = ?";
        String[] selectionArgs = { email };


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
            return cursor.getString(cursor.getColumnIndexOrThrow(Usuarios.Table.COLUMN_NAME_Nick));
        }
        return null;

    }

    public String getPhoneByNick(String nick){
        SQLiteDatabase db = eatFit.getReadableDatabase();

        String[] projection = {
                Usuarios.Table.COLUMN_NAME_NumTelefono
        };

        String selection = Usuarios.Table.COLUMN_NAME_Nick + " = ?";
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
            return cursor.getString(cursor.getColumnIndexOrThrow(Usuarios.Table.COLUMN_NAME_NumTelefono));
        }
        return null;

    }

    public boolean primeraVezLogeado(String usuario, String pwd){
        SQLiteDatabase db = eatFit.getReadableDatabase();

        if(!usuario.contains("@gmail.com")){
            String[] projection = {
                    Usuarios.Table.COLUMN_NAME_Nick,
                    Usuarios.Table.COLUMN_NAME_Password,
                    Usuarios.Table.COLUMN_NAME_Email,
                    Usuarios.Table.COLUMN_NAME_seHaLogeado
            };


            String selection = Usuarios.Table.COLUMN_NAME_Nick + " = ? AND "+ Usuarios.Table.COLUMN_NAME_Password + " = ? ";
            String[] selectionArgs = { usuario , pwd };


            Cursor cursor = db.query(
                    Usuarios.Table.TABLE_NAME,   // The table to query
                    projection,             // The array of columns to return (pass null to get all)
                    selection,              // The columns for the WHERE clause
                    selectionArgs,          // The values for the WHERE clause
                    null,                   // don't group the rows
                    null,                   // don't filter by row groups
                    null                    // The sort order
            );

            boolean existe=false;
            int[] seHaLogeado={0};
            if(cursor.moveToNext()) {
                seHaLogeado[0]=cursor.getInt(cursor.getColumnIndexOrThrow(Usuarios.Table.COLUMN_NAME_seHaLogeado));
            }

            if(seHaLogeado[0]==0)
                return true;
        }else{
            String[] projection = {
                    Usuarios.Table.COLUMN_NAME_Nick,
                    Usuarios.Table.COLUMN_NAME_Password,
                    Usuarios.Table.COLUMN_NAME_Email,
                    Usuarios.Table.COLUMN_NAME_seHaLogeado
            };


            String selection = Usuarios.Table.COLUMN_NAME_Email + " = ? AND "+ Usuarios.Table.COLUMN_NAME_Password + " = ? ";
            String[] selectionArgs = { usuario , pwd };


            Cursor cursor = db.query(
                    Usuarios.Table.TABLE_NAME,   // The table to query
                    projection,             // The array of columns to return (pass null to get all)
                    selection,              // The columns for the WHERE clause
                    selectionArgs,          // The values for the WHERE clause
                    null,                   // don't group the rows
                    null,                   // don't filter by row groups
                    null                    // The sort order
            );

            boolean existe=false;
            int[] seHaLogeado={0};
            if(cursor.moveToNext()) {
                seHaLogeado[0]=cursor.getInt(cursor.getColumnIndexOrThrow(Usuarios.Table.COLUMN_NAME_seHaLogeado));
            }

            if(seHaLogeado[0]==0)
                return true;
        }
        return false;
    }

    public void introduceRutinas(){
        SQLiteDatabase db = eatFit.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values1 = new ContentValues();
        values1.put(Rutinas.Table.COLUMN_NAME_NumRutina, "1");
        values1.put(Rutinas.Table.COLUMN_NAME_DescripcionRutina, "Rutina 1");
        // Insert the new row, returning the primary key value of the new row
        long newRowId1 = db.insert(Rutinas.Table.TABLE_NAME, null, values1);

        // Create a new map of values, where column names are the keys
        ContentValues values2 = new ContentValues();
        values2.put(Rutinas.Table.COLUMN_NAME_NumRutina, "2");
        values2.put(Rutinas.Table.COLUMN_NAME_DescripcionRutina, "Rutina 2");
        // Insert the new row, returning the primary key value of the new row
        long newRowId2 = db.insert(Rutinas.Table.TABLE_NAME, null, values2);

        // Create a new map of values, where column names are the keys
        ContentValues values3 = new ContentValues();
        values3.put(Rutinas.Table.COLUMN_NAME_NumRutina, "3");
        values3.put(Rutinas.Table.COLUMN_NAME_DescripcionRutina, "Rutina 3");
        // Insert the new row, returning the primary key value of the new row
        long newRowId3 = db.insert(Rutinas.Table.TABLE_NAME, null, values3);

        // Create a new map of values, where column names are the keys
        ContentValues values4 = new ContentValues();
        values4.put(Rutinas.Table.COLUMN_NAME_NumRutina, "4");
        values4.put(Rutinas.Table.COLUMN_NAME_DescripcionRutina, "Rutina 4");
        // Insert the new row, returning the primary key value of the new row
        long newRowId4 = db.insert(Rutinas.Table.TABLE_NAME, null, values4);

        // Create a new map of values, where column names are the keys
        ContentValues values5 = new ContentValues();
        values5.put(Rutinas.Table.COLUMN_NAME_NumRutina, "5");
        values5.put(Rutinas.Table.COLUMN_NAME_DescripcionRutina, "Rutina 5");
        // Insert the new row, returning the primary key value of the new row
        long newRowId5 = db.insert(Rutinas.Table.TABLE_NAME, null, values5);
    }

    public void actualizaARutina1(String nick){

        SQLiteDatabase db = eatFit.getWritableDatabase();

        if(!nick.contains("@gmail.com")){
            ContentValues values = new ContentValues();
            values.put(Usuarios.Table.COLUMN_NAME_seHaLogeado, true);
            values.put(Usuarios.Table.COLUMN_NAME_NumRutina, 1);
            Login l = new Login();

            String selection = Usuarios.Table.COLUMN_NAME_Nick + " LIKE ?";
            String[] selectionArgs = {nick};
            int count = db.update(
                    Usuarios.Table.TABLE_NAME,
                    values,
                    selection,
                    selectionArgs);
        }else{
            ContentValues values = new ContentValues();
            values.put(Usuarios.Table.COLUMN_NAME_seHaLogeado, true);
            values.put(Usuarios.Table.COLUMN_NAME_NumRutina, 1);
            Login l = new Login();

            String selection = Usuarios.Table.COLUMN_NAME_Email + " LIKE ?";
            String[] selectionArgs = {nick};
            int count = db.update(
                    Usuarios.Table.TABLE_NAME,
                    values,
                    selection,
                    selectionArgs);
        }


    }

    public void actualizaARutina2(String nick){
        SQLiteDatabase db = eatFit.getWritableDatabase();

        if(!nick.contains("@gmail.com")){
            ContentValues values = new ContentValues();
            values.put(Usuarios.Table.COLUMN_NAME_seHaLogeado, true);
            values.put(Usuarios.Table.COLUMN_NAME_NumRutina, 2);
            Login l = new Login();

            String selection = Usuarios.Table.COLUMN_NAME_Nick + " LIKE ?";
            String[] selectionArgs = {nick};
            int count = db.update(
                    Usuarios.Table.TABLE_NAME,
                    values,
                    selection,
                    selectionArgs);
        }else{
            ContentValues values = new ContentValues();
            values.put(Usuarios.Table.COLUMN_NAME_seHaLogeado, true);
            values.put(Usuarios.Table.COLUMN_NAME_NumRutina, 2);
            Login l = new Login();

            String selection = Usuarios.Table.COLUMN_NAME_Email + " LIKE ?";
            String[] selectionArgs = {nick};
            int count = db.update(
                    Usuarios.Table.TABLE_NAME,
                    values,
                    selection,
                    selectionArgs);
        }
    }

    public void actualizaARutina3(String nick){
        SQLiteDatabase db = eatFit.getWritableDatabase();
        // New value for one column
        ContentValues values = new ContentValues();
        values.put(Usuarios.Table.COLUMN_NAME_seHaLogeado, true);
        values.put(Usuarios.Table.COLUMN_NAME_NumRutina, 3);
        Login l = new Login();

        // Which row to update, based on the title
        String selection = Usuarios.Table.COLUMN_NAME_Nick + " LIKE ?";
        String[] selectionArgs = {nick};
        int count = db.update(
                Usuarios.Table.TABLE_NAME,
                values,
                selection,
                selectionArgs);
    }

    public void actualizaARutina4(String nick){
        SQLiteDatabase db = eatFit.getWritableDatabase();
        // New value for one column
        ContentValues values = new ContentValues();
        values.put(Usuarios.Table.COLUMN_NAME_seHaLogeado, true);
        values.put(Usuarios.Table.COLUMN_NAME_NumRutina, 4);
        Login l = new Login();

        // Which row to update, based on the title
        String selection = Usuarios.Table.COLUMN_NAME_Nick + " LIKE ?";
        String[] selectionArgs = {nick};
        int count = db.update(
                Usuarios.Table.TABLE_NAME,
                values,
                selection,
                selectionArgs);
    }

    public void actualizaARutina5(String nick){
        SQLiteDatabase db = eatFit.getWritableDatabase();
        // New value for one column
        ContentValues values = new ContentValues();
        values.put(Usuarios.Table.COLUMN_NAME_seHaLogeado, true);
        values.put(Usuarios.Table.COLUMN_NAME_NumRutina, 5);
        Login l = new Login();

        // Which row to update, based on the title
        String selection = Usuarios.Table.COLUMN_NAME_Nick + " LIKE ?";
        String[] selectionArgs = {nick};
        int count = db.update(
                Usuarios.Table.TABLE_NAME,
                values,
                selection,
                selectionArgs);
    }

    public boolean existeNick(String nickString){
        SQLiteDatabase db = eatFit.getReadableDatabase();
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

        for(int i=0;i<itemIds.size();i++){
            if(itemIds.get(i).equals(nickString)){
                return true;
            }
        }
        return false;
    }



    public String getRutina(String nick){
        //String nick = preferences.getString("nick",null);
        //String pwd = preferences.getString("password",null);

        SQLiteDatabase db = eatFit.getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                Usuarios.Table.COLUMN_NAME_Nick,
                Usuarios.Table.COLUMN_NAME_NumRutina,
        };

        // Filter results WHERE "title" = 'My Title'
        String selection = Usuarios.Table.COLUMN_NAME_Nick + " = ?";
        String[] selectionArgs = { nick };


        Cursor cursor = db.query(
                Usuarios.Table.TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                selectionArgs,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                null               // The sort order
        );

        int[] numRutina={0};
        if(cursor.moveToNext()) {
            numRutina[0]=cursor.getInt(cursor.getColumnIndexOrThrow(Usuarios.Table.COLUMN_NAME_NumRutina));
        }

        //////////////////////////////////////////////////////////////////////////////////

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection2 = {
                Rutinas.Table.COLUMN_NAME_DescripcionRutina,
                Rutinas.Table.COLUMN_NAME_ImagenRutina
        };

        // Filter results WHERE "title" = 'My Title'
        String selection2 = Rutinas.Table.COLUMN_NAME_NumRutina + " = ?";
        String[] selectionArgs2 = { numRutina[0]+"" };


        Cursor cursor2 = db.query(
                Rutinas.Table.TABLE_NAME,   // The table to query
                projection2,             // The array of columns to return (pass null to get all)
                selection2,              // The columns for the WHERE clause
                selectionArgs2,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                null               // The sort order
        );

        if(cursor2.moveToNext()) {
            return cursor2.getString(cursor2.getColumnIndexOrThrow(Rutinas.Table.COLUMN_NAME_DescripcionRutina));
        }
        return null;
    }


}
