package com.example.database_helper;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.sqlite.model.*;

public class DataBaseHelper extends SQLiteOpenHelper
{
	private static final String LOG = "DatabaseHelper";
	 
    // Database Version
    private static final int DATABASE_VERSION = 1;
 
    // Database Name
    private static final String DATABASE_NAME = "crime_seen";
    
 // Table Names
    private static final String TABLE_USER = "user";
    private static final String TABLE_REPORT = "report";

    // Common column names
    private static final String KEY_ID = "id";
    
    private static final String KEY_USERNAME = "username";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_CITY_ADDRESS = "city_address";
    private static final String KEY_BIRTHDAY = "birthday";
    private static final String KEY_SEX = "sex";
    private static final String KEY_LAST_LOGIN = "last_login";
    
    private static final String KEY_INCIDENT = "incident_type";
    private static final String KEY_DESC = "description";
    private static final String KEY_TIME = "time";
    private static final String KEY_DATE = "date";
    private static final String KEY_EDIT_DATE = "edit_date";
    private static final String KEY_EDIT_TIME = "edit_time";
    private static final String KEY_LAT = "latitude";
    private static final String KEY_LONG = "longitude";
    
    private static final String CREATE_TABLE_USER = "CREATE TABLE "
            + TABLE_USER + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_USERNAME
            + " TEXT," + KEY_PASSWORD + " TEXT," + KEY_SEX + " TEXT," + KEY_CITY_ADDRESS + " TEXT," + KEY_BIRTHDAY
            + " DATETIME," + KEY_LAST_LOGIN + " DATETIME" + ")";
    
    private static final String CREATE_TABLE_REPORT = "CREATE TABLE " 
    		+ TABLE_REPORT + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," 
    		+ KEY_USERNAME + " TEXT," + KEY_INCIDENT + " TEXT," + KEY_DESC  
    		+ " TEXT," + KEY_TIME + " TEXT," + KEY_DATE + " TEXT,"  + KEY_EDIT_DATE 
    		+ " TEXT,"  + KEY_EDIT_TIME + " TEXT," + KEY_LAT + " TEXT,"+ KEY_LONG + " TEXT" + ")";
    
    
    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

	@Override
	public void onCreate(SQLiteDatabase db) {
		 
        // creating required tables
        db.execSQL(CREATE_TABLE_USER);
        db.execSQL(CREATE_TABLE_REPORT);

    }
 
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);

        // create new tables
        onCreate(db);
    }
    
 // ------------------------ "USER" table methods ----------------//
    public void createUser(user_entity ue) {
        SQLiteDatabase db = this.getWritableDatabase();
     
        ContentValues values = new ContentValues();
        values.put(KEY_USERNAME, ue.getUsername());
        values.put(KEY_PASSWORD, ue.getPassword());
        values.put(KEY_SEX, ue.getSex());
        values.put(KEY_CITY_ADDRESS, ue.getCity_address());
        values.put(KEY_BIRTHDAY, ue.getBirthdate());
        values.put(KEY_LAST_LOGIN, ue.getLast_login());
        
        db.insert(TABLE_USER, null, values);

    }
    
    public user_entity getUser(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        
        user_entity ue= new user_entity();
     
        String selectQuery = "SELECT  * FROM " + TABLE_USER + " WHERE "
                + KEY_ID + " = " + id;
     
        Log.e(LOG, selectQuery);
     
        Cursor c = db.rawQuery(selectQuery, null);
     
        if( c != null && c.moveToFirst() ){
        ue= new user_entity(c.getInt(c.getColumnIndex(KEY_ID)), 
        		c.getString(c.getColumnIndex(KEY_USERNAME)),
        		c.getString(c.getColumnIndex(KEY_PASSWORD)),
        		c.getString(c.getColumnIndex(KEY_SEX)),
        		c.getString(c.getColumnIndex(KEY_CITY_ADDRESS)), 
        		c.getString(c.getColumnIndex(KEY_BIRTHDAY)),
        		c.getString(c.getColumnIndex(KEY_LAST_LOGIN)));
        }
     
        return ue;
    }
    
    public user_entity AuthenticateUser(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        
        user_entity ue = new user_entity();
     
        String selectQuery = "SELECT  * FROM " + TABLE_USER + " WHERE "
                + KEY_USERNAME + " = '" + username+"'";
     
        Cursor c = db.rawQuery(selectQuery, null);
        
        if( c != null && c.moveToFirst() ){
        ue= new user_entity(c.getInt(c.getColumnIndex(KEY_ID)), 
        		c.getString(c.getColumnIndex(KEY_USERNAME)),
        		c.getString(c.getColumnIndex(KEY_PASSWORD)),
        		c.getString(c.getColumnIndex(KEY_SEX)),
        		c.getString(c.getColumnIndex(KEY_CITY_ADDRESS)), 
        		c.getString(c.getColumnIndex(KEY_BIRTHDAY)),
        		c.getString(c.getColumnIndex(KEY_LAST_LOGIN)));
        }
        
        c.close();
        return ue;
    }
    
	//----------------------REPORTS methods-------------------//
    
    public void addReport(report_entity report) {
        SQLiteDatabase db = this.getWritableDatabase();
 
        ContentValues values = new ContentValues();
        values.put(KEY_INCIDENT, report.get_incident_type());
        values.put(KEY_DESC, report.get_description());
        values.put(KEY_TIME, report.get_time());
        values.put(KEY_DATE, report.get_date());
        values.put(KEY_EDIT_DATE, report.get_last_edit_date());
        values.put(KEY_EDIT_TIME, report.get_last_edit_time());
        values.put(KEY_USERNAME, report.get_username());
        values.put(KEY_LAT, report.get_lat());
        values.put(KEY_LONG, report.get_lng());
 
        // Inserting Row
        db.insert(TABLE_REPORT, null, values);
        db.close(); // Closing database connection
    }
 
    // Getting single Report
   report_entity getReport(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
 
        Cursor cursor = db.query(TABLE_REPORT, new String[] { KEY_ID,
                KEY_USERNAME, KEY_INCIDENT, KEY_DESC,KEY_TIME, KEY_DATE,
                KEY_EDIT_TIME, KEY_EDIT_DATE, KEY_LAT, KEY_LONG }, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        
        report_entity report = new report_entity();
        
        if (cursor != null && cursor.moveToFirst())
        {
        report = new report_entity(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2), cursor.getString(3),
                cursor.getString(4), cursor.getString(5), cursor.getString(6),
                cursor.getString(7), cursor.getString(8), cursor.getString(9));
        // return Report
        
        }
        return report;
    }
     
    // Getting All Reports
    public ArrayList<report_entity> getAllReports() {
    	ArrayList<report_entity> ReportList = new ArrayList<report_entity>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_REPORT;
 
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
 
        // looping through all rows and adding to list
        if (cursor != null && cursor.moveToFirst()) {
            do {
                report_entity Report = new report_entity();
                Report.set_id(Integer.parseInt(cursor.getString(0)));
                Report.set_username(cursor.getString(1));
                Report.set_incident_type(cursor.getString(2));
                Report.set_description(cursor.getString(3));
                Report.set_time(cursor.getString(4));
                Report.set_date(cursor.getString(5));
                Report.set_last_edit_time(cursor.getString(6));
                Report.set_last_edit_date(cursor.getString(5));
                
                // Adding Report to list
                ReportList.add(Report);
            } while (cursor.moveToNext());
        }
 
        // return Report list
        return ReportList;
    }
    
    public ArrayList<report_entity> getRecentReports() {
    	ArrayList<report_entity> ReportList = new ArrayList<report_entity>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_REPORT + " ORDER BY " +
                KEY_DATE + " DESC, "+KEY_TIME+" DESC" + " LIMIT 10";
 
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
 
        // looping through all rows and adding to list
        if (cursor != null && cursor.moveToFirst()) {
            do {
                report_entity Report = new report_entity();
                Report.set_id(Integer.parseInt(cursor.getString(0)));
                Report.set_username(cursor.getString(1));
                Report.set_incident_type(cursor.getString(2));
                Report.set_description(cursor.getString(3));
                Report.set_time(cursor.getString(4));
                Report.set_date(cursor.getString(5));
                Report.set_last_edit_time(cursor.getString(6));
                Report.set_last_edit_date(cursor.getString(7));
                Report.set_lat(cursor.getString(8));
                Report.set_lng(cursor.getString(9));
                
                // Adding Report to list
                ReportList.add(Report);
            } while (cursor.moveToNext());
        }
 
        // return Report list
        return ReportList;
    }
    
    public List<report_entity> getHotReports() {
        List<report_entity> ReportList = new ArrayList<report_entity>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_REPORT + " ORDER BY " +
        KEY_DATE + ", "+" KEY_TIME DESC" + " LIMIT 5";
 
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
 
        // looping through all rows and adding to list
        if (cursor != null && cursor.moveToFirst()) {
            do {
                report_entity Report = new report_entity();
                Report.set_id(Integer.parseInt(cursor.getString(0)));
                Report.set_username(cursor.getString(1));
                Report.set_incident_type(cursor.getString(2));
                Report.set_description(cursor.getString(3));
                Report.set_time(cursor.getString(4));
                Report.set_date(cursor.getString(5));
                Report.set_last_edit_time(cursor.getString(6));
                Report.set_last_edit_date(cursor.getString(5));
                
                // Adding Report to list
                ReportList.add(Report);
            } while (cursor.moveToNext());
        }
 
        // return Report list
        return ReportList;
    }
 
    // Updating single Report
    public int updateReport(report_entity Report) {
        SQLiteDatabase db = this.getWritableDatabase();
 
        ContentValues values = new ContentValues();
        values.put(KEY_USERNAME, Report.get_username());
        values.put(KEY_INCIDENT, Report.get_incident_type());
        values.put(KEY_DESC, Report.get_description());
        values.put(KEY_TIME, Report.get_time());
        values.put(KEY_DATE, Report.get_date());
        values.put(KEY_ID, Report.get_id());
        values.put(KEY_EDIT_DATE, Report.get_last_edit_date());
        values.put(KEY_EDIT_TIME, Report.get_last_edit_time());
 
        // updating row
        return db.update(TABLE_REPORT, values, KEY_ID + " = ?",
                new String[] { String.valueOf(Report.get_id()) });
    }
 
    // Deleting single Report
    public void deleteReport(report_entity Report) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_REPORT, KEY_ID + " = ?",
                new String[] { String.valueOf(Report.get_id()) });
        db.close();
    }
 
 
    // Getting Reports Count
    public int getReportsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_REPORT;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
 
        // return count
        return cursor.getCount();
    }

}
