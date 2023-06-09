package com.example.ejemplosqlite.bdd;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class BDHelper extends SQLiteOpenHelper {
    public BDHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //CREACIÓN DE LAS TABLAS
            db.execSQL("CREATE TABLE t_RolPagos"+"(" +
                    "usu_funcionario text PRIMARY KEY,"+
                    "usu_cargo text NOT NULL,"+
                    "usu_area text NOT NULL," +
                    "usu_nHijos integer NOT NULL,"+
                    "usu_estadoCivil text NOT NULL," +
                    "usu_atraso text NOT NULL," +
                    "usu_horasExtras integer NOT NULL)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //CAMBIE LA VERSIÓN DE LA TABLA DE LA BDD
        db.execSQL("DROP TABLE t_RolPagos");
        onCreate(db);
    }
}
