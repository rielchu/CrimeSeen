package com.example.crime_maps;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.LocationManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.database_helper.DataBaseHelper;
import com.example.sqlite.model.user_entity;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class AddReportMapActivity extends FragmentActivity implements OnMapClickListener, OnMapLongClickListener {
	private GoogleMap mMap;
	private Bundle mBundle;
	private LocationManager manager;
	private Activity activity;
	private GPSTracker gps;
	private ConnectionDetector cd;
	private Boolean isInternetPresent;
	private Boolean isMobileDataPresent;
	private Marker crimeLocation;
	private Button doneBtn;
	
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_add_map);
 
        FragmentManager fmanager = getSupportFragmentManager();
        Fragment fragment = fmanager.findFragmentById(R.id.map);
        SupportMapFragment supportmapfragment = (SupportMapFragment)fragment;
        mMap = supportmapfragment.getMap();
        
        Context context = AddReportMapActivity.this;
        try {
            MapsInitializer.initialize(context);
        } catch (GooglePlayServicesNotAvailableException e) {
        	e.printStackTrace();
       }
        
        gps = new GPSTracker(context); 
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        cd = new ConnectionDetector(context);
        mMap.setMyLocationEnabled(true);
        
        doneBtn = (Button)findViewById(R.id.buttonMapDone);
        
        mMap.setOnMapClickListener(this);
        mMap.setOnMapLongClickListener(this);
      
        getLocation();
        
   	 doneBtn.setOnClickListener(new View.OnClickListener() {
		 public void onClick(View v) {
			 
			  Intent intent = new Intent();
	            //EditText edtSearchBar = (EditText )findViewById(R.id.tvTest);

	               intent.putExtra("SearchText", "");
	               intent.putExtra("latitude", crimeLocation.getPosition().latitude+"");
	               intent.putExtra("longitude", crimeLocation.getPosition().longitude+"");
	            setResult(200,intent);
	            finish();

		 }
	 });
   	 
   	for (int i=0; i < 3; i++)
   	{
   	     // Toast.makeText(this, "blah", Toast.LENGTH_LONG).show();
   	
     Toast.makeText(getApplicationContext(), "Press and hold on target location" , Toast.LENGTH_LONG).show();   	 
   	} 
    }
    
    @Override
    public void onMapClick(LatLng point) {
    Toast.makeText(getApplicationContext(), "X: "+point.latitude+" Y: "+point.longitude, Toast.LENGTH_LONG).show();
     mMap.animateCamera(CameraUpdateFactory.newLatLng(point));
    }

    @Override
    public void onMapLongClick(LatLng point) {
     
     if (mMap!=null){
    	 
    	 if(crimeLocation!=null)
    	 {
     	crimeLocation.setPosition(point);
       }
    	 else
    	 {
    		 crimeLocation = mMap.addMarker(new MarkerOptions().position(point)
 	                .title("This is where it happened").snippet(point.latitude+", "+point.longitude))
 	                ;
    		 
    	 }
     }
    }
	
	public void getLocation()
	{
	    if(gps.canGetLocation()){
	        
	        double latitude = gps.getLatitude();
	        double longitude = gps.getLongitude();
	        CameraPosition cameraPosition = new CameraPosition.Builder()
	        .target(new LatLng(latitude, longitude))      // Sets the center of the map to Mountain View
	        .zoom(17)                   // Sets the zoom
	        .bearing(90)                // Sets the orientation of the camera to east
	        .tilt(30)                   // Sets the tilt of the camera to 30 degrees
	        .build();                   // Creates a CameraPosition from the builder
	    mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
	       
	        if (mMap!=null){
	        	crimeLocation = mMap.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude))
	                .title("This is where it happened").snippet(latitude+", "+longitude))
	                ;
	          }
	    }else{
	    	 double latitude = 123.0167;
		        double longitude = 11.3333;
		        CameraPosition cameraPosition = new CameraPosition.Builder()
		        .target(new LatLng(latitude, longitude))      // Sets the center of the map to Mountain View
		        .zoom(10)                   // Sets the zoom
		        .bearing(90)                // Sets the orientation of the camera to east
		        .tilt(30)                   // Sets the tilt of the camera to 30 degrees
		        .build();                   // Creates a CameraPosition from the builder
		    mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
		       
	    }
	}
		
	

	
	public void addMapSubmit()
	{
		Intent resultIntent = new Intent();
		resultIntent.putExtra("latitude", crimeLocation.getPosition().latitude);
		resultIntent.putExtra("longitude", crimeLocation.getPosition().longitude);
		
		setResult(RESULT_OK, resultIntent);
		finish();
	}

	}

