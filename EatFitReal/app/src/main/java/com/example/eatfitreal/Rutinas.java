package com.example.eatfitreal;

import android.provider.BaseColumns;

public class Rutinas {

    private Rutinas() {}

    public static class Table implements BaseColumns {
        public static final String TABLE_NAME = "rutinas";
        public static final String COLUMN_NAME_NumRutina= "numRutina";
        public static final String COLUMN_NAME_DescripcionRutina = "descripcionRutina";

        public static final String COLUMN_NAME_ImagenRutina = "imagenRutina";

    }

}
