package com.example.unilibrettobonusium;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {


    public DBHelper(Context context) {
        super(context, "Database_UE2", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase DB) {
        DB.execSQL("create Table l_utenti(username TEXT primary key, nome TEXT, cognome TEXT, data TEXT, password TEXT, log TEXT)");
        DB.execSQL("create table l_esami(nome TEXT, voto INT, cfu INT, user TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase DB, int i, int i1) {
        DB.execSQL("drop Table if exists l_utenti");
        DB.execSQL("drop table if exists l_esami");
    }

    public Boolean registrazione(String username, String nome, String cognome, String data, String password, String log){
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();
        contentValues.put("username", username);
        contentValues.put("nome", nome);
        contentValues.put("cognome", cognome);
        contentValues.put("data", data);
        contentValues.put("password", password);
        contentValues.put("log", log);
        long result = DB.insert("l_utenti", null, contentValues);
        if(result==-1){ return false;}
        else{ return true;}
    }

    public Boolean checkusername(String username) {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from l_utenti where username = ?", new String[]{username});
        if (cursor.getCount() > 0){ return true;}
        else{ return false;}
    }

    public Boolean checkusernamepassword(String username, String password){
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from l_utenti where username = ? and password = ?", new String[] {username,password});
        if(cursor.getCount()>0)
            return true;
        else
            return false;
    }

    public Boolean updateData(String username, String log){
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();
        contentValues.put("log", log);

        Cursor cursor = DB.rawQuery("Select log from l_utenti where username = ?", new String[]{username});
        if(cursor.getCount()>0){
            long result = DB.update("l_utenti", contentValues, "username = ?", new String[]{username});
            if(result==-1)
                return false;
            else
                return true;
        }else
            return false;
    }

    public Cursor getlogged(){
        SQLiteDatabase DB = this.getWritableDatabase();

        Cursor cursor = null;
        if(DB != null)
            cursor = DB.rawQuery("Select username, nome, cognome, data from l_utenti where log = ?", new String[]{"t"});
        return cursor;
    }

    /*****************************************************/


    public Boolean salvaesame(String nome, String voto, String cfu, String user){
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("nome", nome);
        contentValues.put("voto", voto);
        contentValues.put("cfu", cfu);
        contentValues.put("user", user);
        long result = DB.insert("l_esami", null, contentValues);
        if(result == -1){
            return false;
        }
        else{
            return true;
        }
    }

    public Integer elimina_e(String nome, String user){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("l_esami", "nome = ? and user = ?", new String[]{nome, user});
    }

    public Cursor getdata_user(String user){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery("Select * from l_esami where user = ?", new String[]{user});
        }

        return cursor;
    }
}

