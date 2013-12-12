package com.example.crime_maps;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class AddReportIncidentActivity extends ListActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         
        // storing string resources into Array
        String[] incident_types = getResources().getStringArray(R.array.incident_types);
         
        // Binding resources Array to ListAdapter
        this.setListAdapter(new ArrayAdapter<String>(this, R.layout.incident_type_list, R.id.label, incident_types));
         
        ListView lv = getListView();
 
        // listening to single list item on click
        lv.setOnItemClickListener(new OnItemClickListener() {
          public void onItemClick(AdapterView<?> parent, View view,
              int position, long id) {
               
              // selected item
              String type = ((TextView) view).getText().toString();
               
              // Launching new Activity on selecting single List Item
              Intent i = new Intent(getApplicationContext(), AddReportActivity.class);
              // sending data to new activity
              i.putExtra("incident_type", type);
              //startActivity(i);
              
          	  startActivityForResult(i, 100);
             
          }
        });
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) 
    {
    super.onActivityResult(requestCode, resultCode, data);

    if(resultCode==200)
    {
    	setResult(200,getIntent());
    	finish(); 
    }
    else
    {
       // Toast.makeText(this,"some exception occurred",Toast.LENGTH_SHORT).show();
        finish();
    }
}
}
