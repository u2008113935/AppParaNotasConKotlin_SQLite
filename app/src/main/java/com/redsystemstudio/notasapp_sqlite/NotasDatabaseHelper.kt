package com.redsystemstudio.notasapp_sqlite

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class NotasDatabaseHelper (context: Context) : SQLiteOpenHelper(
    context, DATABASE_NAME, null , DATABASE_VERSION) {

    companion object{
        private const val DATABASE_NAME = "notas.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "notas"
        private const val COLUMN_ID = "id"
        private const val COLUMN_TITLE = "titulo"
        private const val COLUMN_DESCRIPTION = "descripcion"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableQuery =
            "CREATE TABLE $TABLE_NAME ($COLUMN_ID INTEGER PRIMARY KEY, $COLUMN_TITLE TEXT, $COLUMN_DESCRIPTION TEXT)"
        db?.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val dropTableQuery =
            "DROP TABLE IF EXISTS $TABLE_NAME"
        db?.execSQL(dropTableQuery)
        onCreate(db)
    }

    fun insertNota(nota : Nota){
        val db = writableDatabase

        val values = ContentValues().apply {
            put(COLUMN_TITLE, nota.titulo)
            put(COLUMN_DESCRIPTION, nota.descripcion)
        }

        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    fun getAllNotas() : List<Nota>{

        val listaNotas = mutableListOf<Nota>()
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME"
        val cursor = db.rawQuery(query, null)

        while (cursor.moveToNext()){
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
            val titulo = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE))
            val descripcion = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPTION))

            val nota = Nota (id, titulo, descripcion)
            listaNotas.add(nota)

        }

        cursor.close()
        db.close()
        return listaNotas
    }

    fun getIdNota(idNota : Int) : Nota{
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME WHERE $COLUMN_ID = $idNota"
        val cursor = db.rawQuery(query, null)
        cursor.moveToFirst()

        val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
        val titulo = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE))
        val descripcion = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPTION))

        cursor.close()
        db.close()
        return Nota(id, titulo , descripcion)
    }

    fun updateNota (nota : Nota){
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_TITLE, nota.titulo)
            put(COLUMN_DESCRIPTION, nota.descripcion)
        }

        val whereClause = "$COLUMN_ID = ?"
        val whereArgs = arrayOf(nota.id.toString())
        db.update(TABLE_NAME, values , whereClause , whereArgs)
        db.close()
    }

    fun deleteNota (idNota : Int){
        val db = writableDatabase
        val whereClaus = "$COLUMN_ID = ?"
        val whereArgs = arrayOf(idNota.toString())
        db.delete(TABLE_NAME, whereClaus , whereArgs)
        db.close()
    }


}