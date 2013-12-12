package com.example.crime_maps;

import java.lang.reflect.Method;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;

public class ConnectionDetector {
    
    private Context _context;
     
    public ConnectionDetector(Context context){
        this._context = context;
    }
 
    public boolean isConnectingToInternet(){
        ConnectivityManager connectivity = (ConnectivityManager) _context.getSystemService(Context.CONNECTIVITY_SERVICE);
          if (connectivity != null)
          {
              NetworkInfo[] info = connectivity.getAllNetworkInfo();
              if (info != null)
                  for (int i = 0; i < info.length; i++)
                      if (info[i].getState() == NetworkInfo.State.CONNECTED)
                      {
                          return true;
                      }
 
          }
          return false;
    }
    
    public Boolean isMobileDataEnabled(){
        Object connectivityService = _context.getSystemService(Context.CONNECTIVITY_SERVICE); 
        ConnectivityManager cm = (ConnectivityManager) connectivityService;

        try {
            Class<?> c = Class.forName(cm.getClass().getName());
            Method m = c.getDeclaredMethod("getMobileDataEnabled");
            m.setAccessible(true);
            return (Boolean)m.invoke(cm);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    protected void createNetErrorDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(_context);
        builder.setMessage("You need a network connection to use this application. Please turn on mobile network or Wi-Fi in Settings.");
            builder.setTitle("Unable to connect");
            builder.setCancelable(false);
            builder.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                   // Intent i = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
                	
                	Intent i = new Intent(Settings.ACTION_WIFI_SETTINGS);
                    _context.startActivity(i);
                }
            }
        )
        .setNegativeButton("Cancel",
            new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                	dialog.cancel();
                	((Activity)_context).finish();
                }
            }
        );
        AlertDialog alert = builder.create();
        alert.show();
    }
}