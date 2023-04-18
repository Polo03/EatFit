package com.example.eatfit;

import android.provider.BaseColumns;

public class Usuarios {

    private Usuarios() {}

    public static class Table implements BaseColumns {
        public static final String TABLE_NAME = "usuarios";

        public static final String COLUMN_NAME_NumUsuario= "numUsuario";
        public static final String COLUMN_NAME_Nick = "nick";
        public static final String COLUMN_NAME_Password = "password";
        public static final String COLUMN_NAME_FechaNacimiento = "fechaNacimiento";
        public static final String COLUMN_NAME_DNI = "dni";
        public static final String COLUMN_NAME_Email="email";
        public static final String COLUMN_NAME_Peso="peso";
        public static final String COLUMN_NAME_Altura = "altura";

        public static final String COLUMN_NAME_seHaLogeado = "logeado";

        public static final String COLUMN_NAME_NumTelefono = "numTelefono";

        public static final String COLUMN_NAME_NumRutina = "numRutina";
    }

}
