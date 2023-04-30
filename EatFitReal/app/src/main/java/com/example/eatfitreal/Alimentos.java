package com.example.eatfitreal;

import android.provider.BaseColumns;

public class Alimentos {

    private Alimentos() {}

    public static class Table implements BaseColumns {
        public static final String TABLE_NAME = "alimentos";
        public static final String COLUMN_NAME_NumAlimento = "numAlimento";
        public static final String COLUMN_NAME_NombreAlimento = "nombreAlimento";
        public static final String COLUMN_NAME_CategoriaAlimento = "categoriaAlimento";
        public static final String COLUMN_NAME_Calorias = "calorias";
    }

}
