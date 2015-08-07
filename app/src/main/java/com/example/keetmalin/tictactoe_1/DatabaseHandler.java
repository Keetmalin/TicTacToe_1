package com.example.keetmalin.tictactoe_1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Pasindu Tennage on 7/31/2015.
 */
public class DatabaseHandler extends SQLiteOpenHelper {

    private  static final int DAIABASE_VERSION =2;
    private  static final String DATABASE_NAME = "scores.db";
    public static final String TABLE_SCORES = "scores";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_record = "score";

    public DatabaseHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DAIABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE scores(name TEXT,score INTEGER );";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS"+ TABLE_SCORES+";");
        onCreate(db);
    }
    public boolean playerExists(String name){
	SQLiteDatabase db = this.getWritableDatabase();
    String query = "SELECT * FROM "+TABLE_SCORES+" WHERE 1 ;";
    Cursor c = db.rawQuery(query,null);
    if(c!=null && c.getCount()>0){
        c.moveToFirst();
                for(int i = 0 ;i<c.getCount();i++){
                    String a =  c.getString(c.getColumnIndex("name"));
		    	    c.move(1);
                    if(a!=null){
                        if(name.equals(a)){return true;} 
                    }
                }
        //}
     }
        db.close();
        return false;
        
    } 	
    public void insertNewPlayer(String name) {
	SQLiteDatabase db = getWritableDatabase();
    	ContentValues values = new ContentValues();
    	values.put(COLUMN_NAME, name);
    	values.put(COLUMN_record, 0);
    	db.insert(TABLE_SCORES, null,values);
	}
    	
    public void updateScore(String name) {
	SQLiteDatabase db = getWritableDatabase();
    String query =  "SELECT * FROM scores WHERE name = \""+name+"\";";;

	Cursor c = db.rawQuery(query,null);
	if(c!=null && c.getCount()>0){
		c.moveToFirst();
		String lastScore =  c.getString(c.getColumnIndex("score"));
		int newScore = Integer.parseInt(lastScore) +1 ;
		db.delete(TABLE_SCORES, COLUMN_NAME + "=\"" + name+"\"", null) ;
		ContentValues values = new ContentValues();
    	values.put(COLUMN_NAME, name);
    	values.put(COLUMN_record, newScore);
    	db.insert(TABLE_SCORES, null,values);
	}

        db.close();
    }
    	 
    
    public String DatabaseToString(){
        String dbString = "";
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM "+TABLE_SCORES+" WHERE 1 ;";

        Cursor c = db.rawQuery(query,null);
        if(c!=null && c.getCount()>0){

        c.moveToFirst();

                for(int i = 0 ;i<c.getCount();i++){
                    String record =  c.getString(c.getColumnIndex("name")) + "	"+c.getString(c.getColumnIndex("score"));
                    c.move(1);

                    if(record!=null){
                        dbString+=record+" \n";
                    }
                }
        //}
    }
        db.close();
        return dbString;
    }
}