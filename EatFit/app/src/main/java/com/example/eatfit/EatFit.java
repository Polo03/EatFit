package com.example.eatfit;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class EatFit extends SQLiteOpenHelper {

    // If you change the database schema, you must increment the database version.
    private static final String SQL_CREATE_ENTRIES_Usuarios =
            "CREATE TABLE IF NOT EXISTS " + Usuarios.Table.TABLE_NAME + " (" +
                    Usuarios.Table.COLUMN_NAME_NumUsuario + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    Usuarios.Table.COLUMN_NAME_Nick + " TEXT," +
                    Usuarios.Table.COLUMN_NAME_Password + " TEXT," +
                    Usuarios.Table.COLUMN_NAME_FechaNacimiento + " DATE," +
                    Usuarios.Table.COLUMN_NAME_DNI+" TEXT,"+
                    Usuarios.Table.COLUMN_NAME_Email + " TEXT," +
                    Usuarios.Table.COLUMN_NAME_Peso+" DOUBLE,"+
                    Usuarios.Table.COLUMN_NAME_Altura+" DOUBLE,"+
                    Usuarios.Table.COLUMN_NAME_seHaLogeado + " INTEGER,"+
                    Usuarios.Table.COLUMN_NAME_NumTelefono + " TEXT,"+
                    Usuarios.Table.COLUMN_NAME_NumRutina + " INTEGER,"+
                    "FOREIGN KEY ("+Usuarios.Table.COLUMN_NAME_NumRutina+") " +
                        "REFERENCES "+Rutinas.Table.TABLE_NAME+"("+Rutinas.Table.COLUMN_NAME_NumRutina+"))";

    private static final String SQL_CREATE_ENTRIES_Alimentos =
            "CREATE TABLE " + Alimentos.Table.TABLE_NAME + " (" +
                    Alimentos.Table.COLUMN_NAME_NumAlimento + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    Alimentos.Table.COLUMN_NAME_NombreAlimento + " TEXT," +
                    Alimentos.Table.COLUMN_NAME_CategoriaAlimento + " TEXT," +
                    Alimentos.Table.COLUMN_NAME_Calorias + " INTEGER)";

    private static final String SQL_CREATE_ENTRIES_Rutinas =
            "CREATE TABLE IF NOT EXISTS " + Rutinas.Table.TABLE_NAME + " (" +
                    Rutinas.Table.COLUMN_NAME_NumRutina + " INTEGER PRIMARY KEY," +
                    Rutinas.Table.COLUMN_NAME_DescripcionRutina + " TEXT,"+
                    Rutinas.Table.COLUMN_NAME_ImagenRutina + " BLOB)";


    private static final String SQL_DELETE_ENTRIES_Usuarios =
            "DROP TABLE IF EXISTS " + Usuarios.Table.TABLE_NAME;

    private static final String SQL_DELETE_ENTRIES_Alimentos =
            "DROP TABLE IF EXISTS " + Alimentos.Table.TABLE_NAME;

    private static final String SQL_DELETE_ENTRIES_Rutinas =
            "DROP TABLE IF EXISTS " + Rutinas.Table.TABLE_NAME;

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "eatfit";


    public EatFit(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES_Usuarios);
        db.execSQL(SQL_CREATE_ENTRIES_Alimentos);
        db.execSQL(SQL_CREATE_ENTRIES_Rutinas);

    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES_Usuarios);
        db.execSQL(SQL_DELETE_ENTRIES_Alimentos);
        db.execSQL(SQL_DELETE_ENTRIES_Rutinas);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

}
