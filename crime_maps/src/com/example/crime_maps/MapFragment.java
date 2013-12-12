package com.example.crime_maps;

import java.util.ArrayList;
import java.util.List;

import com.example.crime_maps.R;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.app.Activity;
import android.content.Context;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.format.Time;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Toast;

public class MapFragment extends SupportMapFragment {
private View v;
private GoogleMap mMap;
private Bundle mBundle;
private LocationManager manager;
private Activity activity;
private GPSTracker gps;
private ConnectionDetector cd;
private Boolean isInternetPresent;
private Boolean isMobileDataPresent;
private Context mContext;

@Override
public View onCreateView(LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState) {
    super.onCreateView(inflater, container, savedInstanceState);
    v=inflater.inflate(R.layout.fragment_map, null);
    
    if (mMap == null) {
  	  mMap = ((SupportMapFragment) getFragmentManager().findFragmentById(R.id.map))
  		        .getMap(); }
    
    Context context = getActivity();
    
    try {
        MapsInitializer.initialize(context);
    } catch (GooglePlayServicesNotAvailableException e) {
    	e.printStackTrace();
   }
   
    // create class object
    gps = new GPSTracker(context); 
    mMap.getUiSettings().setMyLocationButtonEnabled(true);
    cd = new ConnectionDetector(context);
    mMap.setMyLocationEnabled(true);
    mContext= context;
  
    getLocation();
    

    return v;       
}

public void getLocation()
{
	Toast.makeText(mContext, "...", Toast.LENGTH_LONG);
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
            Marker yourLocation = mMap.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude))
                .title("I See You!")
                .snippet("You are here."))
                ;
          }
    }else{
        // can't get location
        // GPS or Network is not enabled
        // Ask user to enable GPS/network in settings
        gps.showSettingsAlert();
    }
	}
  
  @Override
public void onDestroyView() 
{
        super.onDestroyView(); 
        Fragment fragment = (getFragmentManager().findFragmentById(R.id.map));  
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.remove(fragment);
        ft.commit();
}
  


}
