package com.example.view_adapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.example.crime_maps.R;
import com.example.sqlite.model.report_entity;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;
 
public class MyCustomBaseAdapter extends BaseAdapter {
    private static ArrayList<report_entity> searchArrayList;
 
    private LayoutInflater mInflater;
    private Context mContext;
    public MyCustomBaseAdapter(Context context, ArrayList<report_entity> results) {
        searchArrayList = results;
        mInflater = LayoutInflater.from(context);
        mContext=context;
    }
 
    public int getCount() {
        return searchArrayList.size();
    }
 
    public Object getItem(int position) {
        return searchArrayList.get(position);
    }
 
    public long getItemId(int position) {
        return position;
    }
 
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.custom_row_view, null);
            holder = new ViewHolder();
            holder.txtName = (TextView) convertView.findViewById(R.id.name);
            holder.txtCityState = (TextView) convertView
                    .findViewById(R.id.cityState);
            holder.txtPhone = (TextView) convertView.findViewById(R.id.phone);
            holder.txtTime = (TextView) convertView.findViewById(R.id.time);
            TextView type=(TextView) convertView.findViewById(R.id.name);
            //Toast.makeText(mContext, type.getText().toString(), Toast.LENGTH_LONG).show();      
   
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
 
        holder.txtName.setText(searchArrayList.get(position).get_incident_type());
        String tempAdd=getAddress(searchArrayList.get(position)
                .get_lat(), searchArrayList.get(position).get_lng());
        holder.txtCityState.setText(tempAdd);
        holder.txtPhone.setText(searchArrayList.get(position).get_date());
        holder.txtTime.setText(searchArrayList.get(position).get_time());
        
        if(searchArrayList.get(position).get_incident_type().equals("Homicide"))
        {
        	convertView.setBackgroundColor(0xFFFF4444);        	
        }
        else if (searchArrayList.get(position).get_incident_type().equals("Battery/Assault"))
        	{
        	convertView.setBackgroundColor(0xFFAA66CC);
        	}
        
        else if (searchArrayList.get(position).get_incident_type().equals("Kidnapping"))
        {
        	convertView.setBackgroundColor(0xFFFFBB33);
        }
        
        else if (searchArrayList.get(position).get_incident_type().equals("Sexual Offense"))
        {
        	convertView.setBackgroundColor(0xFF33B5E5);
        }
        
        else if (searchArrayList.get(position).get_incident_type().equals("Theft (Larceny)"))
        {
        	convertView.setBackgroundColor(0xFF99CC00);
        }
        
        else if (searchArrayList.get(position).get_incident_type().equals("Robbery"))
        {
        	convertView.setBackgroundColor(0xFFFCD116);
        }
        
        else if (searchArrayList.get(position).get_incident_type().equals("Burglary"))
        {
        	convertView.setBackgroundColor(0xFF00ffff);
        }
        
        else if (searchArrayList.get(position).get_incident_type().equals("Arson"))
        {
        	convertView.setBackgroundColor(0xFFFF66CC);
        }
        
        else if (searchArrayList.get(position).get_incident_type().equals("Others"))
        {
        	convertView.setBackgroundColor(0xFF007FFF);      
        }
        
        else // this will be the default background color: transparent
        {
        	convertView.setBackgroundColor(0x00000000);
        }
        
 
        return convertView;
    }
 
    static class ViewHolder {
        TextView txtName;
        TextView txtCityState;
        TextView txtPhone;
        TextView txtTime;
    }
    
    public String getAddress(String lat, String lng)
    {
    	String addressText="";
    	
    	try {
            Geocoder geocoder = new Geocoder(mContext, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(Double.parseDouble(lat), Double.parseDouble(lng), 1);
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