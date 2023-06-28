package com.example.mini_projet_meteo.Data_Base;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.HashMap;

public class DataBase extends SQLiteOpenHelper {
    public DataBase(@Nullable Context context) {
        super(context, "db_meteo", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE METEO(ID INTEGER PRIMARY KEY AUTOINCREMENT,NOM TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS METEO");
        this.onCreate(db);
    }

    public void ajouterVille(String nom){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues c=new ContentValues();
        c.put("NOM",nom);
        db.insert("METEO",null,c);
    }

    public void delete(String id){
        SQLiteDatabase db=this.getWritableDatabase();
        db.delete("METEO","id=?",new String[]{id});
    }

    public ArrayList<HashMap<String,String>> getVilles(){
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor c=db.rawQuery("select * from METEO",null);
        ArrayList<HashMap<String,String>> villes=new ArrayList<>();
        c.moveToFirst();
        while(!c.isAfterLast()){
            HashMap<String,String> hm=new HashMap<>();
            hm.put("id",c.getString(0));
            hm.put("nom",c.getString(1));
            villes.add(hm);
            c.moveToNext();
        }
        c.close();
        return villes;
    }
}
