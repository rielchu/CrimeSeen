package com.example.crime_maps;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.widget.Toast;
import android.location.Geocoder;

public class ViewReportMapActivity extends FragmentActivity {
	
	private GoogleMap mMap;
	
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_map);
 
        FragmentManager fmanager = getSupportFragmentManager();
        Fragment fragment = fmanager.findFragmentById(R.id.map);
        SupportMapFragment supportmapfragment = (SupportMapFragment)fragment;
        mMap = supportmapfragment.getMap();
        
        Intent i = getIntent();
        String lat = i.getStringExtra("lat");
        String lng = i.getStringExtra("lng");
        String type = i.getStringExtra("type");
        String _date = i.getStringExtra("date");
        String time = i.getStringExtra("time");
        
        Context context = ViewReportMapActivity.this;
        try {
            MapsInitializer.initialize(context);
        } catch (GooglePlayServicesNotAvailableException e) {
        	e.printStackTrace();
       }
        
        //List<Address> addresses = null;
        //Geocoder geocoder = new Geocoder(context);
        
        //StringBuilder addressText = new StringBuilder();
        String addressText="";
        double latitude = Double.parseDouble(lat);
        double longitude = Double.parseDouble(lng);
        //Toast.makeText(getApplicationContext(), latitude+" "+longitude, Toast.LENGTH_LONG).show();      

            try {
                Geocoder geocoder = new Geocoder(this, Locale.getDefault());
                List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
                if (addresses.size() > 0 && addresses!=null) {
                    Address address = addresses.get(0);
//                    addressText.append(address.getAddressLine(0)).append("\n");
//                    addressText.append(address.getLocality()).append("\n");
//                    //addressText.append(address.getPostalCode()).append("\n");
//                    addressText.append(address.getCountryName());
                    
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
            
       if(type.equals("Robbery") ){Marker crimeLocation = mMap.addMarker(new MarkerOptions()
       .position(new LatLng(latitude, longitude)) .title(type).snippet(addressText+"")
       .icon(BitmapDescriptorFactory.defaultMarker((BitmapDescriptorFactory.HUE_YELLOW)))); } 
       if(type.equals("Burglary") ){Marker crimeLocation = mMap.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude))
    		   .title(type).snippet(addressText+"") .icon(BitmapDescriptorFactory.defaultMarker((BitmapDescriptorFactory.HUE_CYAN)))); }
       if(type.equals("Arson") ){Marker crimeLocation = mMap.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude)) 
    		   .title(type).snippet(addressText+"") .icon(BitmapDescriptorFactory.defaultMarker((BitmapDescriptorFactory.HUE_ROSE)))); }
       if(type.equals("Others") ){ Marker crimeLocation = mMap.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude))
    		   .title(type).snippet(addressText+"") .icon(BitmapDescriptorFactory.defaultMarker((BitmapDescriptorFactory.HUE_AZURE)))); }
  
        CameraPosition cameraPosition = new CameraPosition.Builder()
        .target(new LatLng(latitude, longitude))      // Sets the center of the map to Mountain View
        .zoom(17)                   // Sets the zoom
        .bearing(90)                // Sets the orientation of the camera to east
        .tilt(30)                   // Sets the tilt of the camera to 30 degrees
        .build();                   // Creates a CameraPosition from the builder
    mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
       
        if (mMap!=null){
        	Marker crimeLocation = mMap.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude))
                .title(type+" ("+_date+" "+time+")").snippet(addressText+"")); 
        	if(type.equals("Battery/Assault") ) {	
        crimeLocation.setIcon(BitmapDescriptorFactory.defaultMarker((BitmapDescriptorFactory.HUE_VIOLET)));
        	}
        	
        	else if(type.equals("Kidnapping"))
        	{
                crimeLocation.setIcon(BitmapDescriptorFactory.defaultMarker((BitmapDescriptorFactory.HUE_ORANGE)));
        		
        	}
        	
        	else if(type.equals("Homicide") ){
                crimeLocation.setIcon(BitmapDescriptorFactory.defaultMarker((BitmapDescriptorFactory.HUE_RED)));
        		
        }
        	else if(type.equals("Sexual Offense") ){
        		
                crimeLocation.setIcon(BitmapDescriptorFactory.defaultMarker((BitmapDescriptorFactory.HUE_BLUE)));

        	}
        	
        	else  if(type.equals("Theft (Larceny)") ){
                crimeLocation.setIcon(BitmapDescriptorFactory.defaultMarker((BitmapDescriptorFactory.HUE_GREEN)));
	
        	}
        	
        	else if(type.equals("Robbery") ){
                crimeLocation.setIcon(BitmapDescriptorFactory.defaultMarker((BitmapDescriptorFactory.HUE_YELLOW)));
	
        	}
        	
        	else  if(type.equals("Burglary") ){
                crimeLocation.setIcon(BitmapDescriptorFactory.defaultMarker((BitmapDescriptorFactory.HUE_CYAN)));
	
        	}
        	else if(type.equals("Arson") ){
                crimeLocation.setIcon(BitmapDescriptorFactory.defaultMarker((BitmapDescriptorFactory.HUE_ROSE)));

        	}
        	
        	else {
        		 	 
                 crimeLocation.setIcon(BitmapDescriptorFactory.defaultMarker((BitmapDescriptorFactory.HUE_AZURE)));

        	 }
        	
        	crimeLocation.showInfoWindow();
                
          }
        
    }

}
