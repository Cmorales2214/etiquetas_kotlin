package com.sc703.etiquetas_kotlin.ui.sqlite

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class ModeloSQL(contexto: Context?,nombreBD: String?,factory: SQLiteDatabase.CursorFactory?,
                version:Int) :SQLiteOpenHelper(contexto,nombreBD,factory,version) {

    override fun onCreate(db: SQLiteDatabase?) {
       //db!!.execSQL("CREATE TABLE Estudiantes (ID Int PRIMARY KEY, Nombre VARCHAR(150),Edad INT)")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }

}