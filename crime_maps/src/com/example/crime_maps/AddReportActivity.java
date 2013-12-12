package com.example.crime_maps;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import com.example.database_helper.DataBaseHelper;
import com.example.sqlite.model.report_entity;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Marker;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class AddReportActivity extends FragmentActivity{
	
	private static TextView mEdit;
	private static TextView txtTime;
	private TextView txtType;
	private static TextView txtLat, txtLng;
	private Button sendBtn;
	private EditText editDesc;
	private Context context;
	private Double lat,lng;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_add_report);
         
        txtType = (TextView) findViewById(R.id.textViewIncident);
        mEdit = (TextView) findViewById(R.id.textViewDate);
        txtTime = (TextView) findViewById(R.id.textViewTime);
        txtLat = (TextView) findViewById(R.id.textViewLat);
        //txtLng = (TextView) findViewById(R.id.textViewLong);
        editDesc = (EditText) findViewById(R.id.editTextDesc);
        sendBtn = (Button) findViewById(R.id.addReportSubmitBtn);
        
        context = this.getApplication();
         
        Intent i = getIntent();
        // getting attached intent data
        String incident_type = i.getStringExtra("incident_type");
        // displaying selected product name
        txtType.setText(incident_type);
        
    	final Calendar c = Calendar.getInstance();
    	int hour1 = c.get(Calendar.HOUR_OF_DAY);
    	int minute1 = c.get(Calendar.MINUTE);
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
    	
		String temp;
		if(minute1<10)
		{
		temp = "0"+minute1;	
		}
		else
			temp= ""+minute1;
		txtTime.setText(hour1+":"+temp);
    	mEdit.setText(month+"/"+day+"/"+year);
         
    	  sendBtn.setOnClickListener(new View.OnClickListener() {
    			public void onClick(View v) {
    				
    				try{
    				DataBaseHelper db = new DataBaseHelper(getApplicationContext());
    				
    				    String incident = txtType.getText().toString();
    			        String date=mEdit.getText().toString();
    			        String time =txtTime.getText().toString();
    			        String latitude = lat.toString();
    			        String longitude = lng.toString();
    			        
    			        if(incident.trim().equals("")||date.trim().equals("")||time.trim().equals("")||latitude.trim().equals(""))
    			        {
    						Toast.makeText(context, "One or more fields are empty", Toast.LENGTH_LONG);

    			        }
    			        else {
    			        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
						String username = settings.getString("username", null);
						String desc = editDesc.getText().toString();
						
						report_entity r = new report_entity();
						r.set_date(date);
						r.set_incident_type(incident);
						r.set_description(desc);
						r.set_last_edit_date(date);
						r.set_last_edit_time(time);
						r.set_lat(latitude);
						r.set_lng(longitude);
						r.set_time(time);
						r.set_username(username);
						
						db.addReport(r);
						db.close();
				
						Toast.makeText(context, "Report Submission Successful", Toast.LENGTH_LONG);
    			        
						setResult(200,getIntent());
						finish();
    			        }
    				}
    				catch(Exception e)
    				{
						Toast.makeText(context, "One or more fields are empty", Toast.LENGTH_LONG);
		
    				}
    			       
    				}
    			});
    	  
    }
    
    public void showAddLocationActivity(View v)
    {
    	Intent i = new Intent(AddReportActivity.this,AddReportMapActivity.class);    
    	startActivityForResult(i, 100);

    }
    
    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }
    
    public void showTimePickerDialog(View v) {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getSupportFragmentManager(), "timePicker");
    }
    
    public static class DatePickerFragment extends DialogFragment
    implements DatePickerDialog.OnDateSetListener {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
    // Use the current date as the default date in the picker
    final Calendar c = Calendar.getInstance();
    int year = c.get(Calendar.YEAR);
    int month = c.get(Calendar.MONTH);
    int day = c.get(Calendar.DAY_OF_MONTH);

    // Create a new instance of DatePickerDialog and return it
    return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
    // Do something with the date chosen by the user
    	mEdit.setText(month+"/"+day+"/"+year);
    }
    }
    
    public static class TimePickerFragment extends DialogFragment
    implements TimePickerDialog.OnTimeSetListener {

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
	// Use the current time as the default values for the picker
	final Calendar c = Calendar.getInstance();
	int hour = c.get(Calendar.HOUR_OF_DAY);
	int minute = c.get(Calendar.MINUTE);
	
	// Create a new instance of TimePickerDialog and return it
	return new TimePickerDialog(getActivity(), this, hour, minute,
	DateFormat.is24HourFormat(getActivity()));
	}
    
    
	public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
	// Do something with the time chosen by the user
		String temp;
		if(minute<10)
		{
		temp = "0"+minute;	
		}
		else
			temp= ""+minute;
		txtTime.setText(hourOfDay+":"+temp);
	}
    }
		
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) 
	    {
	    super.onActivityResult(requestCode, resultCode, data);

	    if(resultCode ==  200)
	    {

	        String enteredText = "no action defined for this requestcode :"+requestCode;
	        if(requestCode == 100)
	        {
	            enteredText = (String)data.getStringExtra("SearchText");
	  	     
	  		      lat=Double.parseDouble(data.getStringExtra("latitude"));
	  		     lng=Double.parseDouble(data.getStringExtra("longitude"));
	  		     
	  		     
	  		     
	  		     txtLat.setText(getAddress(lat,lng));
	  	      //Toast.makeText(this,enteredText,Toast.LENGTH_SHORT).show();
	  		     
	  		     
	  	      
	  	    
	        }
	    }
	    else
	    {
	       // Toast.makeText(this,"some exception occurred",Toast.LENGTH_SHORT).show();
	    }
	}
	
	 public String getAddress(Double lat, Double lng)
	    {
	    	String addressText="";
	    	
	    	try {
	            Geocoder geocoder = new Geocoder(context, Locale.getDefault());
	            List<Address> addresses = geocoder.getFromLocation(lat, lng, 1);
	            if (addresses.size() > 0 && addresses!=null) {
	                Address address = addresses.get(0);

	                addressText = String.format(
	                        "%s, %s, %s",
	                        // If there's a street address, add it
	                        address.getMaxAddressLineIndex() > 0 ?
	                                address.getAddressLine(0) : "",
	                        // Locality is usually a city
	                        address.getLocality(),
	                        // The country of the address
	                        address.getCountryName());


	            }
	            else
	            {
	            //addressText.append("No address found");	
	            addressText="No address found";
	            }
	            
	            //addressText.append(_date+" "+time);
	            
	        } catch (IOException e) {
	            Log.e("tag", e.getMessage());
	        }
	    	
	    	return addressText;
	    }
	

    

}
