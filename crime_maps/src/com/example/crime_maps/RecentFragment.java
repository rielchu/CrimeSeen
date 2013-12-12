package com.example.crime_maps;

import java.util.ArrayList;

import com.example.crime_maps.R;
import com.example.database_helper.DataBaseHelper;
import com.example.sqlite.model.report_entity;
import com.example.view_adapter.MyCustomBaseAdapter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

public class RecentFragment extends Fragment {
	
	ListView lv;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
          Bundle savedInstanceState) {
	  

	  ArrayList<report_entity> searchResults = new ArrayList<report_entity>(); 
	  searchResults = GetRecentResults();	  

      View rootView = inflater.inflate(R.layout.fragment_recent, container, false);

      lv = (ListView) rootView.findViewById(R.id.srListView);
      lv.setAdapter(new MyCustomBaseAdapter(getActivity(), searchResults));
      lv.setOnItemClickListener(new OnItemClickListener() {
          @Override
          public void onItemClick(AdapterView<?> a, View v, int position, long id) {
              Object o = lv.getItemAtPosition(position);
              report_entity rep = (report_entity)o;
              
          	Intent i = new Intent(getActivity(),ViewReportMapActivity.class);   
          	i.putExtra("lat", rep.get_lat());
          	i.putExtra("lng", rep.get_lng());
          	i.putExtra("type", rep.get_incident_type());
          	i.putExtra("date", rep.get_date());
          	i.putExtra("time", rep.get_time());
          	startActivity(i);

          }
      });
       
      return rootView;
  }
  

  public ArrayList<report_entity> GetRecentResults()
  {
	ArrayList<report_entity> rList = new ArrayList<report_entity>();
	try {
	DataBaseHelper db = new DataBaseHelper(getActivity());
	rList = db.getRecentReports();
	db.close();
	}
	catch(Exception e)
	{
		e.printStackTrace();
	
	}
	return rList;
  }
  
  public void refreshData(Intent data){

      if(lv!=null)
      {
    	  lv.setAdapter(new MyCustomBaseAdapter(getActivity(), GetRecentResults()));
     
    	  lv.setOnItemClickListener(new OnItemClickListener() {
              @Override
              public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                  Object o = lv.getItemAtPosition(position);
                  report_entity rep = (report_entity)o;
                  
              	Intent i = new Intent(getActivity(),ViewReportMapActivity.class);   
              	i.putExtra("lat", rep.get_lat());
              	i.putExtra("lng", rep.get_lng());
              	i.putExtra("type", rep.get_incident_type());
              	i.putExtra("date", rep.get_date());
              	i.putExtra("time", rep.get_time());
              	startActivity(i);

              }
          });
           
      }

}
  
 
  
}
