package com.example.sqlite.model;



import java.sql.Time;

public class report_entity {
    
    //private variables
    private int _id;
    private String _incident_type;
    private String _time;
    private String _date;
    private String _description;
    private String _last_edit_time;
    private String _last_edit_date;
    private String _username;
    private String _lat;
    private String _lng;
     
    // Empty constructor
    public report_entity (){
         
    }
    // constructor
    public report_entity(int id, String username, String incident_type, String time, String date, String description, String last_edit_time, String last_edit_date, String lat, String lng){
        this._id = id;
        this._incident_type = incident_type;
        this._time = time;
		this._date = date;
		this._description = description;
		this._last_edit_date = last_edit_date;
		this._last_edit_time = last_edit_time;
		this._username = username;
		this._lat=lat;
		this._lng=lng;
    }
     
    // constructor
    public report_entity(String username, String incident_type, String time, String date, String description, String last_edit_time, String last_edit_date, String lat, String lng){
        this._incident_type = incident_type;        
		this._time = time;
		this._date = date;
		this._description = description;
		this._last_edit_date = last_edit_date;
		this._last_edit_time = last_edit_time;	
		this._username = username;
		this._lat=lat;
		this._lng=lng;
    }
    
    public String get_date() {
		return _date;
	}
	public void set_date(String _date) {
		this._date = _date;
	}
	public String get_description() {
		return _description;
	}
	public void set_description(String _description) {
		this._description = _description;
	}
	public int get_id() {
		return _id;
	}
	public void set_id(int _id) {
		this._id = _id;
	}
	public String get_incident_type() {
		return _incident_type;
	}
	public void set_incident_type(String _incident_type) {
		this._incident_type = _incident_type;
	}
	public String get_time() {
		return _time;
	}
	public void set_time(String _time) {
		this._time = _time;
	}
	public String get_last_edit_time() {
		return _last_edit_time;
	}
	public void set_last_edit_time(String _last_edit_time) {
		this._last_edit_time = _last_edit_time;
	}
	public String get_last_edit_date() {
		return _last_edit_date;
	}
	public void set_last_edit_date(String _last_edit_date) {
		this._last_edit_date = _last_edit_date;
	}
	public String get_username() {
		return _username;
	}
	public void set_username(String _username) {
		this._username = _username;
	}
	public String get_lat() {
		return _lat;
	}
	public void set_lat(String _lat) {
		this._lat = _lat;
	}
	public String get_lng() {
		return _lng;
	}
	public void set_lng(String _lng) {
		this._lng = _lng;
	}
	
	
}



