package com.example.notesappfull

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBhlpr(context: Context) : SQLiteOpenHelper(context, "notesapp.db", null, 1) {

    var sqLiteDatabase : SQLiteDatabase = writableDatabase

    override fun onCreate(db: SQLiteDatabase?) {
        if (db != null){
            db.execSQL("create table notes(Note text)")
        }

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {}

    fun saveData(note: String): Long{
        val cv = ContentValues()
        cv.put("Note",note)
        var status =  sqLiteDatabase.insert("notes",null,cv)
        return status
    }

    @SuppressLint("Range")
    fun retriveAll():ArrayList<String>{
        var al = arrayListOf<String>()
        var c : Cursor = sqLiteDatabase.query("notes",null,null, null,null,null,null)
        if (c.moveToFirst()) {
            do {
                al.add(c.getString(c.getColumnIndex("Note")))
            } while (c.moveToNext())
        }
        return al
    }

    fun updatNote(note: String, upnote: String) : Int{
        sqLiteDatabase=writableDatabase
        val cv = ContentValues()
        cv.put("Note",upnote)
        var j = sqLiteDatabase.update("notes",cv,"Note =?", arrayOf(note))
        // update table notes set Note = upnote where Note =note
        return j
    }

    fun delNote(note: String){
        sqLiteDatabase=writableDatabase
        sqLiteDatabase.delete("notes","Note=?", arrayOf(note))
    }

}